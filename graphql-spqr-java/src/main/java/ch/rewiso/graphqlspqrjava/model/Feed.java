package ch.rewiso.graphqlspqrjava.model;

import java.util.List;

public class Feed {

    private List<Link> links;
    private int count;

    public Feed() {

    }

    public Feed(List<Link> links, int count) {
        this.links = links;
        this.count = count;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
