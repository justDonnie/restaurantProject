package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.dto.CategoryRequest;
import peaksoft.dto.CategoryResponse;
import peaksoft.dto.SimpleResponse;
import peaksoft.enums.Role;
import peaksoft.exceptions.AccessDeniedException;
import peaksoft.exceptions.BadCredentialException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.models.Category;
import peaksoft.models.SubCategory;
import peaksoft.models.User;
import peaksoft.repository.CategoryRepository;
import peaksoft.repository.UserRepository;
import peaksoft.repository.template.CategoryJdbcTemplate;
import peaksoft.service.CategoryService;
import peaksoft.service.SubCategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryJdbcTemplate categoryJdbcTemplate;
    private final SubCategoryService subCategoryService;
    private final UserRepository userRepository;

    @Override
    public SimpleResponse saveCategory(CategoryRequest categoryRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Authentication required to create a categories !!!");
        }
        String email = authentication.getName();
        User user = userRepository.getUserByEmail(email)
                .orElseThrow(() -> new BadCredentialException("There are no any Users with email: " + email + " !!!"));
        if (user.getRole().equals(Role.WAITER)||user.getRole().equals(Role.CHEF)){
            throw new AccessDeniedException("Authentication required to be ADMIN to create a categories !!!");
        }
        Category category = new Category();
        category.setName(categoryRequest.name());
        categoryRepository.save(category);
        log.info("category is saved!!!");
        return new SimpleResponse(
                HttpStatus.OK,
                "Category: "+category.getName()+" is successfully saved!!!"
        );
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryJdbcTemplate.getAllCategories();
    }

    @Override
    public CategoryResponse findCategoryById(Long categoryId) {
        return categoryJdbcTemplate.findCategoryById(categoryId).orElseThrow(()->new NotFoundException("There are no any categories with ID: "+categoryId));
    }

    @Override
    public SimpleResponse updateCategory(Long categoryId, CategoryRequest categoryRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Authentication required to create a categories !!!");
        }
        String email = authentication.getName();
        User user = userRepository.getUserByEmail(email)
                .orElseThrow(() -> new BadCredentialException("There are no any Users with email: " + email + " !!!"));
        if (user.getRole().equals(Role.WAITER)||user.getRole().equals(Role.CHEF)){
            throw new AccessDeniedException("Authentication required to be ADMIN to create a categories !!!");
        }
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("There are no any categories with ID: " + categoryId));
        category.setName(categoryRequest.name());
        categoryRepository.save(category);
        log.info("Category is successfully updated !!!");
        return new SimpleResponse(
                HttpStatus.OK,
                "Category with  ID: "+categoryId+" is successfully updated !!!"
        );
    }

    @Override
    public SimpleResponse deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("There are no any categories with ID: " + categoryId));
        for (SubCategory subCategory:category.getSubCategories()) {
            subCategoryService.deleteSubCategory(subCategory.getId());
        }
        categoryRepository.delete(category);
        return new SimpleResponse(
                HttpStatus.OK,
                "Category with ID: "+categoryId+" is successfully deleted !!!"
        );
    }
}
