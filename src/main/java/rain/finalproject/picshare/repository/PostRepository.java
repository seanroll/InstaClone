package rain.finalproject.picshare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rain.finalproject.picshare.model.Post;

public interface PostRepository extends JpaRepository<Post, Long>{
}
