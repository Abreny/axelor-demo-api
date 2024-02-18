package pro.abned.tomcatspringweb.axelors;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.mock.http.client.MockClientHttpRequest;
import org.springframework.mock.http.client.MockClientHttpResponse;
import pro.abned.tomcatspringweb.axelors.rest.SessionManager;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class SessionAwareRestTemplateTest {
    @Test
    void testSessionInfoAddedToRequest() throws IOException {
        MockClientHttpRequest request = new MockClientHttpRequest();
        ClientHttpRequestExecution execution = mock(ClientHttpRequestExecution.class);
        when(execution.execute(any(), any())).thenReturn(new MockClientHttpResponse(new byte[0], HttpStatus.OK));

        SessionManager sessionManager = new SessionManager();
        sessionManager.setCookie("testCookie");

        SessionAwareRestTemplate restTemplate = new SessionAwareRestTemplate(sessionManager);

        restTemplate.getInterceptors().getFirst().intercept(request, new byte[0], execution);

        HttpHeaders headers = request.getHeaders();
        assertThat(headers).containsKey("Cookie");
        assertThat(headers.get("Cookie").getFirst()).isEqualTo("testCookie");
    }

    @Test
    void testSessionInfoExtractedFromResponse() throws IOException {
        MockClientHttpResponse response = new MockClientHttpResponse(new byte[0], HttpStatus.OK);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", "sessionCookie=testCookie");
        response.getHeaders().putAll(headers);

        SessionManager sessionManager = mock(SessionManager.class);

        SessionAwareRestTemplate restTemplate = new SessionAwareRestTemplate(sessionManager);
        restTemplate.getInterceptors().getFirst().intercept(new MockClientHttpRequest(), new byte[0], executionWithResponse(response));

        verify(sessionManager, times(1)).extractSessionInfoFromResponse(response);
    }

    private ClientHttpRequestExecution executionWithResponse(ClientHttpResponse response) {
        return (req, body) -> response;
    }
}