package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

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

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateStudent(Integer studentId, StudentUpdateDTO studentDTO) throws IllegalArgumentException {

        // TODO: Find a better way to write this method (including the DTO class)
        Optional<Student> studentToUpdate = studentRepository.findById(studentId);

        if (studentToUpdate.isEmpty())
            throw new IllegalArgumentException(String.format("A user with the id %s does not exist!", studentId));

        studentToUpdate.get().setName(studentDTO.getName());
        studentToUpdate.get().setEmail(studentDTO.getEmail());
    }
}
