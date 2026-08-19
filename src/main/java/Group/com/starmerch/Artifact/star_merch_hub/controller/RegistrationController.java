package Group.com.starmerch.Artifact.star_merch_hub.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import Group.com.starmerch.Artifact.star_merch_hub.model.RegisterRequest;
import Group.com.starmerch.Artifact.star_merch_hub.model.Role;
import Group.com.starmerch.Artifact.star_merch_hub.model.User;
import Group.com.starmerch.Artifact.star_merch_hub.repository.UserRepository;
import jakarta.validation.Valid;

@Controller
public class RegistrationController {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public RegistrationController(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/register")
    public String showRegisterForm(
            Model model) {

        model.addAttribute(
            "registerRequest",
            new RegisterRequest()
        );

        return "register";
    }


    @PostMapping("/register")
    public String register(
            @Valid
            @ModelAttribute("registerRequest")
            RegisterRequest request,

            BindingResult result) {


        if (!request.getPassword()
                .equals(
                    request.getConfirmPassword()
                )) {

            result.rejectValue(
                "confirmPassword",
                "password.mismatch",
                "Passwords do not match."
            );
        }


        if (!request.getUsername().isBlank()
                && userRepository
                    .existsByUsername(
                        request.getUsername()
                    )) {

            result.rejectValue(
                "username",
                "username.exists",
                "This username is already taken."
            );
        }


        if (!request.getEmail().isBlank()
                && userRepository
                    .existsByEmail(
                        request.getEmail()
                    )) {

            result.rejectValue(
                "email",
                "email.exists",
                "This email is already registered."
            );
        }


        if (result.hasErrors()) {
            return "register";
        }


        User user = new User();

        user.setUsername(
            request.getUsername()
        );

        user.setEmail(
            request.getEmail()
        );

        user.setPassword(
            passwordEncoder.encode(
                request.getPassword()
            )
        );

        // EVERY PUBLIC REGISTRATION
        // BECOMES CUSTOMER
        user.setRole(Role.CUSTOMER);

        userRepository.save(user);


        return "redirect:/login?registered";
    }
}