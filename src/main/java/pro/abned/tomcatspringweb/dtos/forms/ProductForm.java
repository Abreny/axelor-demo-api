package pro.abned.tomcatspringweb.dtos.forms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductForm {
    private String name;
    private String code;
    private String ean;
    private String description;
    private Double price;
    private Double quantity;
    private Double weight;
    private Double volume;
}
