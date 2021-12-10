package rain.finalproject.picshare.model;

import lombok.*;

@Getter
@Setter
public class CompleteComment {
	private Long id;

	private String commentData;
	private String ownerUsername;
	private Long unixTimestamp;
	private Long postId;
	private String date; // string version of 'unixTimestamp'
}
