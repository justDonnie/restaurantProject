package peaksoft.api;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.SubCategoryRequest;
import peaksoft.dto.SubCategoryResponse;
import peaksoft.service.SubCategoryService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subCategory")
@Tag(name = "SubCategory API")
public class SubCategoryAPI {
    private final SubCategoryService subCategoryService;

    @PermitAll
    @GetMapping("/getAll")
    List<SubCategoryResponse> getAllSubCategories(){
        return subCategoryService.getAllSubCategories();
    }

    @PostMapping("/save/{catId}")
    SimpleResponse saveSubCategories(@PathVariable Long catId,
                                     @RequestBody SubCategoryRequest request){
        return subCategoryService.saveSubCategory(catId,request);
    }

    @GetMapping("/byId/{subCatId}")
    Optional<SubCategoryResponse>findById(@PathVariable Long subCatId){
        return subCategoryService.findSubCategoryById(subCatId);
    }


    @PutMapping("/update/{subCatId}")
    SimpleResponse updateSubCategory(@PathVariable Long subCatId,
                                     @RequestBody SubCategoryRequest request){
        return subCategoryService.updateSubCategory(subCatId,request);
    }

    @DeleteMapping("/delete/{subCatId}")
    SimpleResponse deleteSubCategory(@PathVariable Long subCatId){
        return subCategoryService.deleteSubCategory(subCatId);
    }



}
