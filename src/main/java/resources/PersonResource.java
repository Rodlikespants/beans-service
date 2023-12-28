package resources;

import com.codahale.metrics.annotation.Timed;
import models.Person;
import org.bson.types.ObjectId;
import services.PersonService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/person")
@Singleton
public class PersonResource {

    PersonService personService;

    @Inject
    public PersonResource(PersonService personService) {
        this.personService = personService;
    }

    @GET
    @Timed
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Person getPerson(@PathParam("id") ObjectId id) {
        return personService.getById(id);
    }

    @DELETE
    @Timed
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String removePerson(@PathParam("id") ObjectId id) {
        personService.remove(id);
        return "Removed person id=" + id;
    }

    @GET
    @Timed
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getPersons() {
        return personService.getAll();
    }

    @POST
    @Timed
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes({MediaType.APPLICATION_JSON})
    public String addPerson(Person person) {
        personService.add(person);
        return "Added Person with id=" + person.getId();
    }

    @PUT
    @Timed
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes({MediaType.APPLICATION_JSON})
    public String updatePerson(Person person) {
        personService.update(person);
        return "Added Person with id=" + person.getId();
    }
}