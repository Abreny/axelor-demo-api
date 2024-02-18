package pro.abned.tomcatspringweb.axelors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import pro.abned.tomcatspringweb.axelors.rest.request_type.AxelorCreate;
import pro.abned.tomcatspringweb.axelors.rest.request_type.AxelorRequestable;
import pro.abned.tomcatspringweb.axelors.rest.request_type.AxelorSearch;
import pro.abned.tomcatspringweb.axelors.rest.request_type.AxelorVersion;

public abstract class AxelorRequest<T> {
    protected final AxelorManager axelorManager;

    public AxelorRequest(AxelorManager axelorManager) {
        this.axelorManager = axelorManager;
    }

    public AxelorModelResponse<T> get(int offset, int limit) {
        return _retryRequest(new AxelorSearch(offset, limit, _getPath(), axelorManager, _getFields()));
    }

    public AxelorModelResponse<T> get(int version) {
        return _retryRequest(new AxelorVersion(version, _getPath(), axelorManager));
    }

    public AxelorModelResponse<T> create(Object data) {
        return _retryRequest(new AxelorCreate(data, _getPath(), axelorManager));
    }

    private AxelorModelResponse<T> _retryRequest(AxelorRequestable requestable) {
        boolean retry = true;
        int limit = 2;
        while (limit > 0 && retry) {
            try {
                return _execute(requestable);
            } catch (HttpClientErrorException exception) {
                if (exception.getStatusCode().value() == 401) {
                    retry = axelorManager.checkLogin();
                    limit--;
                }
            }
        }
        throw requestable.getResponseFailed();
    }

    private AxelorModelResponse<T> _execute(AxelorRequestable requestable) {
        ResponseEntity<String> responseEntity = requestable.execute();

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw requestable.getRequestFailed();
        }
        try {
            return parseAxelResponse(responseEntity);
        } catch (JsonProcessingException e) {
            throw requestable.getResponseFailed();
        }
    }

    protected AxelorModelResponse<T> parseAxelResponse(ResponseEntity<String> responseEntity) throws JsonProcessingException {
        return _getMapper().readValue(responseEntity.getBody(), getResponseType());
    }

    protected ObjectMapper _getMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    protected String[] _getFields() {
        return null;
    }

    abstract protected String _getPath();
    abstract protected TypeReference<AxelorModelResponse<T>> getResponseType();
}
