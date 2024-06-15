package edu.uoc.epcsd.productcatalog.domain.service;

public class OfferNotFoundException extends RuntimeException {
    public OfferNotFoundException(Long id) {
        super("Offer with id '" + id + "' not found");
    }
}
