package resources;

import io.dropwizard.hibernate.UnitOfWork;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import models.Budget;
import models.transactions.BeansTransaction;
import services.BudgetService;

import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@Path("/api/v1/budgets")
@Produces(MediaType.APPLICATION_JSON)
public class BudgetResource {
    private BudgetService service;

    @Inject
    public BudgetResource(BudgetService service) {
        this.service = service;
    }

    @GET
    @Path("/{userId}")
    @UnitOfWork
    public Response getBudget(
            @PathParam("userId") Long userId,
            @QueryParam("month") Optional<Integer> monthOpt,
            @QueryParam("year") Optional<Integer> yearOpt,
            @QueryParam("source") Optional<String> sourceOpt
    ) {
        // TODO hardcoding source
        if (yearOpt.isEmpty() || monthOpt.isEmpty()) {
            throw new IllegalArgumentException("Year and Month must be present");
        }
        LocalDate startDate = LocalDate.of(yearOpt.get(), monthOpt.get(), 1);
        LocalDate endDate = startDate.withDayOfMonth(
                startDate.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth()
        );
        List<Budget> budgets = service.getAllBudgets(userId, startDate, endDate, BeansTransaction.Source.AMEX);
        return Response.ok(budgets).build();
    }
}
