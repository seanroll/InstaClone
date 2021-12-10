package rain.finalproject.picshare.service;

import rain.finalproject.picshare.model.Comment;
import rain.finalproject.picshare.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRepository commentRepository;

	@Override
	public void save(Comment comment) {
		commentRepository.save(comment);
	}

	@Override
	public Optional<Comment> findById(Long id) {
		return commentRepository.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		commentRepository.deleteById(id);
	}

	@Override
	public Iterable<Comment> getComments() {
		return commentRepository.findAll();
	}
}
