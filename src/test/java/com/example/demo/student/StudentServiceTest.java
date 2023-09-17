package com.example.demo.student;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    StudentRepository repository;
    @InjectMocks
    StudentService studentService;

    @Test
    void shoutReturnAllStudents() {

        when(repository.findAll())
                .thenReturn(getStudentList());

        var students = studentService.getAllStudents();

        assertThat(students).hasSize(2);
        verify(repository, times(1)).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldAddANewStudent() {

        var expectedStudent = new Student(10_00000L, "Hamza", "Hayd");

        when(repository.save(any(Student.class)))
                .thenReturn(expectedStudent);

        var actual = studentService.addANewStudent(new Student());

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expectedStudent);

        verify(repository, times(1)).save(any(Student.class));
        verifyNoMoreInteractions(repository);
    }



    private List<Student> getStudentList() {

        return List.of(new Student(5L, "Aneer", "Ulf"),
                new Student(8L, "Stefan", "Steding"));

    }
}