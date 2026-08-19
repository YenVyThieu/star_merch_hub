package Group.com.starmerch.Artifact.star_merch_hub.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @NotBlank(message = "Product name is required.")
    @Size(min = 2, max = 80, message = "Product name must be between 2 and 80 characters.")
    private String name;

    @NotBlank(message = "Description is required.")
    @Size(max = 500, message = "Description cannot be longer than 500 characters.")
    private String description;

    @NotNull(message = "Price is required.")
    @DecimalMin(value = "0.01", message = "Price must be at least $0.01.")
    @DecimalMax(value = "9999.99", message = "Price cannot exceed $9999.99.")
    private BigDecimal price;

    @NotBlank(message = "SKU is required.")
    @Pattern(
        regexp = "[A-Z0-9-]{4,30}",
        message = "SKU must use uppercase letters, numbers, or hyphens."
    )
    @Column(name = "stock_keeping_unit")
    private String stockKeepingUnit;

    @Size(max = 500, message = "Image URL is too long.")
    @Column(name = "image_url")
    private String imageUrl;

    @NotNull(message = "Please select an artist.")
    @Enumerated(EnumType.STRING)
    private ArtistName artist;

    @NotNull(message = "Please select a category.")
    @Enumerated(EnumType.STRING)
    private CategoryType category;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Product() {
    }

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStockKeepingUnit() {
        return stockKeepingUnit;
    }

    public void setStockKeepingUnit(String stockKeepingUnit) {
        this.stockKeepingUnit = stockKeepingUnit;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ArtistName getArtist() {
        return artist;
    }

    public void setArtist(ArtistName artist) {
        this.artist = artist;
    }

    public CategoryType getCategory() {
        return category;
    }

    public void setCategory(CategoryType category) {
        this.category = category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}