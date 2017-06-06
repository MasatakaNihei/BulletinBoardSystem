package validator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
 
@Documented
@Constraint(validatedBy = {LengthValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface LengthValidatorAnotation {
  String message() default "文字列長が不正です。";
 
  Class<?>[] groups() default {};
 
  Class<? extends Payload>[] payload() default {};
 
  int min() default 0;
 
  int max();
 
  @Target({FIELD})
  @Retention(RUNTIME)
  @Documented
  public @interface List {
    LengthValidatorAnotation[] value();
  }
}