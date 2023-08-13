package antne.imagekeeper.resourceserver.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Group.
 * <p>
 * Group can be created by user and in this group users can join if they write correct name group and password.
 * </p>
 * <p>
 * User can create many groups and each user can add new images and search all images in this group.
 * </p>
 */
@Entity
@Table(name = "groups")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Field must not be blank")
    @Pattern(regexp = "^[^\\s]+$", message = "Field must not contain spaces")
    @Column(unique = true)
    private String name;

    @NotBlank(message = "Field must not be blank")
    @Pattern(regexp = "^[^\\s]+$", message = "Field must not contain spaces")
    private String password;

    @ManyToOne
    private User adminUser;

    private String linkToJoin;
    
    @ManyToMany(mappedBy = "groups", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<User> users = new ArrayList<>();

    /**
     * Add user.
     *
     * @param user the user
     */
    public void addUser(User user) {
        users.add(user);
        user.getGroups().add(this);
    }

    /**
     * Remove user.
     *
     * @param user the user
     */
    public void removeUser(User user) {
        users.remove(user);
        user.getGroups().remove(this);
    }

}

