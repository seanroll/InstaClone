package rain.finalproject.picshare.service;

import rain.finalproject.picshare.model.Post;

import java.util.Optional;

public interface PostService {
	void save(Post post);
	Optional<Post> findById(Long id);
	void deleteById(Long id);
	Iterable<Post> getPosts();
}
