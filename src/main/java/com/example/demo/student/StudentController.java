package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @PostMapping("/")
    public ResponseEntity<?> addNewStudent(@RequestBody Student student) {
        try {
            studentService.addNewStudent(student);
        }
        catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(String.format("Student with the email %s was added successfully!", student.getEmail()), HttpStatus.CREATED);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable("studentId") Integer studentId) {
        try {
            studentService.deleteStudent(studentId);
        }
        catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(String.format("Student with the id %s was successfully deleted!", studentId), HttpStatus.OK);
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<?> updateStudent(@PathVariable("studentId") Integer studentId, @RequestBody StudentUpdateDTO student) {
        try {
            studentService.updateStudent(studentId, student);
        }
        catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Student info was successfully updated!", HttpStatus.OK);
    }

}

