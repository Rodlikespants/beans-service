package config;

import java.util.List;

public class MongoDBConnection {
    /**
     * The credentials user and password.
     */
    private Credentials credentials;

    /**
     * The lis of seeds.
     */
    private List<Seed> seeds;

    /**
     * The db.
     */
    private String database;

    public Credentials getCredentials() {
        return credentials;
    }

    public List<Seed> getSeeds() {
        return seeds;
    }

    public String getDatabase() {
        return database;
    }
}