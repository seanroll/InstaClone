package rain.finalproject;
import java.util.ArrayList;

class Comment {
    private String content;
    ArrayList<Comment> replies = new ArrayList<>();
    private int i = 0;
    public Comment(String c){
        content = c;
    }
    public String getContent(){
        return content;
    }
    public void Reply(String rc){
        replies.add(new Comment(("  " + rc)));
        i++;
    }
    public void AllReply(){
        for(int x = 0; x < replies.size(); x++){
            System.out.println(replies.get(x).getContent());
        }

    }
    public static void main(String[] args){
        Comment test = new Comment("Hello World");
        test.Reply("AHHHHH");
        test.Reply("WHY DOESN'T THIS WORK");
        test.AllReply();
    }
}