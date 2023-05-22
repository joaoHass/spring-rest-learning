package com.example.demo.student;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Transient;
import jakarta.persistence.PostLoad;

import java.time.LocalDate;
import java.time.Period;

@Entity
/* @Table -> We don't have to use the @Table annotation here because the table we will
*            be registering on the database has the same name as the entity, so by default
*            Hibernate uses the entity name to choose which table to use on the db.  */
public class Student {
    @Id
    /* The SequenceGenerator indicates that the responsible for generating the unique identifier
    *  will be the persistence provider (aka database). We pass the name of @SequenceGenerator on
    *  the 'generator' property, which will basically map to the sequence on the database*/
    @GeneratedValue (
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    @SequenceGenerator (
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    private Integer id;
    private String name;
    private String email;
    private LocalDate dateOfBirth;
    @Transient
    private Integer age;

    public Student() {}

    public Student(String name, String email, LocalDate dateOfBirth) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.age = Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        this.age = Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    public Integer getAge() {
        return age;
    }

    @PostLoad
    private void computeAge() {
        this.age = Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", age=" + age +
                '}';
    }
}
