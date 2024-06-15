package edu.uoc.epcsd.productcatalog.domain.repository;

import edu.uoc.epcsd.productcatalog.domain.Offer;

import java.util.List;
import java.util.Optional;

public interface OfferRepository {
    Long addOffer(Offer offer);

    List<Offer> findOffersByUser(String email);

    Optional<Offer> findOfferById(Long id);

    Offer save(Offer offer);

}
