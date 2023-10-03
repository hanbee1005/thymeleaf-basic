package hello.thymeleaf.login.web.item.form;

import hello.thymeleaf.login.domain.item.LoginItem;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LoginItemUpdateForm {

    @NotNull
    private Long id;

    @NotBlank
    private String itemName;

    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;

    //수정에서는 수량은 자유롭게 변경할 수 있다.
    private Integer quantity;

    public LoginItem convertToLoginItem() {
        LoginItem item = new LoginItem();
        item.setId(id);
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        return item;
    }

}