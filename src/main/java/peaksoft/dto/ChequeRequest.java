package peaksoft.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ChequeRequest {
    private ZonedDateTime createdAt;
    private List<String>menuItemNames;


    public ChequeRequest( ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
