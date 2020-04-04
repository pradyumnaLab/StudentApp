package com.pk.StudentApp.Controller;

import com.pk.StudentApp.Domain.Student;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/students/")
public class StudentManagementController {

    private static  final List<Student> studentList = Arrays.asList(new Student(308,"Amit"),
            new Student(705,"Pk"),new Student(755,"Ashwani"),
            new Student(703,"Arup"));

    @GetMapping("allStudents")
    public List<Student> getAllStudents(){
        return studentList;
    }

    @PostMapping("createStudent")
    public void createStudent(@RequestBody Student student){
        System.out.println("Create Student : " + student);
    }

    @DeleteMapping("deleteStudent/{studentId}")
    public void deleteStudentById(@PathVariable Integer studentId){
        System.out.println("Delete Student Id: " + studentId);
    }

    @PutMapping("updateStudent/{studentId}")
    public void updateStudent(@PathVariable Integer studentId, @RequestBody Student student){
        System.out.println(String.format("%s %s", studentId, student));
    }
}
