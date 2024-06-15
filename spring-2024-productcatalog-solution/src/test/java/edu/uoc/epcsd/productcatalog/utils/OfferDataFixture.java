package edu.uoc.epcsd.productcatalog.utils;

import edu.uoc.epcsd.productcatalog.domain.Offer;
import edu.uoc.epcsd.productcatalog.domain.OfferStatus;

import java.time.LocalDate;

public class OfferDataFixture {
    public static <userEmail, string> Offer createValidOfferOne() {
        return Offer.builder()
                .id(1L)
                .email("user@example.com")
                .categoryId(10L)
                .productId(100L)
                .serialNumber("SN123456")
                .status(OfferStatus.PENDING)
                .date(LocalDate.now())
                .build();
    }
    public static <userEmail, string> Offer createValidOfferOne(String userEmail) {
        return Offer.builder()
                .id(1L)
                .email(userEmail)
                .categoryId(10L)
                .productId(100L)
                .serialNumber("SN123456")
                .status(OfferStatus.PENDING)
                .date(LocalDate.now())
                .build();
    }

    public static Offer createValidOfferTwo() {
        return Offer.builder()
                .id(2L)
                .email("user@example.com")
                .categoryId(20L)
                .productId(200L)
                .serialNumber("SN654321")
                .status(OfferStatus.PENDING)
                .date(LocalDate.now().plusDays(1))
                .build();
    }
    public static Offer createValidOfferTwo(String userEmail) {
        return Offer.builder()
                .id(2L)
                .email(userEmail == null ? "user@example.com" : userEmail)
                .categoryId(20L)
                .productId(200L)
                .serialNumber("SN654321")
                .status(OfferStatus.PENDING)
                .date(LocalDate.now().plusDays(1))
                .build();
    }
}
