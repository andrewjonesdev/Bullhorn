package byaj.repositories;

/**
 * Created by student on 6/20/17.
 */

import byaj.models.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Integer> {
    public List<Post> findAllByPostUserOrderByPostDateDesc(int num);
    public List<Post> findAllByOrderByPostDateDesc();
    public List<Post> findAllByPostAuthorOrderByPostDateDesc(String username);
    public Post findByPostID(int num);
}