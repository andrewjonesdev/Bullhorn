package byaj.repositories;

/**
 * Created by student on 6/20/17.
 */

import byaj.models.Like;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LikeRepository extends CrudRepository<Like, Integer> {
    public List<Like> findAllByLikeUser(int num);
}