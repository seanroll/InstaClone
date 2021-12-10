package rain.finalproject.picshare.model;

import lombok.*;
import javax.persistence.*;
import javax.persistence.ElementCollection;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "post")
@Getter
@Setter
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Lob
	private Clob caption;

	@Lob
	private Clob pictureData;
	private String ownerUsername;
	private Long unixTimestamp;
	private String date; // string version of 'unixTimestamp'

	@ElementCollection
	private Collection<Long> comments = new ArrayList<>(); // List of commentIDs that belong to this photo
}
