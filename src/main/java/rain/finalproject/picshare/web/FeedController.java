package rain.finalproject.picshare.web;

import org.hibernate.engine.jdbc.ClobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rain.finalproject.picshare.model.Comment;
import rain.finalproject.picshare.model.Post;
import rain.finalproject.picshare.model.User;
import rain.finalproject.picshare.service.*;
import rain.finalproject.Models.*;
import rain.finalproject.picshare.validator.UserValidator;
import org.springframework.stereotype.Controller;

import java.lang.annotation.Target;
import java.time.Instant;
import java.util.*;




/*
@Controller
public class FeedController {

    private PostService posts;
    private List<Post> temporary;

    @GetMapping("/MainPageDemo")
    public String MainPageD(Model model) {

      //  if (posts != null) {
        //    model.addAttribute("feed", posts.getPosts());
        //}
        return "Main/MainPageDemo";
    }*/

    /*
    @PostMapping("/MainPageDemo")
    public String MainPageDSorted(@RequestParam(name = "search", defaultValue = "") String search,
                                  @RequestParam(name = "searchMethod", defaultValue = "New") String method,
                                  Model model) {

        //Similar to the getmapping, this will not work until there is at least 1 post in the system

        postSearch searcher = new postSearch(search, method, Collections.singleton(posts.getPosts()));
        temporary = searcher.search();
        model.addAttribute("sortFeed", temporary);

        return "Main/MainPageDemo";
    }

}
*/