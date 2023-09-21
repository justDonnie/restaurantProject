package peaksoft.repository.template;

import peaksoft.dto.SubCategoryResponse;

import java.util.List;
import java.util.Optional;

public interface SubCategoryJdbcTemplate {

    List<SubCategoryResponse> getAllSubCategory();

    Optional<SubCategoryResponse>findById(Long subCatId);
}
