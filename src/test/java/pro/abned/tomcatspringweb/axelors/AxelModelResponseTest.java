package pro.abned.tomcatspringweb.axelors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pro.abned.tomcatspringweb.axelors.products.AxelorProduct;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AxelModelResponseTest {
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Test
    @SuppressWarnings("unchecked")
    void testDeserializeProduct() throws JsonProcessingException {
        String rawString = """
                        {
                        	"status": 0,
                        	"offset": 0,
                        	"total": 1,
                        	"data": [
                        		{
                        			"salePrice": "20.9900000000",
                        			"fullName": "[P000] First Product Test",
                        			"description": "Best product ever",
                        			"id": 1,
                        			"version": 1,
                        			"picture": null,
                        			"$wkfStatus": null
                        		}
                        	]
                        }
                """;
        TypeReference<AxelorModelResponse<AxelorProduct>> typeReference = new TypeReference<AxelorModelResponse<AxelorProduct>>() {
        };
        AxelorModelResponse<AxelorProduct> products = objectMapper.readValue(rawString, typeReference);
        assertThat(products).isNotNull();
        assertThat(products.getTotal()).isEqualTo(1);
        assertThat(products.getOffset()).isEqualTo(0);

        List<AxelorProduct> productsData = products.getData();
        assertThat(productsData).isNotEmpty();
        assertThat(productsData.getFirst().getFullName()).isEqualTo("[P000] First Product Test");
    }
}