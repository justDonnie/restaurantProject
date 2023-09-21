package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.SubCategoryRequest;
import peaksoft.dto.SubCategoryResponse;
import peaksoft.enums.Role;
import peaksoft.exceptions.*;
import peaksoft.models.Category;
import peaksoft.models.SubCategory;
import peaksoft.models.User;
import peaksoft.repository.CategoryRepository;
import peaksoft.repository.SubCategoryRepository;
import peaksoft.repository.UserRepository;
import peaksoft.repository.template.SubCategoryJdbcTemplate;
import peaksoft.service.SubCategoryService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SubCategoryServiceImpl implements SubCategoryService{

    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final SubCategoryJdbcTemplate subCategoryJdbcTemplate;
    private final UserRepository userRepository;

    @Override
    public SimpleResponse saveSubCategory(Long catId, SubCategoryRequest request) {
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
        if (catId==null){
            throw new BadCredentialException("Input correct value!!!");
        }
        if (request.name().isBlank()){
            throw new BadRequestException("Invalid SubCategory name!!!");
        }
        boolean subCategoryExist = subCategoryRepository.existsByNameIgnoreCase(request.name());
        if (subCategoryExist){
            throw new AlreadyExistException("SubCategory is already exists!!!");
        }
        Category category = categoryRepository
                .findById(catId).orElseThrow(() -> new NotFoundException("Category with ID: " + catId + " is not found!!!"));
        SubCategory subCategory = new SubCategory(request.name());
        subCategory.setCategory(category);
        subCategoryRepository.save(subCategory);
        return new SimpleResponse(
                HttpStatus.OK,
                "SubCategory is successfully saved!!!"
        );
    }

    @Override
    public List<SubCategoryResponse> getAllSubCategories() {
        return subCategoryJdbcTemplate.getAllSubCategory();
    }

    @Override
    public Optional<SubCategoryResponse> findSubCategoryById(Long subCatId) {
        return subCategoryJdbcTemplate.findById(subCatId);
    }

    @Override
    public SimpleResponse updateSubCategory(Long subCatId, SubCategoryRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Authentication required to update a categories !!!");
        }
        String email = authentication.getName();
        User user = userRepository.getUserByEmail(email)
                .orElseThrow(() -> new BadCredentialException("There are no any Users with email: " + email + " !!!"));
        if (user.getRole().equals(Role.WAITER)||user.getRole().equals(Role.CHEF)){
            throw new AccessDeniedException("Authentication required to be ADMIN to update a categories !!!");
        }
        if (subCatId!=null){
            SubCategory subCategory = subCategoryRepository.findById(subCatId)
                    .orElseThrow(() -> new NotFoundException("SubCategory with ID: " + subCatId + " is not found!!!"));
            subCategory.setName(request.name());
            subCategoryRepository.save(subCategory);
            log.info("SubCategory is successfully updated!!!");
            return new SimpleResponse(
                    HttpStatus.OK,
                    "SubCategory is successfully updated!!!"
            );
        }
        else {
            throw new BadCredentialException("Input correct values!!!");
        }
    }

    @Override
    public SimpleResponse deleteSubCategory(Long subCatId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Authentication required to delete a categories !!!");
        }
        String email = authentication.getName();
        User user = userRepository.getUserByEmail(email)
                .orElseThrow(() -> new BadCredentialException("There are no any Users with email: " + email + " !!!"));
        if (user.getRole().equals(Role.WAITER)||user.getRole().equals(Role.CHEF)){
            throw new AccessDeniedException("Authentication required to be ADMIN to delete a categories !!!");
        }
        SubCategory subCategory = subCategoryRepository.findById(subCatId)
                .orElseThrow(() -> new NotFoundException("There are no any subCategories with ID: " + subCatId + " in database!!!"));
        subCategoryRepository.deleteById(subCatId);
        return new SimpleResponse(
                HttpStatus.OK,
                "SubCategory is successfully dropped !!!"
        );
    }
}
