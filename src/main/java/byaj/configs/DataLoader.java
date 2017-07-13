package byaj.configs;


import byaj.models.Role;
import byaj.models.User;
import byaj.repositories.RoleRepository;
import byaj.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner{


    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;


    @Override
    public void run(String... strings) throws Exception {
        System.out.println("Loading data . . .");

        roleRepository.save(new Role("USER"));
        roleRepository.save(new Role("EMPLOYER"));
        roleRepository.save(new Role("ADMIN"));

        Role adminRole = roleRepository.findByRole("ADMIN");
        Role employerRole = roleRepository.findByRole("EMPLOYER");
        Role userRole = roleRepository.findByRole("USER");

        User user = new User("bob@bob.com","bob","Bob","Bobberson", true, "bob", "http://res.cloudinary.com/andrewjonesdev/image/upload/c_scale,h_100/v1499894133/profilepic_kos4l4.jpg");
        user.setRoles(Arrays.asList(userRole));
        userRepository.save(user);

        user = new User("jim@jim.com","jim","Jim","Jimmerson", true, "jim",  "http://res.cloudinary.com/andrewjonesdev/image/upload/c_scale,h_100/v1499894133/profilepic_kos4l4.jpg");
        user.setRoles(Arrays.asList(employerRole));
        userRepository.save(user);

        user = new User("admin@secure.com","TowerOfGod","Viole","Grace", true, "FloorAdministrator", "http://res.cloudinary.com/andrewjonesdev/image/upload/c_scale,h_100/v1499894133/profilepic_kos4l4.jpg");
        user.setRoles(Arrays.asList(userRole));
        user.setRoles(Arrays.asList(employerRole));
        user.setRoles(Arrays.asList(adminRole));
        userRepository.save(user);

        user = new User("sam@every.com","password","Sam","Everyman", true, "everyman",  "http://res.cloudinary.com/andrewjonesdev/image/upload/c_scale,h_100/v1499894133/profilepic_kos4l4.jpg");
        user.setRoles(Arrays.asList(userRole, adminRole));
        userRepository.save(user);

    }
}
