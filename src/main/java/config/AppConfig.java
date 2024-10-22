package config;

import com.fasterxml.jackson.annotation.JsonProperty;
import csv_importer.CsvImporterService;
import csv_importer.processors.AmexCsvProcessor;
import csv_importer.processors.ChaseCsvProcessor;
import db.OrderDAO;
import db.PersonDAO;
import db.daos.BeansTransactionDAO;
import db.daos.UserDAO;
import io.dropwizard.core.Configuration;
import io.dropwizard.db.DataSourceFactory;
import resources.CsvImporterResource;
import resources.OrderResource;
import resources.PersonResource;
import services.OrderService;
import services.PersonService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class AppConfig extends Configuration implements DependencyInjectionConfiguration {
    @NotEmpty
    private String version;

    @JsonProperty
    public String getVersion() {
        return version;
    }

    @JsonProperty
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * The data configuration for MongoDB.
     */
    @Valid
    @NotNull
    private MongoDBConnection mongoDBConnection = new MongoDBConnection();


    @JsonProperty("mongoDBConnection")
    public MongoDBConnection getMongoDBConnection() {
        return mongoDBConnection;
    }

    @JsonProperty("mongoDBConnection")
    public void setMongoDBConnection(MongoDBConnection mongoDBConnection) {
        this.mongoDBConnection = mongoDBConnection;
    }

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.database = dataSourceFactory;
    }

    /**
     * I guess singletons are hardcoded here...
     * @return
     */
    public List<Class<?>> getSingletons() {
        // TODO is there a better way to do this
        final List<Class<?>> result = new ArrayList<>();
        result.add(PersonResource.class);
        result.add(PersonService.class);
        result.add(PersonDAO.class);
        result.add(OrderResource.class);
        result.add(OrderService.class);
        result.add(OrderDAO.class);
        result.add(CsvImporterResource.class);
        result.add(CsvImporterService.class);
        result.add(BeansTransactionDAO.class);
        result.add(UserDAO.class);
//        result.add(AmexCsvProcessor.class);
//        result.add(ChaseCsvProcessor.class);

        return result;
    }

    @Override
    public List<NamedProperty<? extends Object>> getNamedProperties() {
        final List<NamedProperty<? extends Object>> result = new ArrayList<>();
        result.add(new NamedProperty<>("dbUser", "dummy_db_user", String.class));

        return result;
    }
}

