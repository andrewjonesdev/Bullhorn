package byaj.controllers;

import byaj.configs.CloudinaryConfig;
import byaj.models.*;
import byaj.repositories.*;
import byaj.validators.UserValidator;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by student on 7/10/17.
 */
@Controller
public class HomeController {

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private byaj.services.UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private SearchRepository searchRepository;

    @Autowired
    private ProfileBuilderRepository profileBuilderRepository;

    @Autowired
    private PostBuilderRepository postBuilderRepository;

    @Autowired
    private CloudinaryConfig cloudc;

    @RequestMapping("/")
    public String home(Model model){
        model.addAttribute("search", new Search());
        model.addAttribute("post", new Post());
        model.addAttribute("follow", new Follow());
        model.addAttribute("posts", postRepository.findAllByOrderByPostDateDesc());
        return "postresults2";
    }

    @RequestMapping(value="/register", method = RequestMethod.GET)
    public String showRegistrationPage(Model model){
        model.addAttribute("search", new Search());
        model.addAttribute("user", new User());
        return "register2";
    }

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public String processRegistrationPage(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        model.addAttribute("search", new Search());

        model.addAttribute("user", user);
        userValidator.validate(user, result);

        if (result.hasErrors()) {
            return "register2";
        } else {
           /* if (user.getRoleSettings().toUpperCase().equals("ADMIN")) {
                //user.setRoles(Arrays.asList(adminRole));
                //userRepository.save(user);
                userService.saveAdmin(user);
            }
            if (user.getRoleSettings().toUpperCase().equals("EMPLOYER")) {
                //user.setRoles(Arrays.asList(employerRole));
                //userRepository.save(user);
                userService.saveEmployer(user);
            }
            if (user.getRoleSettings().toUpperCase().equals("USER")) {
                //user.setRoles(Arrays.asList(userRole));
                //userRepository.save(user);
                userService.saveUser(user);
                model.addAttribute("message", "User Account Successfully Created");
            }*/
            userService.saveUser(user);
            model.addAttribute("message", "User Account Successfully Created");
        }
        return "redirect:/";
    }
    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("search", new Search());
        return "login2";
    }

    @GetMapping("/post")
    public String newPost(Model model, Principal principal) {
        model.addAttribute("search", new Search());
        model.addAttribute("post", new Post());
        model.addAttribute("posts", postRepository.findAllByPostUserOrderByPostDateDesc(userRepository.findByUsername(principal.getName()).getId()));
        model.addAttribute("follow", new Follow());
        return "post2";
    }

    @PostMapping(path = "/post")
    public String processPost(@Valid Post post, BindingResult bindingResult, @RequestParam("file") MultipartFile file, Principal principal) {
        if (bindingResult.hasErrors()) {
            System.out.println("post");
            return "redirect:/job";
        }
        if(!file.isEmpty()){
            try {
                Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
                post.setPicUrl(cloudc.createUrlSuperPost(uploadResult.get("url").toString(),100, "scale", 2));

        } catch (IOException e){
            e.printStackTrace();
        }
        }

        post.setPostUser(userRepository.findByUsername(principal.getName()).getId());
        post.setPostAuthor(userRepository.findByUsername(principal.getName()).getUsername());
        postRepository.save(post);
        return "redirect:/post";

    }

    @PostMapping("/search")
    public String searchForResumes(@Valid Search search, BindingResult bindingResult, Principal principal, Model model){
        if (bindingResult.hasErrors()) {
            System.out.println("search");
            return "redirect:/";
        }
        model.addAttribute("search", new Search());
        model.addAttribute("profileBuilder", new ProfileBuilder());
        model.addAttribute("postBuilder", new PostBuilder());
        search.setSearchUser(userRepository.findByUsername(principal.getName()).getId());
        searchRepository.save(search);
        if(search.getSearchType().toLowerCase().equals("username")){
            model.addAttribute("posts", postRepository.findAllByPostAuthorOrderByPostDateDesc(search.getSearchValue()));
            model.addAttribute("follow", new Follow());
            return "postresults2";
        }
       /* if(search.getSearchType().toLowerCase().equals("company")){
            ArrayList<User> result = new ArrayList();
            List<Work> comp = workRepository.findAllByWorkEmployerOrderByWorkResAsc(search.getSearchValue());
            for (int count = 0; count< comp.size(); count++){
                result.add(userRepository.findById(comp.get(count).getWorkRes()));
            }
            model.addAttribute("results", result);
            return "searchResults2";
        }
        if(search.getSearchType().toLowerCase().equals("school")){
            ArrayList<User> result = new ArrayList();
            List<Education> comp = educationRepository.findAllByEduSchoolOrderByEduResAsc(search.getSearchValue());
            for (int count = 0; count< comp.size(); count++){
                result.add(userRepository.findById(comp.get(count).getEduRes()));
            }
            model.addAttribute("results", result);
            return "searchResults2";
        }
        if(search.getSearchType().toLowerCase().equals("jobtitle")){
            ArrayList<User> job = new ArrayList();
            //List<Job> comp = jobRepository.findAllByJobTitleOrderByJobStartYearDesc(search.getSearchValue());
            model.addAttribute("jobs", jobRepository.findAllByJobTitleOrderByJobStartYearDescJobStartMonthDesc(search.getSearchValue()));
            return "jobResults2";
        }*/
        return "redirect:/";
    }

    //Follow Post Mapping

    @PostMapping("/follow")
    public String changeFollowStatus(@Valid Follow follow, BindingResult bindingResult, Principal principal, Model model){
        if(bindingResult.hasErrors()){
            return "redirect:/";
        }
        if(follow.getFollowType().toLowerCase().equals("follow")){
            userService.followUser(userRepository.findByUsername(follow.getFollowValue()), userRepository.findByUsername(principal.getName()));
        }
        if(follow.getFollowType().toLowerCase().equals("unfollow")){
            userService.unfollowUser(userRepository.findByUsername(follow.getFollowValue()), userRepository.findByUsername(principal.getName()));
        }
        return "redirect:/";
    }

    //Following Post Mapping

    @GetMapping("/following")
    public String viewFollowing(Model model, Principal principal){
        model.addAttribute("search", new Search());
        model.addAttribute("profileBuilder", new ProfileBuilder());
        model.addAttribute("users", userRepository.findByUsername(principal.getName()).getFollowing());
        model.addAttribute("follow", new Follow());
        return "userresults2";
    }
    @GetMapping("/followers")
    public String viewFollowers(Model model, Principal principal){
        model.addAttribute("search", new Search());
        model.addAttribute("profileBuilder", new ProfileBuilder());
        model.addAttribute("users", userRepository.findByUsername(principal.getName()).getFollowed());
        model.addAttribute("follow", new Follow());
        return "userresults2";
    }
    @GetMapping("/users")
    public String viewUsers(Model model, Principal principal){
        model.addAttribute("search", new Search());
        model.addAttribute("profileBuilder", new ProfileBuilder());
        model.addAttribute("users", userRepository.findAllByOrderByUserDateDesc());
        model.addAttribute("follow", new Follow());
        return "userresults2";
    }
    @PostMapping("/generate/posts")
    public String generatePosts(@Valid ProfileBuilder profileBuilder, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "redirect:/";
        }
        model.addAttribute("search", new Search());
        model.addAttribute("posts", postRepository.findAllByPostAuthorOrderByPostDateDesc(profileBuilder.getProfileBuilderValue()));
        model.addAttribute("follow", new Follow());
        return "postresults2";
    }

}
