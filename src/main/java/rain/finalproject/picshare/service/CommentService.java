package rain.finalproject.picshare.service;

import rain.finalproject.picshare.model.Comment;

import java.util.Optional;

public interface CommentService {
	void save(Comment comment);
	Optional<Comment> findById(Long id);
	void deleteById(Long id);
	Iterable<Comment> getComments();
}
