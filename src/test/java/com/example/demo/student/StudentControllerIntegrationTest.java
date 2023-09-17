package com.example.demo.student;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.hasSize;





@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerIntegrationTest extends TestContainerSetUp {

    @LocalServerPort
    private Integer port;
    @Autowired
    StudentService studentService;

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
                .get("/api/v1/students")
                .then()
                .statusCode(200)
                .body(".", hasSize(2));

    }
}
