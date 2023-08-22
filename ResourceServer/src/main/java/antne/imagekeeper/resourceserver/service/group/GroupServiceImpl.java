package antne.imagekeeper.resourceserver.service.group;

import antne.imagekeeper.resourceserver.exception.object.ObjectExistException;
import antne.imagekeeper.resourceserver.exception.object.ObjectNotFoundException;
import antne.imagekeeper.resourceserver.model.Group;
import antne.imagekeeper.resourceserver.model.ModelType;
import antne.imagekeeper.resourceserver.model.User;
import antne.imagekeeper.resourceserver.repository.GroupRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The type Group service.
 */
@Slf4j
@Service
public class GroupServiceImpl implements GroupService {

    private GroupRepository groupRepository;

    /**
     * Instantiates a new Group service.
     *
     * @param groupRepository the group repository
     */
    public GroupServiceImpl(@Autowired GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    /**
     * Method to find group by id. Not safe as the given group may not exist.
     *
     * Thrown ObjectNotFoundException.
     *
     * @param id the id group
     * @return group the group
     */
    @Override
    public Group findById(long id) {
        return checkOnExist(groupRepository.findById(id));
    }

    /**
     * Method to save to the database. Not safe, since the group may already be created,
     * an exception is thrown ObjectExistException when this script is executed.
     *
     * @param user the user
     * @return long user id
     */
    @Override
    public long save(Group group, User user) {
        log.info("Saving group: {}", group.getName());
        if(groupRepository.findByName(group.getName()).isPresent()) {
            log.error("Group already exists: {}", group.getName());
            throw new ObjectExistException(
                    "Group is exist",
                    ModelType.Group
            );
        }
        group.setAdminUser(user);
        group.addUser(user);
        return groupRepository.save(group).getId();
    }

    /**
     * Method add user to group. Safe method.
     *
     * @param group the group
     * @param user  the user
     * @return group the group
     */
    @Override
    public boolean addUserInGroup(Group group, String groupPassword, User user) {
        if(!group.getPassword().equals(groupPassword))
            throw new ObjectNotFoundException("Error password group", ModelType.Group);
        group.addUser(user);
        groupRepository.save(group);
        return true;
    }

    /**
     * Method remove user from group. Safe method.
     *
     * @param group the group
     * @param user  the user
     * @return group the group
     */
    @Override
    public boolean removeUserInGroup(Group group, User user) {
        log.info("Removing user {} from group: {}", user.getUsername(), group.getName());
        group.removeUser(user);
        if(group.getUsers().size() == 0) {
            groupRepository.delete(group);
            log.info("Group was deleted as it became empty: {}", group.getName());
        }
        else{
            groupRepository.save(group);
            log.info("Group was updated after removing user: {}", group.getName());
        }
        return true;
    }

    /**
     * Method to find group. Not safe as the given group may not exist.
     *
     * Thrown ObjectNotFoundException.
     *
     * @param name the name group
     * @return group the group
     */
    @Override
    public Group findByName(String name){
        return checkOnExist(groupRepository.findByName(name));
    }

    /**
     * Method on check exist in DB. If does not exist thrown ObjectNotFoundException.
     *
     * @param Optional<Group> group the safety group object.
     * @return User user
     */
    private Group checkOnExist(Optional<Group> group){
        if(group.isEmpty()) throw new ObjectNotFoundException("Group does not exist", ModelType.Group);
        return group.get();
    }
}
