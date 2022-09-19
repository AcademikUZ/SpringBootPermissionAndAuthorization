package fan.company.springbootpermissionandauthorization.component;

import fan.company.springbootpermissionandauthorization.entity.Huquq;
import fan.company.springbootpermissionandauthorization.entity.Lavozim;
import fan.company.springbootpermissionandauthorization.entity.User;
import fan.company.springbootpermissionandauthorization.repository.LavozimRepository;
import fan.company.springbootpermissionandauthorization.repository.UserRepository;
import fan.company.springbootpermissionandauthorization.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static fan.company.springbootpermissionandauthorization.entity.Huquq.*;

@Component
public class DataLoder implements CommandLineRunner {

    @Autowired
    LavozimRepository lavozimRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${spring.sql.init.mode}")
    private String initModeType;


    @Override
    public void run(String... args) throws Exception {

        /**
         * Boshlang'ich userlarni yaratish
         */

        if (initModeType.equals("always")) {
            Huquq[] huquqs = Huquq.values();

            Lavozim adminRole = lavozimRepository.save(
                    new Lavozim(
                            AppConstants.ADMIN,
                            Arrays.asList(huquqs),
                            "Tizim administratori"
                    )
            );

            Lavozim userRole = lavozimRepository.save(
                    new Lavozim(
                            AppConstants.USER,
                            Arrays.asList(ADD_COMMENT, EDIT_COMMENT, DELETE_MY_COMMENT),
                            "Oddiy foydalanuvchi"
                    )
            );

            userRepository.save(
                    new User(
                            "Admin",
                            "admin",
                            passwordEncoder.encode("admin"),
                            adminRole
                    )
            );

            userRepository.save(
                    new User(
                            "User",
                            "user",
                            passwordEncoder.encode("user"),
                            userRole
                    )
            );

        }

    }
}
