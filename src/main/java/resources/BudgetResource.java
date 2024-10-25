package resources;

import io.dropwizard.hibernate.UnitOfWork;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Optional;

//@Path("/budget")
//@Produces(MediaType.APPLICATION_JSON)
//public class BudgetResource {
//    private BudgetService service;
//
//    @Inject
//    public BudgetResource(BudgetService service) {
//        this.service = service;
//    }
//
//    @POST
//    @UnitOfWork
//    public Response importFile(
////            @QueryParam("filename") Optional<String> filenameOpt,
////            @QueryParam("source") Optional<String> sourceOpt,
////            @QueryParam("email") Optional<String> userEmailOpt
//    ) {
//        if (filenameOpt.isPresent() && sourceOpt.isPresent()) {
//            try {
//                BudgetService.importFile();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            return Response.ok().build();
//        } else {
//            throw new IllegalArgumentException("filename and source must be provided");
//        }
//    }
//}
