package ch.rewiso.graphqlspqrjava.model;

import io.leangen.graphql.annotations.GraphQLIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Vote {

    @Id
    private String id;
    private String userId;
    private String linkId;

    public Vote() {

    }

    public Vote(String userId, String linkId) {
        this(null, userId, linkId);
    }

    public Vote(String id, String userId, String linkId) {
        this.id = id;
        this.userId = userId;
        this.linkId = linkId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @GraphQLIgnore
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @GraphQLIgnore
    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }
}
