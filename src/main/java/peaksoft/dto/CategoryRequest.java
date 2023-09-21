package peaksoft.dto;

import lombok.Builder;
import peaksoft.validation.UniqueName;

@Builder
public record CategoryRequest(
        @UniqueName
        String name
) {

    public CategoryRequest {
    }

}
