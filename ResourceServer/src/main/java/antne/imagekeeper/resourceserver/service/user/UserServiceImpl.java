package antne.imagekeeper.resourceserver.service.user;

import antne.imagekeeper.resourceserver.exception.object.ObjectExistException;
import antne.imagekeeper.resourceserver.exception.object.ObjectNotFoundException;
import antne.imagekeeper.resourceserver.model.Group;
import antne.imagekeeper.resourceserver.model.ModelType;
import antne.imagekeeper.resourceserver.model.User;
import antne.imagekeeper.resourceserver.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type User service.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * Instantiates a new User service.
     *
     * @param userRepository the user repository
     */
    public UserServiceImpl(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Method to save to the database. Not safe, since the user may already be created,
     * an exception is thrown ObjectExistException when this script is executed.
     *
     * @param user the user
     * @return long user id
     */
    @Override
    public long save(User user) {
        log.info("Saving user: {}", user.getUsername());
        Optional<User> userInDB = userRepository.findByUserId(user.getUserId());
        if (userInDB.isPresent()) {
            log.error("User already exists: {}", user.getUsername());
            throw new ObjectExistException("User is exist", ModelType.User);
        }
        return userRepository.save(user).getId();
    }

    /**
     * Method to find user. Not safe as the given id may not exist.
     * <p>
     * If does not exist thrown ObjectNotFoundException.
     *
     * @param userId the userId
     * @return user the user
     */

    @Override
    public User findByUserId(long userId) {
        log.info("Finding user by userId: {}", userId);
        return checkOnExist(userRepository.findByUserId(userId));
    }

    /**
     * Method to leave user from group. Safe method.
     *
     * @param user  the user
     * @param group the group
     * @return user thr user
     */
    @Override
    public User leaveGroup(User user, Group group) {
        log.info("Leaving user {} from group: {}", user.getUsername(), group.getName());
        user.removeGroup(group);
        return userRepository.save(user);
    }

    /**
     * Method join user to group. Safe method.
     *
     * @param user  the user
     * @param group the group
     * @return user thr user
     */
    @Override
    public User joinToGroup(User user, Group group) {
        log.info("Joining user {} to group: {}", user.getUsername(), group.getName());
        user.addGroup(group);
        return userRepository.save(user);
    }

    @Override
    public List<Group> findGroupsByUserId(long userId) {
        User user = findByUserId(userId);
        return user.getGroups();
    }

    /**
     * Method on check exist in DB.
     *
     * @param Optional<User> object the safety data object.
     * @return Data object
     */
    private User checkOnExist(Optional<User> user) {
        if (user.isEmpty()) {
            log.error("User does not exist");
            throw new ObjectNotFoundException("User does not exist", ModelType.User);
        }
        return user.get();
    }

}
