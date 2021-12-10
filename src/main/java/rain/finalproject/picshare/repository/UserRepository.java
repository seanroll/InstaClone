package rain.finalproject.picshare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rain.finalproject.picshare.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByUsername(String username);
}
