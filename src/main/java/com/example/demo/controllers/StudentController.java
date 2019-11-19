package com.example.demo.controllers;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import com.example.demo.handler.StudentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.students.Student;
import com.example.demo.students.StudentRepository;

@RestController
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    @GetMapping(value = "student/{id}", produces="application/json")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) throws NoSuchElementException {
        if(studentRepository.findById(id)!=null){
            return new ResponseEntity<>(studentRepository.findById(id).get(),HttpStatus.OK);
        }
        else{
            throw new StudentNotFoundException();
        }
    }

    @GetMapping(value = "students/{firstName}", produces = "application/json")
    public ResponseEntity<List<Student>> getStudentsByFirstName(@PathVariable String firstName){
        if (studentRepository.findByFirstName(firstName) != null) {
            return new ResponseEntity<>(studentRepository.findByFirstName(firstName), HttpStatus.OK);
        }
        else{
            throw new StudentNotFoundException();
        }
    }

    @PostMapping(value = "student", produces = "application/json")
    public ResponseEntity<Student> createStudent(@RequestBody Student request) {
            studentRepository.save(new Student(request.getFirstName(), request.getLastName()));
            return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("student")
    public ResponseEntity<Student> updateStudent(@RequestBody Student request) {
        Optional<Student> student = studentRepository.findById(request.getId());
        if (student.isPresent()) {
            student.get().setFirstName(request.getFirstName());
            student.get().setLastName(request.getLastName());
            studentRepository.save(student.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            throw new StudentNotFoundException();
        }
    }

    @DeleteMapping("student/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if(student.isPresent()){
            studentRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            throw new StudentNotFoundException();
    }

    }
}