package edu.uoc.epcsd.productcatalog.application.rest.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public final class AddOfferRequest {

    @NotBlank
    private String email;

    @NotNull
    private Long categoryId;

    @NotNull
    private Long productId;

    @NotBlank
    private final String serialNumber;
}
