package ch.rewiso.graphqlspqrjava.service;

import ch.rewiso.graphqlspqrjava.model.*;
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

import java.util.ArrayList;
import java.util.List;

@GraphQLApi
@Service
public class FeedService {

    @Autowired
    private LinkService linkService;


    @GraphQLQuery
    public Feed feed(LinkFilter filter, Integer skip, Integer first) {
        List<Link> links = linkService.allLinks(filter, skip, first);
        return new Feed(links, links.size());
    }



}
