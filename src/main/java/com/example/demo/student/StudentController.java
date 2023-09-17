package com.example.demo.student;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping
    ResponseEntity<Iterable<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PostMapping
    ResponseEntity<Student> postANewStudent(@RequestBody Student student, UriComponentsBuilder ucb) {
        var newStudent = studentService.addANewStudent(student);
        URI uri = ucb.path("api/v1/students/{id}")
                .buildAndExpand(newStudent.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("{firstName}")
    ResponseEntity<List<Student>> getStudentsWithSameFirstName(@PathVariable String firstName) {
        return ResponseEntity.ok(studentService.getStudentsWithSimilarFirstName(firstName));
    }

    @GetMapping("/{id}")
    ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

}
