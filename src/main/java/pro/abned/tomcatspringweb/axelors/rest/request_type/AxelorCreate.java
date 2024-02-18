package pro.abned.tomcatspringweb.axelors.rest.request_type;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import pro.abned.tomcatspringweb.axelors.AxelorManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AxelorCreate implements AxelorRequestable {
    private Map<String, Object> params;
    private final String path;
    private final AxelorManager axelorManager;

    public AxelorCreate(Object data, String path, AxelorManager axelorManager) {
        this.params = new HashMap<>();
        this.params.put("data", data);
        this.path = path;
        this.axelorManager = axelorManager;
    }

    @Override
    public ResponseEntity<String> execute() {
        HttpEntity<?> request = new HttpEntity<>(params, null);
        return axelorManager.getRestTemplate().exchange(String.format("%s/%s", axelorManager.getAxelorSetting().getHost(), path), HttpMethod.PUT, request, String.class);
    }

    @Override
    public IllegalArgumentException getRequestFailed() {
        return new IllegalArgumentException(String.format("axelor.create.error#[%s]", axelorManager.getAxelorSetting().getHost()));
    }

    @Override
    public IllegalArgumentException getResponseFailed() {
        return new IllegalArgumentException(String.format("axelor.create.parser.error#[%s]", axelorManager.getAxelorSetting().getHost()));
    }
}
