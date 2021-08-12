package petstore;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

public class Pet {
    String uri = "https://petstore.swagger.io/v2/pet";

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    @Test
    public void incluirPet() throws IOException {
       String jsonBody = lerJson("db/pet1.json");

       given()
               .contentType("application/json")
               .log().all()
               .body(jsonBody)
       .when()
               .post(uri)
       .then()
               .log().all()
               .statusCode(200)
               .body("name",is("Totó"))
               .body("status", is("available"))
               .body("category.name", is("dog"))
               .body("tags.name", contains("sta"))
               ;
    }

    @Test
    public void consultarPet(){
        String petId = "1952121522";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "/" + petId)

        .then()
                .log().all()
                .statusCode(200)
                .body("id", is(1952121522))
                .body("category.name", is("dog"))

        ;

    }


}
