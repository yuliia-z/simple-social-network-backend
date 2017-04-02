package com.github.aelmod.ssn2.user;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.aelmod.ssn2.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @JsonView(User.MinimalView.class)
    public List<User> getAll(UserSpecification userSpecification) {
        return userService.findBy(userSpecification);
    }

    @GetMapping("{userId}")
    @JsonView(User.FullView.class)
    public User getById(@CurrentUser User currentUser, @PathVariable int userId) {
        User userByPk = userService.getByPk(userId);
        if (userByPk.getIgnoreList().contains(currentUser)) {
            User user = new User();
            user.setName(userByPk.getName());
            return user;
        }
        return userByPk;
    }

    @GetMapping("{userId}/friends")
    @JsonView(User.AllPrimitivesView.class)
    public Set<User> getFriends(@PathVariable Integer userId) {
        return userService.getByPk(userId).getFriends();
    }

    @PutMapping("{ignoredUserId}/ignore")
    public void addUserToIgnoreList(@CurrentUser User currentUser, @PathVariable Integer ignoredUserId) {
        userService.ignore(currentUser, ignoredUserId);
    }

    @GetMapping("friends/requests")
    @JsonView(User.MinimalView.class)
    public Set<User> getFriendRequests(@CurrentUser User currentUser) {
        return currentUser.getFriendRequestsBucket();
    }

    @PostMapping
    public void requestFriendship(@CurrentUser User currentUser, @RequestBody Integer requestedFriendshipUserId) {
        userService.requestFriendship(currentUser, requestedFriendshipUserId);
    }

    @PostMapping("register")
    public void registerUser(@RequestBody @Valid UserRegisterForm registerForm) {
        userService.save(registerForm.toUser());
    }
}
