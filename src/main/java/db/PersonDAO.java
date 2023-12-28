package db;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import db.entities.PersonEntity;
import io.dropwizard.hibernate.AbstractDAO;
import models.Person;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.hibernate.SessionFactory;
import util.PersonMapper;

import javax.inject.Singleton;
import java.util.*;

@Singleton
public class PersonDAO {
//    private static Map<ObjectId, Person> persons = new HashMap<>();
//    private static final ObjectId id1 = new ObjectId("64b6c710171adc359ebcba3e");
//    private static final ObjectId id2 = new ObjectId("64b6c71e54f44431a452bb17");
//    private static final ObjectId id3 = new ObjectId("64b6c724ed632ec59fd867ae");
//    private static final ObjectId id4 = new ObjectId("64b6c72b235971d6c2c87898");
//
//    static {
//        persons.put(id1, new Person(id1, "FN1", "LN1", "email1@email.com"));
//        persons.put(id2, new Person(id2, "FN2", "LN2", "email2@email.com"));
//        persons.put(id3, new Person(id3, "FN3", "LN3", "email3@email.com"));
//        persons.put(id4, new Person(id4, "FN4", "LN4", "email4@email.com"));
//    }

//    public PersonDAO(SessionFactory sessionFactory) {
//        super(sessionFactory);
//    }

//    public Person getById(ObjectId id) {
//        return persons.get(id);
//    }
//
//    public List<Person> getAll() {
//        List<Person> result = new ArrayList<Person>();
//        for (ObjectId key : persons.keySet()) {
//            result.add(persons.get(key));
//        }
//        return result;
//    }
//
//    public int getCount() {
//        return persons.size();
//    }
//
//    public void remove() {
//        if (!persons.keySet().isEmpty()) {
//            persons.remove(persons.keySet().toArray()[0]);
//        }
//    }
//
//    public String save(Person person) {
//        String result = "";
//        if (persons.get(person.getId()) != null) {
//            result = "Updated Person with id=" + person.getId();
//        } else {
//            result = "Added Person with id=" + person.getId();
//        }
//        persons.put(person.getId(), person);
//        return result;
//    }

    /** The collection of Persons */
    final MongoCollection<Document> personCollection;

    /**
     * Constructor.
     *
     * @param personCollection the collection of persons.
     */
    public PersonDAO(final MongoCollection<Document> personCollection) {
        this.personCollection = personCollection;
    }

    /**
     * Find all persons.
     *
     * @return the persons.
     */
    public List<PersonEntity> getAll() {
        final MongoCursor<Document> persons = personCollection.find().iterator();
        final List<PersonEntity> personsFind = new ArrayList<>();
        try {
            while (persons.hasNext()) {
                final Document person = persons.next();
                personsFind.add(PersonMapper.map(person));
            }
        } finally {
            persons.close();
        }
        return personsFind;
    }

    /**
     * Get one document find in other case return null.
     *
     * @param id the identifier for find.
     * @return the Person find.
     */
    public PersonEntity getOne(final ObjectId id) {
        final Optional<Document> personFind = Optional.ofNullable(personCollection.find(new Document("_id", id)).first());
        return personFind.isPresent() ? PersonMapper.map(personFind.get()) : null;
    }

    public void save(final PersonEntity person){
        Document savePerson = new Document("firstName", person.getFirstName())
                .append("lastName", person.getLastName())
                .append("email", person.getEmail());
        if (person.getId() != null) {
            savePerson = savePerson.append("_id", person.getId());
        }
        personCollection.insertOne(savePerson);
    }

    /**
     * Update a register.
     *
     * @param id the identifier.
     * @param person the object to update.
     */
    public void update(final ObjectId id, final PersonEntity person) {
        Document updateDoc = new Document("firstName", person.getFirstName())
                .append("lastName", person.getLastName())
                .append("email", person.getEmail());
        personCollection.updateOne(new Document("_id", id),
                new Document("$set",
                        updateDoc
                )
        );
    }

    /**
     * Delete a register.
     * @param id    the identifier.
     */
    public void delete(final ObjectId id){
        personCollection.deleteOne(new Document("_id", id));
    }
}
