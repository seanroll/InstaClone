package rain.finalproject.picshare.service;

import rain.finalproject.picshare.model.Post;
import rain.finalproject.picshare.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private PostRepository postRepository;

	@Override
	public void save(Post post) {
		postRepository.save(post);
	}

	@Override
	public Optional<Post> findById(Long id) {
		return postRepository.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		postRepository.deleteById(id);
	}

	@Override
	public Iterable<Post> getPosts() {
		return postRepository.findAll();
	}
}
