package byaj.services;

import byaj.models.Post;
import byaj.models.User;
import byaj.repositories.PostRepository;
import byaj.repositories.RoleRepository;
import byaj.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public int countByEmail(String email) {
        return userRepository.countByEmail(email);
    }
    Principal principal;

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public void saveUser(User user) {
        user.setRoles(Arrays.asList(roleRepository.findByRole("USER")));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public void saveEmployer(User user) {
        user.setRoles(Arrays.asList(roleRepository.findByRole("USER")));
        user.setRoles(Arrays.asList(roleRepository.findByRole("EMPLOYER")));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public void saveAdmin(User user) {
        user.setRoles(Arrays.asList(roleRepository.findByRole("USER")));
        user.setRoles(Arrays.asList(roleRepository.findByRole("EMPLOYER")));
        user.setRoles(Arrays.asList(roleRepository.findByRole("ADMIN")));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public void followUser(User otherUser, User thisUser){
        //this user is following other user
        Collection<User> following=thisUser.getFollowing();
        if(!following.contains(otherUser)) {
            following.add(otherUser);
            thisUser.setFollowing(following);
        }
        //other user is being followed by this user
        Collection<User> followed=otherUser.getFollowed();
        if(followed.contains(thisUser)) {
            followed.add(thisUser);
            otherUser.setFollowed(followed);
        }
        userRepository.save(otherUser);
        userRepository.save(thisUser);
    }
    public void unfollowUser(User otherUser, User thisUser){
        Collection<User> unfollowing;

            if( thisUser.getFollowing().contains(otherUser)){
                unfollowing=thisUser.getFollowing();
                unfollowing.remove(otherUser);
                thisUser.setFollowing(unfollowing);
            }
        Collection<User> unfollowed;
            if (otherUser.getFollowed().contains(thisUser)){
                unfollowed=otherUser.getFollowed();
                unfollowed.remove(thisUser);
                otherUser.setFollowed(unfollowed);
            }
        userRepository.save(otherUser);
        userRepository.save(thisUser);
    }

    public void likePost(User user, Post post){
        //this user is likedPosts other user
        Collection<Post> likedPosts=user.getLikes();
        if(!likedPosts.contains(post)) {
            likedPosts.add(post);
            user.setLikes(likedPosts);
        }
        //other user is being likers by this user
        Collection<User> likers=post.getUsers();
        if(likers.contains(user)) {
            likers.add(user);
            post.setUsers(likers);
        }
        postRepository.save(post);
        userRepository.save(user);
    }

    public void unlikePost(User user, Post post){
        Collection<User> unlike;

        if( post.getUsers().contains(user)){
            unlike=post.getUsers();
            unlike.remove(user);
            post.setUsers(unlike);
        }
        Collection<Post> dislikers;
        if (user.getLikes().contains(post)){
            dislikers=user.getLikes();
            dislikers.remove(post);
            user.setLikes(dislikers);
        }
        userRepository.save(user);
        postRepository.save(post);
    }
}
