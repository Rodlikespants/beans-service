package config;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jackson.PasswordSerializer;

import java.util.Arrays;
import java.util.Objects;

public class Credentials {

    /** The user name.*/
    private String username;

    /** The password.*/
    @JsonSerialize(using = PasswordSerializer.class)
    private char[] password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credentials that = (Credentials) o;
        return Objects.equals(username, that.username) && Arrays.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(username);
        result = 31 * result + Arrays.hashCode(password);
        return result;
    }
}