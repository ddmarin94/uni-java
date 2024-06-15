package edu.uoc.epcsd.productcatalog.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@ToString
@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Offer {
    @NotNull
    private Long id;

    @NotBlank
    private String email;

    @NotNull
    private Long categoryId;

    @NotNull
    private Long productId;

    @NotBlank
    private String serialNumber;

    @NotNull
    @Builder.Default
    private OfferStatus status = OfferStatus.PENDING;

    private LocalDate date;
}
