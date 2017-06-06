package validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
 
public class LengthValidator implements ConstraintValidator<LengthValidatorAnotation, String> {
 
  private int min;
 
  private int max;
 
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }
 System.out.println(max);
    return (value.length() >= min && value.length() <= max);
  }

  @Override
  public void initialize(LengthValidatorAnotation annotation) {
	  min = annotation.min();
      max = annotation.max();
  }
}