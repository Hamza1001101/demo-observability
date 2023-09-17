package com.example.demo.student;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerIntegrationTest extends TestContainerSetUp {

    @LocalServerPort
    private Integer port;
    @Autowired
    StudentService studentService;
    private final String API_URL = "/api/v1/students";

    @BeforeEach
    void setUp() {
        baseURI = "http://localhost:" + port;
        studentService.deleteAllStudents();
    }

    @Test
    void shouldReturnAllStudents() {
        List<Student> customers = List.of(
                new Student(null, "John", "john@mail.com"),
                new Student(null, "Dennis", "dennis@mail.com")
        );
        studentService.saveAll(customers);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get(API_URL)
                .then()
                .statusCode(200)
                .body(".", hasSize(2))
                .body("[0].firstName", equalTo("John"));

    }

    @Test
    void shouldReturnStudentWithSpecificId() {
        var student = new Student(null, "John", "Doe");
        studentService.addANewStudent(student);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get(API_URL + "/" + student.getId())
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("firstName", equalTo("John"))
                .body("lastName", equalTo("Doe"));
    }

    @Test
    void shouldPostAnewStudent() {

        with().body(new Student(null, "Farah", "Doe"))
                .contentType(ContentType.JSON)
                .when()
                .request("POST", API_URL)
                .then()
                .statusCode(201)
                .log();
    }
}
