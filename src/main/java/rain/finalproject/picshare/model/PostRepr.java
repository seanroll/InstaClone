package rain.finalproject.picshare.model;

import lombok.Getter;

import java.sql.Clob;
import java.util.Collection;

import static rain.finalproject.picshare.model.ClobMethods.clobToString;

@Getter
public class PostRepr {
    private Long id;
    private String caption;
    private String pictureData;
    private String ownerUsername;
    private Long unixTimestamp;
    private String date;
    private Collection<Long> comments;

    public PostRepr(Post p){
        caption = clobToString(p.getCaption());
        pictureData = clobToString(p.getPictureData()).substring(0,51);
        ownerUsername = p.getOwnerUsername();
        unixTimestamp = p.getUnixTimestamp();
        date = p.getDate();
        comments = p.getComments();
        id = p.getId();
    }
}
