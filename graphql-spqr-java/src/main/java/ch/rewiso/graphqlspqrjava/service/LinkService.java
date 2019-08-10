package ch.rewiso.graphqlspqrjava.service;

import ch.rewiso.graphqlspqrjava.model.Link;
import ch.rewiso.graphqlspqrjava.model.LinkFilter;
import ch.rewiso.graphqlspqrjava.model.User;
import ch.rewiso.graphqlspqrjava.model.Vote;
import ch.rewiso.graphqlspqrjava.respository.LinkRepository;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.annotations.GraphQLSubscription;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@GraphQLApi
@Service
public class LinkService {

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private final List<FluxSink<Link>> subscribers = new ArrayList<>();

    public List<Link> allLinks(LinkFilter filter, Integer skip, Integer first) {
        Query query = new Query();
        if (filter != null) {
            query.addCriteria(new Criteria().orOperator(
                    Criteria.where("description").regex(".*" + filter.getDescriptionContains() + ".*"),
                    Criteria.where("url").regex(".*" + filter.getUrlContains() + ".*"))
            );
        }
        if (skip != null) {
            query.skip(skip.longValue());
        }
        if (first != null) {
            query.limit(first.intValue());
        }
        List<Link> links = mongoTemplate.find(query, Link.class);
        return links;
    }

    @GraphQLQuery
    public Link link(String id) {
        return linkRepository.findById(id).orElse(null);
    }

    @GraphQLQuery(name = "link")
    public Link linkByVote(@GraphQLContext Vote vote) {
        return linkRepository.findById(vote.getLinkId()).orElse(null);
    }

    @GraphQLQuery
    public List<Link> linksByUser(@GraphQLContext User user) {
        return linkRepository.findByUserId(user.getId());
    }

    @GraphQLMutation(name = "create")
    public Link createLink(String url, String description) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication();
        if (user == null) throw new SecurityException("User is not authorized");
        ZonedDateTime now = Instant.now().atZone(ZoneOffset.UTC);
        Link newLink = new Link(now, url, description, user.getId());
        subscribers.forEach(subscriber -> subscriber.next(newLink)); //Notify all the subscribers
        linkRepository.save(newLink);
        return newLink;
    }

    @GraphQLSubscription
    public Publisher<Link> newLink() {
        return Flux.create(subscriber -> subscribers.add(subscriber.onDispose(() -> subscribers.remove(subscriber))), FluxSink.OverflowStrategy.LATEST);
    }

}
