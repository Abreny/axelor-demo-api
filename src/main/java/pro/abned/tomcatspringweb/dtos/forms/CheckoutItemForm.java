package pro.abned.tomcatspringweb.dtos.forms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckoutItemForm {
    private Long product;
    private Double quantity;
}
