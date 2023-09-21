package peaksoft.dto;

import lombok.Builder;
import peaksoft.validation.UniqueName;
@Builder
public record SubCategoryResponse(
        Long id,
        @UniqueName
        String name

) {
    public SubCategoryResponse {
    }
}
