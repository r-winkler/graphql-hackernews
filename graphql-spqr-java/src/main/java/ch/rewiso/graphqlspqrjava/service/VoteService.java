package ch.rewiso.graphqlspqrjava.service;

import ch.rewiso.graphqlspqrjava.model.Link;
import ch.rewiso.graphqlspqrjava.model.User;
import ch.rewiso.graphqlspqrjava.model.Vote;
import ch.rewiso.graphqlspqrjava.respository.VoteRepository;
import io.leangen.graphql.annotations.*;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
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
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    private final List<FluxSink<Vote>> subscribers = new ArrayList<>();

    @GraphQLQuery(name = "votes")
    public List<Vote> findVotesByLink(@GraphQLContext Link link) {
        return voteRepository.findByLinkId(link.getId());
    }

    @GraphQLMutation(name = "vote")
    public Vote createVote(@GraphQLId String linkId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication();
        if (user == null) throw new SecurityException("User is not authorized");
        Vote newVote = new Vote(user.getId(), linkId);
        subscribers.forEach(subscriber -> subscriber.next(newVote)); //Notify all the subscribers
        return voteRepository.save(newVote);
    }

    @GraphQLSubscription
    public Publisher<Vote> newVote() {
        return Flux.create(subscriber -> subscribers.add(subscriber.onDispose(() -> subscribers.remove(subscriber))), FluxSink.OverflowStrategy.LATEST);
    }
}
