package peaksoft.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.CategoryRequest;
import peaksoft.dto.CategoryResponse;
import peaksoft.dto.SimpleResponse;
import peaksoft.service.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
@Tag(name = "Category API")
public class CategoryAPI {
    private final CategoryService categoryService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    SimpleResponse saveCategory(@RequestBody CategoryRequest request){
        return categoryService.saveCategory(request);
    }

    @PermitAll
    @GetMapping("/getAll")
    List<CategoryResponse> getAllCategories(){
        return categoryService.getAllCategories();
    }
    @PermitAll
    @GetMapping("/byId/{catId}")
    CategoryResponse findById(@PathVariable Long catId){
        return categoryService.findCategoryById(catId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{catId}")
    SimpleResponse updateCategory(@PathVariable Long catId,
                                  @RequestBody CategoryRequest request){
        return categoryService.updateCategory(catId,request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{catId}")
    SimpleResponse deleteCategory(@PathVariable Long catId){
        return categoryService.deleteCategory(catId);
    }

}
