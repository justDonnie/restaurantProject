package peaksoft.dto;

import lombok.Builder;
import peaksoft.validation.UniqueName;

@Builder
public record CategoryResponse(
        Long id,
        @UniqueName
        String name
) {

    public CategoryResponse {
    }
}
