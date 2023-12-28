package config;

import java.util.Objects;

public class Seed {

    /** The host.*/
    private String host;

    /** The port.*/
    private int port;
    //getters, setters, hashcode and equals methods.

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seed seed = (Seed) o;
        return port == seed.port && Objects.equals(host, seed.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, port);
    }
}
