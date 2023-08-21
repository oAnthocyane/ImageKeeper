package antne.imagekeeper.resourceserver.controller;

import antne.imagekeeper.resourceserver.model.Group;
import antne.imagekeeper.resourceserver.model.User;
import antne.imagekeeper.resourceserver.response.ResponseHandler;
import antne.imagekeeper.resourceserver.service.group.GroupService;
import antne.imagekeeper.resourceserver.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * The type Group controller.
 *
 * <p>
 * Class for controls requests for rest api by request /api/groups for group
 * </p>
 */
@Slf4j
@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;
    private final UserService userService;

    /**
     * Instantiates a new Group controller.
     *
     * @param groupService the group service
     * @param userService  the user service
     */
    public GroupController(@Autowired GroupService groupService, @Autowired UserService userService) {
        this.groupService = groupService;
        this.userService = userService;
    }

    /**
     * Add group and return id by request /api/groups/save.
     *
     * @param group       the group
     * @param adminUserId the admin user id
     * @return the long
     */
    @PostMapping("/save/{adminUserId}")
    public ResponseEntity<Object> addGroup(@RequestBody Group group, @PathVariable long adminUserId) {
        User adminUser = userService.findByUserId(adminUserId);
        long groupId = groupService.save(group, adminUser);
        return ResponseHandler.responseBuilder("Group was added", HttpStatus.CREATED, groupId);
    }

    /**
     * Add user to group and group to user and return group by request /api/groups/{groupName}/join/{userId}.
     *
     * @param groupName the group id
     * @param userId    the user id
     * @return the user
     */
    @PostMapping("/{groupName}/join/{userId}")
    public ResponseEntity<Object> joinGroup(@PathVariable String groupName, @PathVariable long userId) {
        User user = userService.findByUserId(userId);
        Group group = groupService.findByName(groupName);
        Group modifiedGroup = groupService.addUserInGroup(group, user);
        return ResponseHandler.responseBuilder("User was joined to group", HttpStatus.CREATED, modifiedGroup);
    }

    /**
     * Remove group from user and user from group and return group by request /api/groups/{groupName}/join/{userId}.
     *
     * @param groupName the group id
     * @param userId    the user id
     * @return the user
     */
    @PostMapping("/{groupName}/leave/{userId}")
    public ResponseEntity<Object> leaveGroup(@PathVariable String groupName, @PathVariable long userId) {
        User user = userService.findByUserId(userId);
        Group group = groupService.findByName(groupName);
        boolean isRemoved = groupService.removeUserInGroup(group, user);
        return ResponseHandler.responseBuilder("User was removed from group", HttpStatus.ACCEPTED, isRemoved);
    }

}
