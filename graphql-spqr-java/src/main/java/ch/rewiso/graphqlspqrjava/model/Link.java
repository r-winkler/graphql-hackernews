package ch.rewiso.graphqlspqrjava.model;

import io.leangen.graphql.annotations.GraphQLIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;

@Document
public class Link {

    @Id
    private String id;
    private String url;
    private String description;
    private String userId;
    private ZonedDateTime createdAt;

    public Link() {

    }

    public Link(ZonedDateTime createdAt, String url, String description, String userId) {
        this(null, createdAt, url, description, userId);
    }

    public Link(String id, ZonedDateTime createdAt, String url, String description, String userId) {
        this.id = id;
        this.url = url;
        this.description = description;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @GraphQLIgnore
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
