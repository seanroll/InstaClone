package rain.finalproject.picshare.model;

import lombok.Getter;

import java.sql.Clob;

import static rain.finalproject.picshare.model.ClobMethods.clobToString;

@Getter
public class CommentRepr  {
    private Long id;
    private String commentString;
    private String ownerUsername;
    private Long unixTimestamp;
    private Long postId;
    private String date;

    public CommentRepr(Comment c){
        commentString = clobToString(c.getCommentData());
        ownerUsername = c.getOwnerUsername();
        unixTimestamp = c.getUnixTimestamp();
        postId = c.getPostId();
        date = c.getDate();
        id = c.getId();
    }
}
