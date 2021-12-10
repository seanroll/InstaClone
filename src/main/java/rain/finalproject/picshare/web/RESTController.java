package rain.finalproject.picshare.web;

import com.google.gson.Gson;
import com.sun.xml.bind.v2.runtime.output.SAXOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rain.finalproject.mjson.Json;
import rain.finalproject.picshare.model.*;
import rain.finalproject.picshare.repository.CommentRepository;
import rain.finalproject.picshare.repository.PostRepository;
import rain.finalproject.picshare.repository.UserRepository;
import rain.finalproject.picshare.service.CommentService;
import rain.finalproject.picshare.service.PostService;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.*;

import static rain.finalproject.picshare.model.ClobMethods.clobToString;

@Controller
public class RESTController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;


    @GetMapping("/crossoverAPI")
    public String crossoverAPI() {
        return "REST_API/crossoverAPI";
    }

    //returns the data of a post with a given id
    @GetMapping ("/getPost")
    public String getPost(@RequestParam Long id , Model model){
        try{
        PostRepr postData = new PostRepr(postRepository.getOne(id));
        model.addAttribute("postData", postData);
        return "REST_API/getPost";}
        catch (Exception e){
            return "REST_API/APIerror";
        }
    }

    //returns the data of a comment with a given id
    @GetMapping("/getComment")
    public String getComment(@RequestParam Long id , Model model){
        try{
        CommentRepr commentData = new CommentRepr(commentRepository.getOne(id));
        model.addAttribute("commentData", commentData);
        return "REST_API/getComment";}
        catch (Exception e){
            return "REST_API/APIerror";
        }
    }

    //returns the data of a user with a given id
    @GetMapping("/getUser")
    public String getUser(@RequestParam Long id, Model model){
        try{
        UserRepr userData = new UserRepr(userRepository.getOne(id));
        model.addAttribute("userData", userData);
        return "REST_API/getUser";}
        catch (Exception e){
            return "REST_API/APIerror";
        }
    }

    //returns the data of a post whose caption contains a given phrase
    //default value will return all posts
    @GetMapping("/searchPost")
    public String searchPost(@RequestParam(name = "phrase", defaultValue = "", required = true) String phrase, Model model){

        ArrayList<PostRepr> postsSearchData = new ArrayList<PostRepr>();

        for(Post p : postRepository.findAll()){
            if (clobToString(p.getCaption()).toLowerCase().contains(phrase.toLowerCase())){
                postsSearchData.add(new PostRepr(p));
            }
        }

        model.addAttribute("postSearchData", postsSearchData);
        return "REST_API/searchPost";
    }

    //returns the data of a comment whose content contains a given phrase
    //default value will return all comments
    @GetMapping("/searchComment")
    public String searchComment(@RequestParam(name = "phrase", defaultValue = "", required = true) String phrase, Model model){

        ArrayList<CommentRepr> commentSearchData = new ArrayList<CommentRepr>();

        for(Comment c : commentRepository.findAll()){
            if (clobToString(c.getCommentData()).toLowerCase().contains(phrase.toLowerCase())){
                commentSearchData.add(new CommentRepr(c));
            }
        }

        model.addAttribute("commentSearchData", commentSearchData);
        return "REST_API/searchComment";
    }


    //JSON API
    @GetMapping("/_getPost")
    public @ResponseBody String _getPost(@RequestParam Long id, Model model){
        String postData = new Gson().toJson(new PostRepr(postRepository.getOne(id)));
        model.addAttribute("postData", postData);
        return postData;

    }

    //returns the data of a comment with a given id
    @GetMapping("/_getComment")
    public  @ResponseBody String _getComment(@RequestParam Long id, Model model){
        String commentData = new Gson().toJson(new CommentRepr(commentRepository.getOne(id)));
        model.addAttribute("commentData", commentData);
        return commentData;
    }

    //returns the data of a user with a given id
    @GetMapping("/_getUser")
    public @ResponseBody String _getUser(@RequestParam Long id, Model model){
        System.out.println("mapping received");
        String userData = new Gson().toJson(new UserRepr(userRepository.getOne(id)));
        model.addAttribute("userData", userData);
        return userData;
    }

    //returns the data of a post whose caption contains a given phrase
    //default value will return all posts
    @GetMapping("/_searchPost")
    public @ResponseBody String _searchPost(@RequestParam(name = "phrase", defaultValue = "", required = true) String phrase, Model model){

        ArrayList<PostRepr> posts = new ArrayList<PostRepr>();

        for(Post p : postRepository.findAll()){
            if (clobToString(p.getCaption()).toLowerCase().contains(phrase.toLowerCase())){
                posts.add(new PostRepr(p));
            }
        }

        String postSearchData = new Gson().toJson(posts);
        model.addAttribute("postSearchData", postSearchData);
        return postSearchData;
    }

    //returns the data of a comment whose content contains a given phrase
    //default value will return all comments
    @GetMapping("/_searchComment")
    public @ResponseBody String _searchComment(@RequestParam(name = "phrase", defaultValue = "", required = true) String phrase, Model model){

        ArrayList<CommentRepr> comments = new ArrayList<CommentRepr>();

        for(Comment c : commentRepository.findAll()){
            if (clobToString(c.getCommentData()).toLowerCase().contains(phrase.toLowerCase())){
                comments.add(new CommentRepr(c));
            }
        }

        String commentSearchData = new Gson().toJson(comments);
        model.addAttribute("commentSearchData", commentSearchData);
        return commentSearchData;
    }

    public String StreamToString(InputStream IS) throws IOException {
        BufferedReader BR = new BufferedReader(new InputStreamReader(IS));
        String Line;
        StringBuffer Content = new StringBuffer();
        while ((Line = BR.readLine()) != null) {
            Content.append(Line);
        }
        BR.close();
        return Content.toString();
    }

    //-- Crossover API
    @GetMapping("/_crossoverGetAll")
    public @ResponseBody String _crossoverGetAll() {
        try {
            URL GETURL = new URL("https://snakeeyes5.herokuapp.com/studyGuide");
            HttpURLConnection Con = (HttpURLConnection)GETURL.openConnection();
            Con.setRequestMethod("GET");
            return StreamToString(Con.getInputStream());
        } catch (Exception Ex) {
            return "Crossover API is down.";
        }
    }

    @GetMapping("/_crossoverGet/{id}")
    public @ResponseBody String _crossoverGet(@PathVariable String id) {
        Long parsedId = 0L;
        try {
            parsedId = Long.parseLong(id);
        } catch (Exception Ex) {
            return "Invalid ID.";
        }

        try {
            URL GETURL = new URL("https://snakeeyes5.herokuapp.com/studyGuide/" + parsedId);
            HttpURLConnection Con = (HttpURLConnection)GETURL.openConnection();
            Con.setRequestMethod("GET");
            return StreamToString(Con.getInputStream());
        } catch (Exception Ex) {
            return "Crossover API is down.";
        }
    }

    //@PostMapping("/_crossoverPost")
    //@RequestMapping(value = "/_crossoverPost", method = RequestMethod.POST, consumes = "application/json")
    @GetMapping("/_crossoverPost")
    public @ResponseBody String _crossoverPost(@RequestParam(value="unit") String unit, @RequestParam(value="content") String content) {
        try {
            URL POSTURL = new URL("https://snakeeyes5.herokuapp.com/studyGuide");
            HttpURLConnection Con = (HttpURLConnection)POSTURL.openConnection();
            Con.setRequestMethod("POST");
            Con.setRequestProperty("Content-Type", "application/json; utf-8");
            Con.setRequestProperty("Accept", "application/json");

            Con.setDoOutput(true);
            String JSONInputString = "{\"unit\": \"" + unit + "\", \"content\": \"" + content + "\"}";
            try (OutputStream OS = Con.getOutputStream()) {
                byte[] Input = JSONInputString.getBytes("utf-8");
                OS.write(Input, 0, Input.length);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(Con.getInputStream(), "utf-8"))) {
                StringBuilder Response = new StringBuilder();
                String ResponseLine = null;
                while ((ResponseLine = br.readLine()) != null) {
                    Response.append(ResponseLine.trim());
                }
                return Response.toString();
            }
        } catch (Exception Ex) {
            return "Crossover API is down.";
        }
    }

    @GetMapping("/_crossoverDelete/{id}")
    public @ResponseBody String _crossoverDelete(@PathVariable String id) {
        Long parsedId = 0L;
        try {
            parsedId = Long.parseLong(id);
        } catch (Exception Ex) {
            return "Invalid ID.";
        }

        try {
            URL GETURL = new URL("https://snakeeyes5.herokuapp.com/studyGuide/" + parsedId);
            HttpURLConnection Con = (HttpURLConnection)GETURL.openConnection();
            Con.setRequestMethod("DELETE");
            if (Con.getResponseCode() != 200) {
                return "Error deleting study guide ID " + id;
            } else {
                return "Successfully deleted study guide ID " + id;
            }
        } catch (Exception Ex) {
            return "Crossover API is down.";
        }
    }
}
