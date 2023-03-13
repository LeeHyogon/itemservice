package hello.itemservice.domain.item;

import lombok.Data;

@Data
public class ItemDto {
    private String itemName;
    private Integer price;
    private Integer quantity;


}

