package services;

import db.PersonDAO;
import db.entities.PersonEntity;
import models.Person;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class PersonService {
    PersonDAO personDao;

    @Inject
    public PersonService(PersonDAO personDao) {
        this.personDao = personDao;
    }

    public Person getById(ObjectId id) {
        PersonEntity personEntity = personDao.getOne(id);
        return toPerson(personEntity);
    }

    public List<Person> getAll() {
        List<PersonEntity> personEntities = personDao.getAll();
        return personEntities.stream()
                .map(PersonService::toPerson)
                .toList();
    }

    public void add(Person person) {
        PersonEntity personEntity = toPersonEntity(person);
        personDao.save(personEntity);
    }

    public void update(Person person) {
        PersonEntity personEntity = toPersonEntity(person);
        personDao.update(person.getId(), personEntity);
    }

    public void remove(ObjectId id) {
        personDao.delete(id);
    }

    /**
     * This could be in a better place
     * @param personEntity
     * @return
     */
    private static Person toPerson(PersonEntity personEntity) {
        return new Person(
                personEntity.getId(),
                personEntity.getFirstName(),
                personEntity.getLastName(),
                personEntity.getEmail()
        );
    }

    private static PersonEntity toPersonEntity(Person person) {
        PersonEntity personEntity = new PersonEntity(
                person.getId(),
                person.getFirstName(),
                person.getLastName(),
                person.getEmail()
        );
        return personEntity;
    }
}
