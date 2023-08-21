package antne.imagekeeper.resourceserver.service.user;

import antne.imagekeeper.resourceserver.model.Group;
import antne.imagekeeper.resourceserver.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * The interface User service.
 *
 * <p>
 * Interface that implements business logic for interaction with group objects and the database
 * </p>
 */
public interface UserService {
    /**
     * Save user and return id this user.
     *
     * @param user the user
     * @return the long
     */
    long save(User user);

    /**
     * Find by userId and return this user.
     *
     * @param userId the userId
     * @return the user
     */
    User findByUserId(long userId);

    /**
     * Leave group for user and return user.
     *
     * @param user  the user
     * @param group the group
     * @return the user
     */
    User leaveGroup(User user, Group group);

    /**
     * Add group to user and return user.
     *
     * @param user  the user
     * @param group the group
     * @return the user
     */
    User joinToGroup(User user, Group group);

    List<Group> findGroupsByUserId(long userId);
}
