package peaksoft.repository.template;

import peaksoft.dto.CategoryRequest;
import peaksoft.dto.CategoryResponse;
import peaksoft.dto.SimpleResponse;

import java.util.List;
import java.util.Optional;

public interface CategoryJdbcTemplate {

    List<CategoryResponse> getAllCategories();

    Optional<CategoryResponse>findCategoryById(Long catId);


}
