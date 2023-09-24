package peaksoft.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import peaksoft.exceptions.BadRequestException;

public class PassValidator implements ConstraintValidator<PassValid,String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value.length()>=4) {
            return value.length() > 4;
        }else {
            throw new BadRequestException("Password should contains more than 4 symbols !!!");
        }
    }
}
