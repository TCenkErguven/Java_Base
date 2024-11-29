package e2e;

import com.hazelcast.HazelcastServiceApplication;
import com.hazelcast.model.Custom;
import com.hazelcast.repository.CustomRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;



@SpringBootTest(classes = HazelcastServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HazelCastServiceE2ETest {
    /*
    @Autowired
    private CustomRepository customRepository;

    @LocalServerPort
    private Integer port;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    public static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        customRepository.deleteAll();
    }

    @Test
    public void shouldGetAllDto(){;
        //  customRepository.save( new Custom(null,"ses","1232-3211-3455-4321"));
        //  customRepository.save( new Custom(null,"ses2","51232-3211-3455-4321"));

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/find-by-uuid/2123312")
                .then()
                .statusCode(200)
                .body(".", hasSize(1));
    }
*/

}
