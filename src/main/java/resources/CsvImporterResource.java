package resources;

import csv_parser.CsvImporterService;
import csv_parser.CsvParserUtil;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;
import models.Order;
import services.OrderService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/csvimporter")
@Produces(MediaType.APPLICATION_JSON)
public class CsvImporterResource {
    private CsvImporterService service;

    /**
     * Constructor.
     *
     * @param orderService DAO object to manipulate orders.
     */
    @Inject
    public CsvImporterResource(CsvImporterService service) {
        this.service = service;
    }

    @POST
    @UnitOfWork
    public Response importFile() {
        service.importFile();
        return Response.ok().build();
    }
}
