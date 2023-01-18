package com.example.munselfservice.controller.forms;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CourseRegistrationResponse {

    //根据CourseRegistrationController  编写出 Response
    // @RequestMapping("/course-registration")
    //    public ResponseEntity<CourseRegistrationResponse> courseRegistration(
    //            @RequestBody CourseRegistrationForm courseRegistrationForm){
    Boolean isSuccess;

    String messages;


}
