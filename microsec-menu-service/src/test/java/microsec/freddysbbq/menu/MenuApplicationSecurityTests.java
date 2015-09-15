package microsec.freddysbbq.menu;

import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;

import microsec.test.SecurityIntegrationTest;
import microsec.test.UaaJwtToken;
import microsec.test.UaaJwtToken.UaaJwtTokenBuilder;

@SpringApplicationConfiguration(classes = MenuApplication.class)
@TestPropertySource(properties = "security.require-ssl=true")
public class MenuApplicationSecurityTests extends SecurityIntegrationTest {

    @Test
    public void testMenuItemsSecurity() throws Exception {
        checkHttpsRedirect("/menuItems");

        HttpResponse response = httpsRequest("/menuItems");
        Assert.assertEquals(401, response.getStatusLine().getStatusCode());

        UaaJwtTokenBuilder tokenBuilder = UaaJwtToken.builder();
        response = httpsRequest("/menuItems", tokenBuilder.build());
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());

    }

}