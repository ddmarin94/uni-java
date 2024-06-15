package edu.uoc.epcsd.productcatalog.domain.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import edu.uoc.epcsd.productcatalog.application.rest.response.GetUserResponse;
import edu.uoc.epcsd.productcatalog.domain.Offer;
import edu.uoc.epcsd.productcatalog.domain.repository.OfferRepository;
import edu.uoc.epcsd.productcatalog.domain.service.OfferServiceImpl;
import edu.uoc.epcsd.productcatalog.domain.service.UserNotFoundException;
import edu.uoc.epcsd.productcatalog.utils.OfferDataFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class OfferServiceUnitTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private OfferRepository offerRepository;

    @InjectMocks
    private OfferServiceImpl offerService;

    private final String getUserByEmailUrl = "http://example.com/api/users/{email}";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(offerService, "getUserByEmail", getUserByEmailUrl);
    }


    @Test
    void testFindOffersByUser_WithValidEmail_ReturnsOffers() {
        // Arrange
        String userEmail = "david@example.com";
        GetUserResponse mockResponse = new GetUserResponse(
                1L,
                "David",
                userEmail,
                "123456789"
        );
        ResponseEntity<GetUserResponse> responseEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);
        when(restTemplate.getForEntity(getUserByEmailUrl, GetUserResponse.class, userEmail)).thenReturn(responseEntity);

        List<Offer> expectedOffers = Arrays.asList(
                OfferDataFixture.createValidOfferOne(mockResponse.getEmail()),
                OfferDataFixture.createValidOfferTwo(mockResponse.getEmail())
        );
        when(offerRepository.findOffersByUser(userEmail)).thenReturn(expectedOffers);

        // Act
        List<Offer> actualOffers = offerService.findOffersByUser(userEmail);

        // Assert
        assertNotNull(actualOffers);
        assertFalse(actualOffers.isEmpty());
        assertEquals(expectedOffers, actualOffers);
    }

    @Test
    void testFindOffersByUser_WithInvalidEmail_ThrowsException() {
        // Arrange
        String userEmail = "nonexistent@example.com";
        when(restTemplate.getForEntity(getUserByEmailUrl, GetUserResponse.class, userEmail))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        // Act & Assert
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            offerService.findOffersByUser(userEmail);
        });

        assertTrue(exception.getMessage().contains("User with email '" + userEmail + "' not found"));
    }
}
