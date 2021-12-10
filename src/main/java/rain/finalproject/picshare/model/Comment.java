package rain.finalproject.picshare.model;

import lombok.*;
import javax.persistence.*;
import java.sql.Clob;

@Entity
@Table(name = "comment")
@Getter
@Setter
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Lob
	private Clob commentData;
	private String ownerUsername;
	private Long unixTimestamp;
	private Long postId;
	private String date; // string version of 'unixTimestamp'
}
