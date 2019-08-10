package ch.rewiso.graphqlspqrjava.model;

import io.leangen.graphql.annotations.GraphQLIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Document
public class User implements Authentication {

    @Id
    private String id;
    private String name;
    private String email;
    private String password;

    public User() {
    }

    public User(String name, String email, String password) {
        this(null, name, email, password);
    }

    public User(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @GraphQLIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @GraphQLIgnore
    @Override
    public Object getCredentials() {
        return null;
    }

    @GraphQLIgnore
    @Override
    public Object getDetails() {
        return null;
    }

    @GraphQLIgnore
    @Override
    public Object getPrincipal() {
        return null;
    }

    @GraphQLIgnore
    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @GraphQLIgnore
    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {

    }
}
