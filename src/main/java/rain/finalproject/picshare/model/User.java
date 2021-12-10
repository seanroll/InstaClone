package rain.finalproject.picshare.model;

import lombok.*;
import javax.persistence.*;
import javax.persistence.ElementCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;
	private String password;

	@ElementCollection
	private Collection<Long> posts = new ArrayList<>(); // List of post IDs created by User

	@ElementCollection
	private Collection<Long> comments = new ArrayList<>();

	@Transient
	private String passwordConfirm;

	@ManyToMany
	private Set<Role> roles;
}
