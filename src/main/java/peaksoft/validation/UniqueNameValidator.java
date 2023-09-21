package peaksoft.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import peaksoft.exceptions.BadRequestException;
import peaksoft.models.Category;
import peaksoft.repository.CategoryRepository;


import java.util.List;

@RequiredArgsConstructor
public class UniqueNameValidator implements ConstraintValidator<UniqueName,String> {

    private final CategoryRepository categoryRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        List<Category> categories = categoryRepository.findAll().stream().filter(category -> category.getName().equalsIgnoreCase(value)).toList();
        if (categories.size() == 0) {
            return false;
        } else {
            throw new BadRequestException("The name is already exist !!!");
        }
    }
}
