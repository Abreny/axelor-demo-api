package pro.abned.tomcatspringweb.axelors.auths;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pro.abned.tomcatspringweb.axelors.AxelorSetting;

public class AxelorLogin {
    public static final String PATH = "callback";
    private final RestTemplate restTemplate;
    private final AxelorSetting settings;

    public AxelorLogin(RestTemplate restTemplate, AxelorSetting settings) {
        this.restTemplate = restTemplate;
        this.settings = settings;
    }

    public boolean authenticate(String username, String password) {
        final LoginRequest loginRequest = new LoginRequest(username, password);
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity(String.format("%s/%s", settings.getHost(), PATH), loginRequest, Void.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException(String.format("axelor.authenticate.error#[%s,%s,%s]", settings.getHost(), username, password));
        }
        return true;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private static class LoginRequest {
        private String username;
        private String password;
    }
}
