package pro.abned.tomcatspringweb.axelors.products;

import com.fasterxml.jackson.core.type.TypeReference;
import pro.abned.tomcatspringweb.axelors.AxelorManager;
import pro.abned.tomcatspringweb.axelors.AxelorModelResponse;
import pro.abned.tomcatspringweb.axelors.AxelorRequest;

public class AxelorGetProduct extends AxelorRequest<AxelorProduct> {
    public static final String PATH = "ws/rest/com.axelor.apps.base.db.Product/search";
    public static final String[] FIELDS = {"fullName", "salePrice", "description", "picture", "code", "version"};

    public AxelorGetProduct(AxelorManager axelorManager) {
        super(axelorManager);
    }

    @Override
    protected TypeReference<AxelorModelResponse<AxelorProduct>> getResponseType() {
        return new TypeReference<AxelorModelResponse<AxelorProduct>>() {
        };
    }

    @Override
    protected String _getPath() {
        return PATH;
    }

    @Override
    protected String[] _getFields() {
        return FIELDS;
    }
}
