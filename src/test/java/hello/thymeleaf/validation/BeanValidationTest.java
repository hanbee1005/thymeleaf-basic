package hello.thymeleaf.validation;

import hello.thymeleaf.validation.domain.item.ValidationItem;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class BeanValidationTest {
    @Test
    public void beanValidation() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        ValidationItem item = new ValidationItem();
        item.setItemName(" ");
        item.setPrice(0);
        item.setQuantity(10000);

        Set<ConstraintViolation<ValidationItem>> violations = validator.validate(item);
        violations.forEach(v -> {
            System.out.println("violation = " + v);
            System.out.println("violation.getMessage = " + v.getMessage());
        });
    }
}
