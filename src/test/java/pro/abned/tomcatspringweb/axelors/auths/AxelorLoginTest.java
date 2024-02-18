package pro.abned.tomcatspringweb.axelors.auths;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pro.abned.tomcatspringweb.axelors.AxelorSetting;
import pro.abned.tomcatspringweb.axelors.SessionAwareRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


class AxelorLoginTest {
    @Test
    void testLogin() {
        AxelorSetting setting = new AxelorSetting();
        setting.setHost("https://example.com/erp");

        SessionAwareRestTemplate restTemplate = mock(SessionAwareRestTemplate.class);
        when(restTemplate.postForEntity(eq("https://example.com/erp/callback"), notNull(), eq(Void.class))).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        AxelorLogin axelorLogin = new AxelorLogin(restTemplate, setting);

        boolean result = axelorLogin.authenticate("user1", "pass1");

        assertThat(result).isTrue();
        verify(restTemplate, times(1)).postForEntity(eq("https://example.com/erp/callback"), notNull(), eq(Void.class));
    }
}