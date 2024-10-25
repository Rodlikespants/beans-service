package resources;

import csv_importer.CsvImporterService;
import io.dropwizard.hibernate.UnitOfWork;

import javax.inject.Inject;
import javax.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.util.Optional;

@Path("/api/v1/csvimporter")
@Produces(MediaType.APPLICATION_JSON)
//@Singleton
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
            @QueryParam("source") Optional<String> sourceOpt,
            @QueryParam("email") Optional<String> userEmailOpt
    ) {
        if (filenameOpt.isPresent() && sourceOpt.isPresent()) {
            try {
                service.importFile(filenameOpt.get(), sourceOpt.get(), userEmailOpt.get());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return Response.ok().build();
        } else {
            throw new IllegalArgumentException("filename and source must be provided");
        }
    }
}
