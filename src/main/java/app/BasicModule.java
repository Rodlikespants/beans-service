package app;

import com.google.inject.AbstractModule;
import config.AppConfig;
import db.MongoDBFactoryConnection;
import db.MongoDBManaged;
import db.OrderDAO;
import db.PersonDAO;
import db.daos.BeansTransactionDAO;
import db.daos.categories.CategoryDAO;
import db.daos.categories.ParentCategoryDAO;
import db.daos.UserDAO;
import healthchecks.DropwizardMongoDBHealthCheck;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.core.setup.Environment;

public class BasicModule extends AbstractModule {
    private AppConfig config;
    private Environment env;

    private HibernateBundle<AppConfig> hibernateBundle;

    public BasicModule(AppConfig config, Environment env, HibernateBundle<AppConfig> hibernateBundle) {
        this.config = config;
        this.env = env;
        this.hibernateBundle = hibernateBundle;
    }

    /**
     * Add all entities here
     */

    @Override
    protected void configure() {

        final MongoDBFactoryConnection mongoDBManagerConn = new MongoDBFactoryConnection(config.getMongoDBConnection());

        final MongoDBManaged mongoDBManaged = new MongoDBManaged(mongoDBManagerConn.getClient());

        final PersonDAO personDao = new PersonDAO(
                mongoDBManagerConn.getClient()
                        .getDatabase(
                                config.getMongoDBConnection().getDatabase()).getCollection("persons")); // TODO complete
        /*
        new DonutDAO(mongoDBManagerConn.getClient()
                .getDatabase(configuration.getMongoDBConnection().getDatabase())
                .getCollection("donuts"));
         */

        bind(PersonDAO.class).toInstance(personDao);

        final OrderDAO orderDao = new OrderDAO(hibernateBundle.getSessionFactory());
        bind(OrderDAO.class).toInstance(orderDao);

        final BeansTransactionDAO beansTransactionDao = new BeansTransactionDAO(hibernateBundle.getSessionFactory());
        bind(BeansTransactionDAO.class).toInstance(beansTransactionDao);

        final CategoryDAO categoryDao = new CategoryDAO(hibernateBundle.getSessionFactory());
        bind(CategoryDAO.class).toInstance(categoryDao);

        final ParentCategoryDAO parentCategoryDAO = new ParentCategoryDAO(hibernateBundle.getSessionFactory());
        bind(ParentCategoryDAO.class).toInstance(parentCategoryDAO);

        final UserDAO userDao = new UserDAO(hibernateBundle.getSessionFactory());
        bind(UserDAO.class).toInstance(userDao);

        env.lifecycle().manage(mongoDBManaged);
//        env.jersey().register(new DonutResource(donutDAO));
        env.healthChecks().register("DropwizardMongoDBHealthCheck",
                new DropwizardMongoDBHealthCheck(mongoDBManagerConn.getClient()));
    }
}
