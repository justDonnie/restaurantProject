package peaksoft.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import peaksoft.models.MenuItem;

import java.time.LocalDate;
@Getter
@Setter
@Builder
public class StopListResponse {

    private Long id;
    private String reason;
    private LocalDate date;
    private MenuItem menuItem;

    public StopListResponse(Long id, String reason, LocalDate date, MenuItem menuItem) {
        this.id = id;
        this.reason = reason;
        this.date = date;
        this.menuItem = menuItem;
    }
    public StopListResponse(Long id, String reason, LocalDate date) {
        this.id = id;
        this.reason = reason;
        this.date = date;
    }
    public StopListResponse() {
    }


}
