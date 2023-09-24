package peaksoft.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter

public class ChequeResponse {
    private Long id;
    private BigDecimal priceAverage;
    private ZonedDateTime createdAt;
    private int services;
    private BigDecimal grandTotal;

    public ChequeResponse(Long id, BigDecimal priceAverage, ZonedDateTime createdAt) {
        this.id = id;
        this.priceAverage = priceAverage;
        this.createdAt = createdAt;
    }

    public ChequeResponse() {
    }
}
