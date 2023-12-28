package util;

import db.entities.PersonEntity;
import org.bson.Document;

public class PersonMapper {

    /**
     * Map objects {@link Document} to {@link db.entities.PersonEntity}.
     *
     * @param personDocument the information document.
     * @return A object {@link db.entities.PersonEntity}.
     */
    public static PersonEntity map(final Document personDocument) {
        final PersonEntity person = new PersonEntity();
        person.setId(personDocument.getObjectId("_id"));
        person.setFirstName(personDocument.getString("firstName"));
        person.setLastName(personDocument.getString("lastName"));
        person.setEmail(personDocument.getString("email"));
        return person;
    }
}
