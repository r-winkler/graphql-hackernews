package ch.rewiso.graphqlspqrjava.respository;

import ch.rewiso.graphqlspqrjava.model.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends MongoRepository<Vote, String> {

    List<Vote> findByLinkId(String linkId);
}
