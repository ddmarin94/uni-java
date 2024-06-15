package edu.uoc.epcsd.productcatalog;

import edu.uoc.epcsd.productcatalog.config.RestClientTestConfig;
import edu.uoc.epcsd.productcatalog.domain.Offer;
import edu.uoc.epcsd.productcatalog.domain.repository.OfferRepository;
import edu.uoc.epcsd.productcatalog.utils.OfferDataFixture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OfferRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OfferRepository offerRepository;

    @Test
    public void testOfferInsertion() {
        Offer offer = OfferDataFixture.createValidOfferOne();
        offer = entityManager.persistAndFlush(offer);
        assertNotNull(offerRepository.findOfferById(offer.getId()));
    }
}
