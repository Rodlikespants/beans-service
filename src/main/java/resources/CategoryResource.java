package resources;


import io.dropwizard.hibernate.UnitOfWork;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import services.CategoryService;

import javax.inject.Inject;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
public class CategoryResource {
    private CategoryService categoryService;

    @Inject
    public CategoryResource(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @POST
    @UnitOfWork
    public Response foo() {
        return Response.ok().build();
    }

    // TODO add an endpoint to add parent category
    // TODO add an endpoint to assign parent category to category
}
