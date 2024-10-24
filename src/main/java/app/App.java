package app;

import com.google.inject.Guice;
import com.google.inject.Injector;
import config.AppConfig;
import db.OrderDAO;
import db.entities.OrderEntity;
import db.entities.categories.CategoryEntity;
import db.entities.categories.ParentCategoryEntity;
import db.entities.transactions.BeansTransactionEntity;
import healthchecks.AppHealthCheck;
import io.dropwizard.core.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import resources.CategoryResource;
import resources.CsvImporterResource;
import resources.OrderResource;
import resources.PersonResource;

/**
 * java -jar sample-dropwizard-rest-stub-1.0-SNAPSHOT.jar server ../config.yml
 *
 */
public class App extends Application<AppConfig> {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    // TODO is there a better way than adding every entity class here?
    private final HibernateBundle<AppConfig> hibernate = new HibernateBundle<AppConfig>(
            OrderEntity.class,
            BeansTransactionEntity.class,
            CategoryEntity.class,
            ParentCategoryEntity.class
    ) {
        @Override
        public DataSourceFactory getDataSourceFactory(AppConfig configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    @Override
    public void initialize(Bootstrap<AppConfig> bootstrap) {
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(AppConfig config, Environment env) throws Exception {
        final DependencyInjectionBundle dependencyInjectionBundle = new DependencyInjectionBundle();
        dependencyInjectionBundle.run(config, env);



        BasicModule module = new BasicModule(config, env, hibernate);
        Injector injector = Guice.createInjector(module);

        env.jersey().register(injector.getInstance(PersonResource.class));
        env.jersey().register(injector.getInstance(OrderResource.class));
        env.jersey().register(injector.getInstance(CsvImporterResource.class));
        env.jersey().register(injector.getInstance(CategoryResource.class));

        env.healthChecks().register("template",
                new AppHealthCheck(config.getVersion()));
    }
    public static void main( String[] args ) throws Exception {
        LOGGER.info("About to run app...");
        new App().run(args);
    }
}
