package com.pk.StudentApp.Controller;

import com.pk.StudentApp.Domain.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/students/")
public class StudentController {

    private static  final List<Student> studentList = Arrays.asList(new Student(308,"Amit"),
            new Student(705,"Pk"),new Student(755,"Ashwani"),
            new Student(703,"Arup"));

    @GetMapping(path = "{studentId}")
    public Student getStudent(@PathVariable("studentId") Integer studentId){
        Student student = studentList.stream()
                .filter(p -> p.getStudentId().equals(studentId))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Student Id not found ******"));

        return student;
    }
}
