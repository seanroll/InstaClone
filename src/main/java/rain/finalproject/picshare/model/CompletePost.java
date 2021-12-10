package rain.finalproject.picshare.model;

import lombok.*;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
public class CompletePost {
	private Long id;
	private String caption;

	private String pictureData;
	private String ownerUsername;
	private Long unixTimestamp;
	private String date;

	private ArrayList<CompleteComment> comments = new ArrayList<>(); // List of commentIDs that belong to this photo
}
