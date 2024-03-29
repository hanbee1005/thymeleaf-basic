package hello.thymeleaf.login.web.item.form;

import hello.thymeleaf.login.domain.item.LoginItem;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LoginItemSaveForm {

    @NotBlank
    private String itemName;

    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;

    @NotNull
    @Max(value = 9999)
    private Integer quantity;

    public LoginItem convertToLoginItem() {
        return new LoginItem(itemName, price, quantity);
    }

}