package peaksoft.dto;

import lombok.Builder;
import peaksoft.validation.UniqueName;
@Builder
public record SubCategoryRequest(
        @UniqueName
        String name) {

    public SubCategoryRequest {
    }
}
