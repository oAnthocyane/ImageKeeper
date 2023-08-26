package antne.imagekeeper.resourceserver.service.group;

import antne.imagekeeper.resourceserver.model.Group;
import antne.imagekeeper.resourceserver.model.User;

/**
 * The interface Group service.
 *
 * <p>
 * Interface that implements business logic for interaction with group objects and the database.
 * </p>
 */
public interface GroupService {

    // TODO: add a password crypt to group.

    /**
     * Save group and return id this group.
     *
     * @param group the group
     * @param user  the user
     * @return the long
     */
    long save(Group group, User user);

    /**
     * Find by id group.
     *
     * @param id the id
     * @return the group
     */
    Group findById(long id);

    /**
     * Add user in group.
     *
     * @param group the group
     * @param user  the user
     * @return the user
     */
    boolean addUserInGroup(Group group, String groupPassword, User user);

    /**
     * Remove user in group and return group.
     *
     * @param group the group
     * @param user  the user
     * @return the user
     */
    boolean removeUserInGroup(Group group, User user);

    /**
     * Find by name group.
     *
     * @param name the name
     * @return the group
     */
    Group findByName(String name);
}
