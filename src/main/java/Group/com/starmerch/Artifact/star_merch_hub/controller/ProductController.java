package Group.com.starmerch.Artifact.star_merch_hub.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Group.com.starmerch.Artifact.star_merch_hub.model.ArtistName;
import Group.com.starmerch.Artifact.star_merch_hub.model.CategoryType;
import Group.com.starmerch.Artifact.star_merch_hub.model.Product;
import Group.com.starmerch.Artifact.star_merch_hub.repository.ProductRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public String listProducts(
            @RequestParam(required = false) ArtistName artist,
            @RequestParam(required = false) CategoryType category,
            @RequestParam(defaultValue = "nameAsc") String sort,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        Sort sorting;

        switch (sort) {
            case "nameDesc":
                sorting = Sort.by("name").descending();
                break;

            case "priceAsc":
                sorting = Sort.by("price").ascending();
                break;

            case "priceDesc":
                sorting = Sort.by("price").descending();
                break;

            case "createdDesc":
                sorting = Sort.by("createdAt").descending();
                break;

            default:
                sorting = Sort.by("name").ascending();
        }

        Pageable pageable = PageRequest.of(page, 6, sorting);

        Page<Product> productPage;

        if (artist != null && category != null) {
            productPage = productRepository.findByArtistAndCategory(
                    artist,
                    category,
                    pageable
            );
        } else if (artist != null) {
            productPage = productRepository.findByArtist(
                    artist,
                    pageable
            );
        } else if (category != null) {
            productPage = productRepository.findByCategory(
                    category,
                    pageable
            );
        } else {
            productPage = productRepository.findAll(pageable);
        }

        model.addAttribute("productPage", productPage);
        model.addAttribute("artists", ArtistName.values());
        model.addAttribute("categories", CategoryType.values());
        model.addAttribute("selectedArtist", artist);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("sort", sort);

        return "products";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {

        model.addAttribute("product", new Product());
        model.addAttribute("artists", ArtistName.values());
        model.addAttribute("categories", CategoryType.values());

        return "product-form";
    }

    @PostMapping
    public String createProduct(
            @Valid @ModelAttribute("product") Product product,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {

            model.addAttribute("artists", ArtistName.values());
            model.addAttribute("categories", CategoryType.values());

            return "product-form";
        }

        productRepository.save(product);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Your little sun has been added successfully! ☀️"
        );

        return "redirect:/products";
    }
}