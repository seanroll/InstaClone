package rain.finalproject.Models;

import lombok.Getter;
import org.w3c.dom.stylesheets.LinkStyle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/*
public class postSearch {

    private List<String> keywords;
    private String date = "";
    private String method = "";
    Iterable<Object> posts = new ArrayList<>();

    public postSearch(String keywords, String method, Iterable<Object> posts) {
        this.keywords = Arrays.asList(keywords.split(" "));
        //this.date = date;
        this.method = method;
        this.posts = posts;
    }

    public ArrayList search() {

        ArrayList<post> feed = (ArrayList<post>) posts.clone();
        ArrayList<post> temp = new ArrayList<>();

        if (date != null) {
            for (post singlepost : feed) {
                if (date.compareTo(singlepost.getDate()) >= 0) {temp.add(singlepost);}
            }
            feed = temp;
        }

        for (String keyword : keywords) {
            for (post singlepost : feed) {
                if ((singlepost.getTitle()).contains(keyword) || (singlepost.getDescription()).contains(keyword)) {
                temp.add(singlepost);
                }
            }
            feed = temp;
            temp.clear();
        }

        return feed;
    }

}

//temporary class to demonstrate use
@Getter
class post {
    private String title;
    private String description;
    private String date;
}
*/