package pro.abned.tomcatspringweb.axelors.rest;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class SessionInterceptor implements ClientHttpRequestInterceptor {
    private final SessionManager sessionManager;

    public SessionInterceptor(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        sessionManager.addSessionInfoToRequest(request);
        ClientHttpResponse response = execution.execute(request, body);
        sessionManager.extractSessionInfoFromResponse(response);
        return response;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
