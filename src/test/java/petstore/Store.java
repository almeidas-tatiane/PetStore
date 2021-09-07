package petstore;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

public class Store {
    String uri = "https://petstore.swagger.io/v2/store/order";

    public String lerJson (String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    @Test(priority=1)
    public void incluirOrder() throws IOException {
        String jsonBody = lerJson("db/order1.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .post(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("id", is(1))
                .body("petId", is (1))
                .body("quantity", is (10))
                .body("status", is ("placed"));
    }

    @Test(priority=2)
    public void pesquisarOrderById() throws IOException {
        Integer orderId = 1;

        given()
                .contentType("application/json")
                .log().all()

        .when()
                .get(uri + "/" + orderId)
        .then()
                .log().all()
                .statusCode(200)
                .body("id", is(1))
                .body("petId", is (1))
                .body("quantity", is (10))
                .body("status", is ("placed"));
    }

    @Test(priority=3)
    public void deletarOrderById() throws IOException{
        Integer orderId = 1;

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .delete(uri + "/" + orderId)
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"));
        }

}
