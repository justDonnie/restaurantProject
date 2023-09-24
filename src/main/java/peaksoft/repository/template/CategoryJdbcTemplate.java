package peaksoft.repository.template;

import org.springframework.stereotype.Repository;
import peaksoft.dto.CategoryResponse;


import java.util.List;
import java.util.Optional;
@Repository
public interface CategoryJdbcTemplate {

    List<CategoryResponse> getAllCategories();

    Optional<CategoryResponse>findCategoryById(Long catId);


}
