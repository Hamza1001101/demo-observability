package com.example.demo.student;

import com.example.demo.QStudent;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    final StudentRepository repository;
    JPAQueryFactory queryFactory;


    public StudentService(StudentRepository repository, EntityManager em) {
        this.repository = repository;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Iterable<Student> getAllStudents() {
        return repository.findAll();
    }

    Student addANewStudent(Student student) {
        return repository.save(student);
    }

    List<Student> getStudentsWithSimilarFirstName(String firstName) {
        QStudent qStudent = getqStudent();
        return queryFactory.select(qStudent)
                .from(qStudent)
                .where(qStudent.firstName.eq(firstName))
                .fetchAll()
                .stream().toList();
    }

    public void deleteAllStudents() {
        repository.deleteAll();
    }

    public void saveAll(List<Student> customers) {
        repository.saveAll(customers);
    }

    public Optional<Student> getStudentById(Long id) {
        QStudent qStudent = getqStudent();
        return Optional.ofNullable(queryFactory.select(qStudent)
                .from(qStudent)
                .where(qStudent.id.eq(id))
                .fetchOne());
    }

    private static QStudent getqStudent() {
        return QStudent.student;
    }
}
