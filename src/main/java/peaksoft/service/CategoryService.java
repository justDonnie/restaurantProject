package peaksoft.service;

import peaksoft.dto.CategoryRequest;
import peaksoft.dto.CategoryResponse;
import peaksoft.dto.SimpleResponse;

import java.util.List;

public interface CategoryService {

    SimpleResponse saveCategory(CategoryRequest categoryRequest);

    List<CategoryResponse> getAllCategories();

    CategoryResponse findCategoryById(Long categoryId);

    SimpleResponse updateCategory(Long categoryId, CategoryRequest categoryRequest);

    SimpleResponse deleteCategory(Long categoryId);

}
