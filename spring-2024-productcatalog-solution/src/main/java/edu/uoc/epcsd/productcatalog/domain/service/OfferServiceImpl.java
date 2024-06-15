package edu.uoc.epcsd.productcatalog.domain.service;

import edu.uoc.epcsd.productcatalog.application.rest.response.GetUserResponse;
import edu.uoc.epcsd.productcatalog.domain.Offer;
import edu.uoc.epcsd.productcatalog.domain.OfferStatus;
import edu.uoc.epcsd.productcatalog.domain.repository.OfferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class OfferServiceImpl implements OfferService {

    @Value("${userService.getUserByEmail.url}")
    private String getUserByEmail;

    private final ItemService itemService;

    private final OfferRepository offerRepository;

    private final RestTemplate restTemplate;

    public Long addOffer(Offer offer) {
        try {
            ResponseEntity<GetUserResponse> response = restTemplate.
                    getForEntity(getUserByEmail, GetUserResponse.class, offer.getEmail());
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Could not fetch user with email " + offer.getEmail());
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new UserNotFoundException(offer.getEmail());
            }
            throw new RuntimeException("Could not fetch user with email " + offer.getEmail());
        }

        return offerRepository.addOffer(offer);
    }

    public List<Offer> findOffersByUser(String email) {
        try {
            ResponseEntity<GetUserResponse> response = restTemplate.
                    getForEntity(getUserByEmail, GetUserResponse.class, email);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Could not fetch user with email " + email);
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new UserNotFoundException(email);
            }
            throw new RuntimeException("Could not fetch user with email " + email);
        }
        return offerRepository.findOffersByUser(email);
    }

    public Offer evaluateOffer(Long id, LocalDate date, OfferStatus status) {
        Offer offer = offerRepository.findOfferById(id).orElseThrow(() -> new OfferNotFoundException(id));
        offer.setDate(date);
        offer.setStatus(status);

        if (status.equals(OfferStatus.ACCEPTED)) {
            itemService.createItem(offer.getProductId(), offer.getSerialNumber());
        }

        offer = offerRepository.save(offer);

        return offer;
    }
}
