package vemc.cinema.configuration;

import vemc.security.entity.Role;
import vemc.security.repository.RoleRepository;
import vemc.security.repository.UserWithRolesRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class SetupDevUsers implements ApplicationRunner {

    UserWithRolesRepository userWithRolesRepository;
    RoleRepository roleRepository;
    PasswordEncoder pwEncoder;

    public SetupDevUsers(UserWithRolesRepository userWithRolesRepository,RoleRepository roleRepository,PasswordEncoder passwordEncoder) {
        this.userWithRolesRepository = userWithRolesRepository;
        this.roleRepository = roleRepository;
        this.pwEncoder = passwordEncoder;
    }

    public void run(ApplicationArguments args) {
        setupAllowedRoles();
    }

    private void setupAllowedRoles(){
        roleRepository.save(new Role("USER"));
        roleRepository.save(new Role("ADMIN"));
    }
}
