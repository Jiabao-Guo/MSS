package com.example.munselfservice.controller;

import com.example.munselfservice.entity.Course;
import com.example.munselfservice.entity.Student;
import com.example.munselfservice.object.CourseForm;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CourseController extends BaseController{

    @RequestMapping("/course")
    public List<Course> getCourses(@RequestBody CourseForm courseForm) {
        Student student = validateStudentSession(courseForm.getStudentNumber(),
                                                 courseForm.getSessionId());

        if (student == null) {
            return new ArrayList<>();
        }

        return courseRepository.findAll();
    }
}


/*

Controller 写法大全

1. 列出信息类：getCourses, searchInstructors
    返回: List of entities 比如 List<Course> List<Instructor>
    参数: @RequestBody xxxForm
    其中xxxForm是一个类，里面包含着查询条件（第几页 叫什么名字）、身份认证信息（SessionID）等等
    加@RequestBody是为了把从前端发来的表单信息，转换成对应的xxxForm类

2. 新增、删除、其他操作： addInstructor, login
    返回：ResponseEntity<xxxResponse>其中xxxResponse包含是否操作成功，为什么不成功的信息
    参数：@RequestBody xxxForm
    其中xxxForm是一个类，里面包含着进行操作所需的信息（比如新增教师的信息、登录所需用户名密码）
    和身份认证信息（SessionID）等等

3. 更新类 updateCourses, addInstructor
    返回：ResponseEntity<xxxResponse>其中xxxResponse包含是否操作成功，为什么不成功的信息
    参数：@RequestBody xxxEntity 其中xxxEntity是一个在entity包里面的实体类

3. 上传文件

4.

 */