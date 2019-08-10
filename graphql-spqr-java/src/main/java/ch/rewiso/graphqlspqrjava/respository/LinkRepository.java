package ch.rewiso.graphqlspqrjava.respository;

import ch.rewiso.graphqlspqrjava.model.Link;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkRepository extends MongoRepository<Link, String> {

    List<Link> findByUserId(String userId);

    List<Link> findByDescriptionContainingOrUrlContaining(String descriptionContaining, String urlContaining);

}
