package Group.com.starmerch.Artifact.star_merch_hub.controller;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Group.com.starmerch.Artifact.star_merch_hub.repository.ProductRepository;
import Group.com.starmerch.Artifact.star_merch_hub.repository.UserRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    public AdminController(
            ProductRepository productRepository,
            UserRepository userRepository) {

        this.productRepository =
            productRepository;

        this.userRepository =
            userRepository;
    }


    @GetMapping
    public String dashboard(
            Model model) {

        model.addAttribute(
            "products",
            productRepository.findAll(
                Sort.by("name").ascending()
            )
        );

        model.addAttribute(
            "users",
            userRepository.findAll(
                Sort.by("username").ascending()
            )
        );

        return "admin";
    }
}