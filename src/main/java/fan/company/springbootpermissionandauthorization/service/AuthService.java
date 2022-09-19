package fan.company.springbootpermissionandauthorization.service;

import fan.company.springbootpermissionandauthorization.entity.Lavozim;
import fan.company.springbootpermissionandauthorization.entity.User;
import fan.company.springbootpermissionandauthorization.exceptions.ResourceNotFoundException;
import fan.company.springbootpermissionandauthorization.payload.ApiResult;
import fan.company.springbootpermissionandauthorization.payload.LoginDto;
import fan.company.springbootpermissionandauthorization.payload.RegisterDto;
import fan.company.springbootpermissionandauthorization.repository.LavozimRepository;
import fan.company.springbootpermissionandauthorization.repository.UserRepository;
import fan.company.springbootpermissionandauthorization.security.tokenGenerator.JwtProvider;
import fan.company.springbootpermissionandauthorization.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository repository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    LavozimRepository lavozimRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;

    public ApiResult register(RegisterDto dto) {


        if (!dto.getPassword().equals(dto.getPrePassword()))
            return new ApiResult("Passwordlar mos emas!", false);

        if (repository.findByUsername(dto.getUsername()).isPresent())
            return new ApiResult("Bunday username mavjud!", false);

        User user = new User(
                dto.getFullName(),
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()),

                lavozimRepository.findByName(AppConstants.USER).orElseThrow(() -> new ResourceNotFoundException(
                        "lavozim", "name", AppConstants.USER
                ))
        );

        repository.save(user);

        return new ApiResult("Muvoffaqiyatli ro'yxatdan o'tdingiz!", true);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /**
         * Usernameni email orqali topish
         */

        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " topilmadi!"));
    }

    public ApiResult login(LoginDto dto) {

        try {

            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    dto.getUsername(),
                    dto.getPassword()
            ));
            User user = (User) authenticate.getPrincipal();
            String token = jwtProvider.generatorToken(user.getUsername(), user.getLavozim());
            return new ApiResult("Token", true, token);

        } catch (BadCredentialsException e){
            return new ApiResult("Login yoki parol xato!", false);
        }
    }
}
