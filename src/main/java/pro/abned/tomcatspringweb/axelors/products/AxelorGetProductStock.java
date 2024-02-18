package pro.abned.tomcatspringweb.axelors.products;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import pro.abned.tomcatspringweb.axelors.AxelorManager;
import pro.abned.tomcatspringweb.axelors.AxelorModelResponse;
import pro.abned.tomcatspringweb.axelors.AxelorRequest;

import java.util.List;

public class AxelorGetProductStock extends AxelorRequest<AxelorProductStock> {
    public static final String PATH_PATTERN = "ws/aos/stock-product/fetch-product-with-stock/%d";
    private Long id;

    public AxelorGetProductStock(AxelorManager axelorManager) {
        super(axelorManager);
    }

    public AxelorModelResponse<AxelorProductStock> getForProduct(AxelorProduct product) {
        this.id = product.getId();
        return super.get(product.getVersion());
    }

    @Override
    protected TypeReference<AxelorModelResponse<AxelorProductStock>> getResponseType() {
        return new TypeReference<AxelorModelResponse<AxelorProductStock>>() {};
    }

    @Override
    protected String _getPath() {
        return String.format(PATH_PATTERN, id);
    }

    @Override
    protected AxelorModelResponse<AxelorProductStock> parseAxelResponse(ResponseEntity<String> responseEntity) throws JsonProcessingException {
        final AxelorStockResponse _response = _getMapper().readValue(responseEntity.getBody(), AxelorStockResponse.class);

        return _response.toGenericResponse();
    }

    @Getter
    @Setter
    private static class AxelorStockResponse {
        private Integer codeStatus;
        private String messageStatus;
        private AxelorProductStock object;

        public AxelorModelResponse<AxelorProductStock> toGenericResponse() {
            final AxelorModelResponse<AxelorProductStock> prodStock = new AxelorModelResponse<>();
            prodStock.setStatus(codeStatus == 200 ? 1 : -1);
            prodStock.setOffset(0);
            if (object != null) {
                prodStock.setData(List.of(object));
                prodStock.setTotal(1L);
            }
            return  prodStock;
        }
    }
}
