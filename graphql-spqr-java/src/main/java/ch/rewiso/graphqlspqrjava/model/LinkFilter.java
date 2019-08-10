package ch.rewiso.graphqlspqrjava.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LinkFilter {

    private String descriptionContains = "any default string that is not contained for sure";
    private String urlContains = "any default string that is not contained for sure";

    @JsonProperty("description_contains") //the name must match the schema
    public String getDescriptionContains() {
        return descriptionContains;
    }

    public void setDescriptionContains(String descriptionContains) {
        this.descriptionContains = descriptionContains;
    }

    @JsonProperty("url_contains")
    public String getUrlContains() {
        return urlContains;
    }

    public void setUrlContains(String urlContains) {
        this.urlContains = urlContains;
    }
}
