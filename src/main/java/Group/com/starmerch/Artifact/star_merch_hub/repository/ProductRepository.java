package Group.com.starmerch.Artifact.star_merch_hub.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import Group.com.starmerch.Artifact.star_merch_hub.model.ArtistName;
import Group.com.starmerch.Artifact.star_merch_hub.model.CategoryType;
import Group.com.starmerch.Artifact.star_merch_hub.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByArtist(
        ArtistName artist,
        Pageable pageable
    );

    Page<Product> findByCategory(
        CategoryType category,
        Pageable pageable
    );

    Page<Product> findByArtistAndCategory(
        ArtistName artist,
        CategoryType category,
        Pageable pageable
    );
}