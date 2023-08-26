package antne.imagekeeper.resourceserver.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The type User.
 *
 * <p>
 * For telegram bot type user created when user click the button /start.
 * </p>
 * <p>
 * Each user has their own photos that are only available to them.
 * </p>
 * <p>
 * User can create a groups for add and find images together with other users who are in the group.
 * </p>
 */

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long userId;

    private String username;

    @Column(nullable = false)
    private Long chatId;

    @ManyToMany
    @JsonBackReference
    private List<Group> groups = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt = new Date();

    /**
     * Add group for user.
     *
     * @param group the group
     */
    public void addGroup(Group group) {
        groups.add(group);
        group.getUsers().add(this);
    }

    /**
     * Remove group for user.
     *
     * @param group the group
     */
    public void removeGroup(Group group) {
        groups.remove(group);
        group.getUsers().remove(this);
    }


}
