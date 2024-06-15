package edu.uoc.epcsd.productcatalog.infrastructure.repository.jpa;

import edu.uoc.epcsd.productcatalog.domain.Offer;
import edu.uoc.epcsd.productcatalog.domain.repository.OfferRepository;
import edu.uoc.epcsd.productcatalog.domain.service.CategoryNotFoundException;
import edu.uoc.epcsd.productcatalog.domain.service.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OfferRepositoryImpl implements OfferRepository {

    private final SpringDataOfferRepository jpaRepository;

    private final SpringDataCategoryRepository jpaCategoryRepository;

    private final SpringDataProductRepository jpaProductRepository;

    @Override
    public Long addOffer(Offer offer) {
        Long categoryId = offer.getCategoryId();
        CategoryEntity category = jpaCategoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));

        Long productId = offer.getProductId();
        ProductEntity product = jpaProductRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));

        OfferEntity offerEntity = OfferEntity.fromDomain(offer);
        offerEntity.setCategory(category);
        offerEntity.setProduct(product);

        return jpaRepository.save(offerEntity).getId();
    }

    @Override
    public List<Offer> findOffersByUser(String email) {
        return jpaRepository.findOfferEntitiesByEmail(email).stream().map(OfferEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Offer> findOfferById(Long id) {
        return jpaRepository.findById(id).map(OfferEntity::toDomain);
    }

    @Override
    public Offer save(Offer offer) {
        OfferEntity offerEntity = OfferEntity.fromDomain(offer);
        offerEntity.setCategory(jpaCategoryRepository.findById(offer.getCategoryId()).orElseThrow());
        offerEntity.setProduct(jpaProductRepository.findById(offer.getProductId()).orElseThrow());
        return jpaRepository.save(offerEntity).toDomain();
    }
}
