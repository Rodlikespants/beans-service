package resources;

import csv_importer.CsvImporterService;
import io.dropwizard.hibernate.UnitOfWork;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Optional;

@Path("/csvimporter")
@Produces(MediaType.APPLICATION_JSON)
public class CsvImporterResource {
    private CsvImporterService service;

    @Inject
    public CsvImporterResource(CsvImporterService service) {
        this.service = service;
    }

    @POST
    @UnitOfWork
    public Response importFile(
            @QueryParam("filename") Optional<String> filenameOpt,
            @QueryParam("source") Optional<String> sourceOpt
    ) {
        if (filenameOpt.isPresent() && sourceOpt.isPresent()) {
            try {
                service.importFile(filenameOpt.get(), sourceOpt.get());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return Response.ok().build();
        } else {
            throw new IllegalArgumentException("filename and source must be provided");
        }
    }
}
