package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) throws IllegalArgumentException {

        if (studentExists(student.getEmail()))
            throw new IllegalArgumentException(String.format("The e-mail %s is already taken", student.getEmail()));

        studentRepository.save(student);
    }

    public boolean studentExists(String studentEmail) {
        return studentRepository.findStudentByEmail(studentEmail).isPresent();
    }

    public void deleteStudent(Integer studentId) throws IllegalArgumentException {
        if (!studentRepository.existsById(studentId))
            throw new IllegalArgumentException(String.format("A user with the id %s does not exist!", studentId));

        studentRepository.deleteById(studentId);
    }
}
