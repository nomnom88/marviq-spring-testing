package nl.sourcelabs.workshop.testing.contract;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.WebApplicationContext;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ContractTestBase {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }


}
