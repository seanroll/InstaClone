package rain.finalproject.picshare.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;

@Getter
public class UserRepr {
    private Long id;
    private String username;
    private Collection<Long> posts;
    private Collection<Long> comments;

    public UserRepr(User u){
        id = u.getId();
        username = u.getUsername();
        posts = u.getPosts();
        comments = u.getComments();
    }
}
