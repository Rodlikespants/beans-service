package resources;

import models.Person;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * currently doesn't do anything useful...
 * just testing mocks
 */
public class PersonResourceTest {


    @Mock
    PersonResource resource;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCall() {
        ObjectId id = new ObjectId();
        String firstName = "Fakey";
        String lastName = "McFakerson";
        String email = "guyincognito@fakemail.com";
        Person newPerson = new Person(
                id,
                firstName,
                lastName,
                email
        );
        List<Person> persons = List.of(newPerson);
        when(resource.getPersons()).thenReturn(persons);

        List<Person> results = resource.getPersons();
        verify(resource, times(1)).getPersons();
        assertEquals(persons, results);
    }
}
