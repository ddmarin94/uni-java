package edu.uoc.epcsd.productcatalog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class OfferUnitTest {

    @Test
    public void whenCreatingOffer_thenStatusIsPending() {
        Offer offer = new Offer();
        assertThat(offer.getStatus()).isEqualTo(OfferStatus.PENDING);
    }
}
