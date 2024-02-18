package pro.abned.tomcatspringweb.dtos.forms;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CheckoutForm {
    private List<CheckoutItemForm> products;
}
