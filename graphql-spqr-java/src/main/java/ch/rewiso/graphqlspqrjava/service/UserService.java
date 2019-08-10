package ch.rewiso.graphqlspqrjava.service;

import ch.rewiso.graphqlspqrjava.model.Link;
import ch.rewiso.graphqlspqrjava.model.SigninPayload;
import ch.rewiso.graphqlspqrjava.model.User;
import ch.rewiso.graphqlspqrjava.model.Vote;
import ch.rewiso.graphqlspqrjava.respository.UserRepository;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@GraphQLApi
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @GraphQLQuery
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @GraphQLQuery(name = "postedBy")
    public User getPostedBy(@GraphQLContext Link link) {
        return userRepository.findById(link.getUserId()).orElse(null);
    }

    @GraphQLQuery(name= "user")
    public User userByVote(@GraphQLContext Vote vote) {
        return userRepository.findById(vote.getUserId()).orElse(null);
    }

}
