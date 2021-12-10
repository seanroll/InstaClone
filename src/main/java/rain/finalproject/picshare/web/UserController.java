package rain.finalproject.picshare.web;

import com.google.gson.Gson;
import org.hibernate.engine.jdbc.ClobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rain.finalproject.picshare.model.*;
import rain.finalproject.picshare.service.*;
import rain.finalproject.picshare.validator.UserValidator;
import org.springframework.stereotype.Controller;

import java.lang.annotation.Target;
import java.sql.Clob;
import java.time.Instant;
import java.util.*;

import static rain.finalproject.picshare.model.ClobMethods.clobToString;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private PostService postService;

	@Autowired
	private CommentService commentService;

	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("userForm", new User());

		return "PicShare/register";
	}

	@PostMapping("/register")
	public String register(@ModelAttribute("userForm") User userForm, Model model, BindingResult bindingResult) {
		userValidator.validate(userForm, bindingResult);
		if (bindingResult.hasErrors()) {
			return "PicShare/register";
		}

		userService.hashPassword(userForm);
		userService.addRoles(userForm);
		userService.save(userForm);
		securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

		return "redirect:/";
	}

	@GetMapping("/login")
	public String login(Model model, String error, String logout) {
		if (error != null) {
			model.addAttribute("error", "Your username and/or password is invalid.");
		}

		if (logout != null) {
			model.addAttribute("message", "You have been logged out successfully.");
		}

		return "PicShare/login";
	}


	@GetMapping("/feed")
	public String feed(Model model) {
		ArrayList<Post> Posts = (ArrayList<Post>)postService.getPosts();
		ArrayList<CompletePost> CompletePosts = new ArrayList<>();

		// Convert comments & posts to thymeleaf-friendly versions
		for (Post P : Posts) {
			CompletePost CP = new CompletePost();
			CP.setId(P.getId());
			CP.setCaption(clobToString(P.getCaption()));
			CP.setDate(P.getDate());
			CP.setOwnerUsername(P.getOwnerUsername());
			//CP.setPictureData(clobToString(P.getPictureData()));
			CP.setPictureData(""); // we're using a diff method for grabbing picturedata cuz i think it results in data that's
									// too long --> thymeleaf errors??
			CP.setUnixTimestamp(P.getUnixTimestamp());

			ArrayList<CompleteComment> CompleteComments = new ArrayList<>();

			for (Long CID : P.getComments()) {
				Optional<Comment> _C = commentService.findById(CID);
				if (!_C.isEmpty()) {
					Comment C = _C.get();
					CompleteComment CC = new CompleteComment();
					CC.setId(C.getId());
					CC.setCommentData(clobToString(C.getCommentData()));
					CC.setOwnerUsername(C.getOwnerUsername());
					CC.setUnixTimestamp(C.getUnixTimestamp());
					CC.setPostId(C.getPostId());
					CC.setDate(C.getDate());

					CompleteComments.add(CC);
				}
			}

			CompleteComments.sort(Comparator.comparing(CompleteComment::getUnixTimestamp).reversed());
			CP.setComments(CompleteComments);
			CompletePosts.add(CP);
		}

		CompletePosts.sort(Comparator.comparing(CompletePost::getUnixTimestamp).reversed());

		System.out.println(new Gson().toJson(CompletePosts));

		model.addAttribute("posts", CompletePosts);
		return "PicShare/feed";
	}

	@GetMapping("/getpostimage/{id}")
	public @ResponseBody String getPostImage(@PathVariable String id, Model model) {
		Long parsedId = 0L;
		try {
			parsedId = Long.parseLong(id);
		} catch (Exception Ex) {
			return "";
		}

		Optional<Post> TargetPost = postService.findById(parsedId);
		if (!TargetPost.isEmpty()) {
			return clobToString(TargetPost.get().getPictureData());
		}

		return "";
	}

	@GetMapping("/addpost")
	public String addPost(Model model) {
		String Username = securityService.findLoggedInUsername();
		if (Username != null) {
			User CurrentUser = userService.findByUsername(Username);
			if (CurrentUser != null) {
				return "PicShare/upload";
			}
		}
		return "PicShare/AccountRequired";
	}

	//-- Post endpoints
	@PostMapping("/addpost")
	public String addPost(@RequestParam String fileData,
						  @RequestParam String caption,
						  Model model) {
		String Username = securityService.findLoggedInUsername();
		if (Username != null) {
			User CurrentUser = userService.findByUsername(Username);
			if (CurrentUser != null) {
				// To-do: verify if fileData is correct;

				Post NewPost = new Post();

				NewPost.setPictureData(ClobProxy.generateProxy(fileData));
				NewPost.setCaption(ClobProxy.generateProxy(caption));
				NewPost.setOwnerUsername(Username);
				NewPost.setUnixTimestamp(Instant.now().getEpochSecond());
				NewPost.setDate(new Date(NewPost.getUnixTimestamp() * 1000).toString());
				postService.save(NewPost);

				Collection<Long> CurrentPostIds = CurrentUser.getPosts();
				CurrentPostIds.add(NewPost.getId());
				CurrentUser.setPosts(CurrentPostIds);

				userService.save(CurrentUser);

				model.addAttribute("result", "Successfully made a new post!");
				return "PicShare/upload";
			}
		}

		model.addAttribute("error", "You need to be logged in to make a post!");

		// For now, redirect to the main page (until we do html integration)
		return "PicShare/upload";
	}

	@PostMapping("/deletepost/{id}")
	public String deletePost(@PathVariable String id, Model model) {
		Long parsedId = 0L;
		try {
			parsedId = Long.parseLong(id);
		} catch (Exception Ex) {
			return "index";
		}
		String Username = securityService.findLoggedInUsername();
		if (Username != null) {
			User CurrentUser = userService.findByUsername(Username);
			if (CurrentUser != null) {
				Optional<Post> TargetPost = postService.findById(parsedId);
				if (!TargetPost.isEmpty() && TargetPost.get().getOwnerUsername().equals(Username)) {


					for (long CID : TargetPost.get().getComments()) {
						// delete all comments associated w/ this post
						Optional<Comment> TargetComment = commentService.findById(CID);
						if (!TargetComment.isEmpty()) {
							User CommentUser = userService.findByUsername(TargetComment.get().getOwnerUsername());

							Collection<Long> CommentUserComments = CommentUser.getComments();
							CommentUserComments.remove(CID);
							CommentUser.setComments(CommentUserComments);
							userService.save(CommentUser);

							commentService.deleteById(CID);
						}
					}

					Collection<Long> CurrentPosts = CurrentUser.getPosts();
					CurrentPosts.remove(parsedId);
					CurrentUser.setPosts(CurrentPosts);
					userService.save(CurrentUser);

					postService.deleteById(parsedId);
				}
			}
		}
		return "redirect:/feed";
	}

	//-- Comment endpoints
	@PostMapping("/addcomment")
	public String addComment(@RequestParam String commentData,
						  @RequestParam String postId,
						  Model model) {
		String Username = securityService.findLoggedInUsername();
		if (Username != null) {
			User CurrentUser = userService.findByUsername(Username);
			if (CurrentUser != null) {
				// Does a post exist w/ this id?
				Long parsedId = 0L;
				try {
					parsedId = Long.parseLong(postId);
				} catch (Exception Ex) {
					return "index";
				}
				Optional<Post> TargetPost = postService.findById(parsedId);
				if (!TargetPost.isEmpty()) {
					Comment NewComment = new Comment();
					NewComment.setCommentData(ClobProxy.generateProxy(commentData));
					NewComment.setOwnerUsername(Username);
					NewComment.setUnixTimestamp(Instant.now().getEpochSecond());
					NewComment.setDate(new Date(NewComment.getUnixTimestamp() * 1000).toString());
					NewComment.setPostId(TargetPost.get().getId());
					commentService.save(NewComment);

					Collection<Long> CurrentUserCommentIds = CurrentUser.getComments();
					CurrentUserCommentIds.add(NewComment.getId());
					CurrentUser.setComments(CurrentUserCommentIds);
					userService.save(CurrentUser);

					Collection<Long> CurrentPostCommentIds = TargetPost.get().getComments();
					CurrentPostCommentIds.add(NewComment.getId());
					TargetPost.get().setComments(CurrentPostCommentIds);
					postService.save(TargetPost.get());
				}
			}
		}

		return "redirect:/feed";
	}

	@PostMapping("/deletecomment/{id}")
	public String deleteComment(@PathVariable String id, Model model) {
		Long parsedId = 0L;
		try {
			parsedId = Long.parseLong(id);
		} catch (Exception Ex) {
			return "index";
		}
		String Username = securityService.findLoggedInUsername();
		if (Username != null) {
			User CurrentUser = userService.findByUsername(Username);
			if (CurrentUser != null) {
				Optional<Comment> TargetComment = commentService.findById(parsedId);
				if (!TargetComment.isEmpty() && TargetComment.get().getOwnerUsername().equals(Username)) {
					Collection<Long> CurrentUserComments = CurrentUser.getComments();
					CurrentUserComments.remove(parsedId);
					CurrentUser.setComments(CurrentUserComments);
					userService.save(CurrentUser);

					Optional<Post> TargetPost = postService.findById(TargetComment.get().getPostId());
					Collection<Long> CurrentPostComments = TargetPost.get().getComments();
					CurrentPostComments.remove(parsedId);
					TargetPost.get().setComments(CurrentPostComments);
					postService.save(TargetPost.get());

					commentService.deleteById(parsedId);
				}
			}
		}
		return "redirect:/feed";
	}
}
