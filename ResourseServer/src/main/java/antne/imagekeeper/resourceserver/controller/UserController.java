package antne.imagekeeper.resourceserver.controller;

import antne.imagekeeper.resourceserver.model.Group;
import antne.imagekeeper.resourceserver.model.User;
import antne.imagekeeper.resourceserver.response.ResponseHandler;
import antne.imagekeeper.resourceserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type User controller.
 *
 * <p>
 * Class for controls requests for rest api by request /api/users for user
 * </p>
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /**
     * Instantiates a new User controller.
     *
     * @param userService the user service
     */
    public UserController(@Autowired UserService userService) {
        this.userService = userService;
    }

    /**
     * Add user and return id by request /api/users/save.
     *
     * @param user the user
     * @return the long
     */
    @PostMapping("/save")
    public ResponseEntity<Object> addUser(@RequestBody User user) {
        long id = userService.save(user);
        return ResponseHandler.responseBuilder("User was added", HttpStatus.CREATED, id);
    }

    /**
     * Gets a list of groups associated with a user by userId.
     *
     * @param userId ID пользователя.
     * @return ResponseEntity со списком групп.
     */
    @GetMapping("/{userId}/groups")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<Object> getGroups(@PathVariable long userId){
        List<Group> groups = userService.findGroupsByUserId(userId);
        return ResponseHandler.responseBuilder("Groups was returned", HttpStatus.FOUND, groups);
    }


}
