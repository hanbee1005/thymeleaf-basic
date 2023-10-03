package hello.thymeleaf.validation.web.validation.form;

import hello.thymeleaf.validation.domain.item.ValidationItem;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ItemUpdateForm {
    @NotNull
    private Long id;
    @NotBlank
    private String itemName;
    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;
    private Integer quantity;

    public ValidationItem convertToValidationItem() {
        ValidationItem validationItem = new ValidationItem();
        validationItem.setId(id);
        validationItem.setItemName(itemName);
        validationItem.setPrice(price);
        validationItem.setQuantity(quantity);

        return validationItem;
    }
}
