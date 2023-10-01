package hello.thymeleaf.validation.domain.item;

import lombok.Data;

@Data
public class ValidationItem {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public ValidationItem() {
    }

    public ValidationItem(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
