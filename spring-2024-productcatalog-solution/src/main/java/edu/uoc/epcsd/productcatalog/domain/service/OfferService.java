package edu.uoc.epcsd.productcatalog.domain.service;

import edu.uoc.epcsd.productcatalog.domain.Offer;
import edu.uoc.epcsd.productcatalog.domain.OfferStatus;

import java.time.LocalDate;
import java.util.List;

public interface OfferService {
    Long addOffer(Offer offer);

    List<Offer> findOffersByUser(String email);

    Offer evaluateOffer(Long id, LocalDate date, OfferStatus status);
}
