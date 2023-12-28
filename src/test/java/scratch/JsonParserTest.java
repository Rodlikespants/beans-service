package scratch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Person;
import org.bson.types.ObjectId;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class JsonParserTest {

    @Test
    public void testJsonDeserialization() {
        ObjectId id = new ObjectId();
        String firstName = "Rod";
        String lastName = "Lai";
        String email = "rodwittlai@fakemail.com";
        Person person = new Person(id, firstName, lastName, email);

        // test serialization on a person
        ObjectMapper mapper = new ObjectMapper();
        String personSerialized;
        try {
            personSerialized = mapper.writeValueAsString(person);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // then deserialize that same person
        Person personDeserialized;
        try {
            personDeserialized = mapper.readValue(personSerialized, Person.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        assertEquals(person.getId().toString(), personDeserialized.getId().toString());
        assertEquals(person.getFirstName(), personDeserialized.getFirstName());
        assertEquals(person.getLastName(), personDeserialized.getLastName());
        assertEquals(person.getEmail(), personDeserialized.getEmail());

        // second test
        String personStr = "{\"id\":\"" + id + "\",\"firstName\":\"Rod\",\"lastName\":\"Lai\",\"email\":\"rodwittlai@fakemail.com\"}";
        Person personParsed;
        try {
            personParsed = mapper.readValue(personStr, Person.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        assertEquals(person.getId().toString(), personParsed.getId().toString());
        assertEquals(person.getFirstName(), personParsed.getFirstName());
        assertEquals(person.getLastName(), personParsed.getLastName());
        assertEquals(person.getEmail(), personParsed.getEmail());
    }

}
