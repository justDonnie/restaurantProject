package peaksoft.repository.template;

import org.springframework.stereotype.Repository;
import peaksoft.dto.SubCategoryResponse;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubCategoryJdbcTemplate {

    List<SubCategoryResponse> getAllSubCategory();

    Optional<SubCategoryResponse>findById(Long subCatId);
}
