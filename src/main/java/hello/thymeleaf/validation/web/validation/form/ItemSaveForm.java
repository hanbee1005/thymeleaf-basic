package hello.thymeleaf.validation.web.validation.form;

import hello.thymeleaf.validation.domain.item.ValidationItem;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ItemSaveForm {
    @NotBlank
    private String itemName;
    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;
    @NotNull
    @Max(9999)
    private Integer quantity;

    public ValidationItem convertToValidationItem() {
        return new ValidationItem(itemName, price, quantity);
    }
}
