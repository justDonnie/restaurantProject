package peaksoft.dto;


import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
public record MenuItemRequest(
        String name,
        String image,
        @Positive(message = "Invalid price format !!!")
        BigDecimal price,
        String description,
        Boolean isVegetarian,
        Long subCategoryId
) {
    public MenuItemRequest {
    }
}

