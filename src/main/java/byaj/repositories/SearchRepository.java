package byaj.repositories;

/**
 * Created by student on 6/20/17.
 */

import byaj.models.Search;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SearchRepository extends CrudRepository<Search, Integer> {
    public List<Search> findAllBySearchUser(int num);
    public Search findBySearchAuthorOrderBySearchDateDesc(String author);
}