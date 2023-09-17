package com.example.demo.student;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    StudentService studentService;


    @Test
    void shouldGetAllStudents() throws Exception {

        Mockito.when(studentService.getAllStudents())
                .thenReturn(getStudentList());

        mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("Aneer")))
                .andExpect(jsonPath("$[0].lastName", is("Ulf")));
    }

    @Test
    void shouldPostANewStudent() throws Exception {
        var expectedStudent = new Student(8L, "Stefan", "Steding");

        Mockito.when(studentService.addANewStudent(ArgumentMatchers.any(Student.class)))
                .thenReturn(expectedStudent);

        mockMvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJson()))
                .andExpect(status().isCreated());
    }

    @Disabled
    @Test
    void shouldGetStudentsWithSameFirstName() {
    }

    private List<Student> getStudentList() {
        return List.of(new Student(5L, "Aneer", "Ulf"),
                new Student(8L, "Stefan", "Steding"));
    }

    private String studentJson() {
        return "{\"firstName\": \"Stefan\", \"lastName\":\"Steding\"}";
    }
}