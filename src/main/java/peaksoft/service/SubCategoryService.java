package peaksoft.service;

import peaksoft.dto.*;

import java.util.List;
import java.util.Optional;

public interface SubCategoryService {

    SimpleResponse saveSubCategory(Long catId,SubCategoryRequest request);

    List<SubCategoryResponse>getAllSubCategories();

    Optional<SubCategoryResponse> findSubCategoryById(Long subCatId);

    SimpleResponse updateSubCategory(Long subCatId, SubCategoryRequest request);

    SimpleResponse deleteSubCategory(Long subCatId);

}
