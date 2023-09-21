package peaksoft.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
public record StopListRequest(
        String reason,
        LocalDate date) {

    public StopListRequest {
    }
}
