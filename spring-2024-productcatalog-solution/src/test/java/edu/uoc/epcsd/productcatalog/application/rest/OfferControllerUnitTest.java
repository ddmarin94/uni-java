package edu.uoc.epcsd.productcatalog.application.rest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import edu.uoc.epcsd.productcatalog.utils.OfferDataFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import edu.uoc.epcsd.productcatalog.domain.Offer;
import edu.uoc.epcsd.productcatalog.domain.service.OfferService;

import java.util.Arrays;
import java.util.List;

public class OfferControllerUnitTest {

    private MockMvc mockMvc;

    @Mock
    private OfferService offerService;

    @InjectMocks
    private OfferRESTController offerController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this); // Initializes annotations
        mockMvc = standaloneSetup(offerController).build(); // Builds MockMvc without the Spring context
    }

    @Test
    void testGetOffersByUserEmail() throws Exception {
        // Arrange
        String userEmail = "user@example.com";
        Offer offerOne = OfferDataFixture.createValidOfferOne(userEmail);
        Offer offerTwo = OfferDataFixture.createValidOfferTwo(userEmail);

        List<Offer> expectedOffers = Arrays.asList(offerOne, offerTwo);

        when(offerService.findOffersByUser(userEmail)).thenReturn(expectedOffers);

        // Act & Assert
        mockMvc.perform(get("/offers?email=" + userEmail))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].email").value(userEmail))
                .andExpect(jsonPath("$[0].id").value(offerOne.getId()))
                .andExpect(jsonPath("$[1].email").value(userEmail))
                .andExpect(jsonPath("$[1].id").value(offerTwo.getId()));
    }
}
