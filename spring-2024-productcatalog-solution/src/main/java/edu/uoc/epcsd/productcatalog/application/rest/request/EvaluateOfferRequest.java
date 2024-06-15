package edu.uoc.epcsd.productcatalog.application.rest.request;

import edu.uoc.epcsd.productcatalog.domain.OfferStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public final class EvaluateOfferRequest {

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @NotNull
    private OfferStatus status;
}
