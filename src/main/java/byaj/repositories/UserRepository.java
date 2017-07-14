package byaj.repositories;

import byaj.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UserRepository extends CrudRepository<User, Integer> {
    public User findByUsername(String username);
    public User findById(int id);
    public User findOneByUsername(String num);
    public List<User> findAllByFullNameOrderByIdAsc(String fullName);
    public List<User> findAllByOrderByLastNameAscFirstNameAsc();
    public List<User> findAllByUsernameOrderByUserDateDesc(String username);
    public List<User> findAllByOrderByUserDateDesc();
    public List<User> findAllByUsernameOrderByIdAsc(String username);
    User findByEmail(String email);
    int countByEmail(String email);
    int countByUsername(String username);

}
