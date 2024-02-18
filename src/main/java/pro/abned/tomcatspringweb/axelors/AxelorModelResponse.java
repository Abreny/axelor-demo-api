package pro.abned.tomcatspringweb.axelors;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AxelorModelResponse<T> {
    private Integer status;
    private Integer offset;
    private Long total;
    private List<T> data;
}
