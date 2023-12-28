package resources;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;
import models.Order;
import services.OrderService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

    /**
     * The DAO object to manipulate orders.
     */
    private OrderService orderService;

    /**
     * Constructor.
     *
     * @param orderService DAO object to manipulate orders.
     */
    @Inject
    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Method looks for an order by her id.
     *
     * @param id the id of an order we are looking for.
     * @return Optional containing the found order or an empty Optional
     * otherwise.
     */
    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<Order> findById(@PathParam("id") LongParam id) {
        return orderService.findById(id.get());
    }

    /**
     * Looks for orders whose first or last name contains the passed
     * parameter as a substring. If name argument was not passed, returns all
     * orders stored in the database.
     *
     * @param source query parameter
     * @return list of orders whose first or last name contains the passed
     * parameter as a substring or list of all orders stored in the database.
     */
    @GET
    @UnitOfWork
    public List<Order> findBySource(
            @QueryParam("source") Optional<String> source
    ) {
        return orderService.findBySource(source);
    }
}