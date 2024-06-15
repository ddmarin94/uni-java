package edu.uoc.epcsd.productcatalog.infrastructure.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataOfferRepository extends JpaRepository<OfferEntity, Long> {
    public List<OfferEntity> findOfferEntitiesByEmail(String email);
}
