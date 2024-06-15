package edu.uoc.epcsd.productcatalog.infrastructure.repository.jpa;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.uoc.epcsd.productcatalog.domain.Offer;
import edu.uoc.epcsd.productcatalog.domain.OfferStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "Offer")
@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OfferEntity implements DomainTranslatable<Offer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @ManyToOne(optional = false)
    private CategoryEntity category;

    @ManyToOne(optional = false)
    private ProductEntity product;

    @Column(name = "`date`", columnDefinition = "DATE")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OfferStatus status;

    @Column(name = "serialNumber", nullable = false, unique = true)
    private String serialNumber;

    public static OfferEntity fromDomain(Offer offer) {
        if (offer == null) {
            return null;
        }

        return OfferEntity.builder()
                .id(offer.getId())
                .email(offer.getEmail())
                .date(offer.getDate())
                .status(offer.getStatus())
                .serialNumber(offer.getSerialNumber())
                .build();
    }

    @Override
    public Offer toDomain() {
        return Offer.builder()
                .id(this.getId())
                .email(this.getEmail())
                .categoryId(this.getCategory().getId())
                .productId(this.getProduct().getId())
                .date(this.getDate())
                .status(this.getStatus())
                .serialNumber(this.getSerialNumber())
                .build();
    }
}
