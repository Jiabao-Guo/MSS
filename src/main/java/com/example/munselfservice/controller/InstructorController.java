package com.example.munselfservice.controller;


import com.example.munselfservice.controller.forms.InstructorQueryForm;
import com.example.munselfservice.entity.Instructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InstructorController extends BaseController {

    /**
     * 根据教授的名字进行搜索，获取教授的列表
     * @param name 教授的名字的一部分
     * @return 搜到的教授列表
     */
    @RequestMapping("/instructor/{name}")
    List<Instructor> findByName(@PathVariable String name) {
        // 搜索之后 初步符合条件的 放入第一页(20条)
        Pageable pageable = PageRequest.of(0, 20);
        return instructorRepository.findByNameContaining(pageable, name).getContent();
    }

    @RequestMapping("/instructor/query")
    Page<Instructor> findBySalaryBetweenAndNameContaining(
            //Pageable前段造不出来 ,所以变成2个具体page 和 amount,  Page 与 Pageable为Java Spring Boot 特有类
            //page amount min max name 5个参数过多 归到forms包下的InstructorQueryForm中
            @RequestBody InstructorQueryForm form)

    {
        //将所有符合的搜索的 都归结到 多少页, 每页可以有多少条信息
        // InstructorQueryForm中的 通过form拿出来 form.getPage(),form.getAmount()
        Pageable pageable = PageRequest.of(form.getPage(),form.getAmount());
        return instructorRepository.findBySalaryBetweenAndNameContaining(
                pageable,
                form.getMin(),
                form.getMax(),
                form.getName());
    }

    @RequestMapping("/instructor")
                            //@Request..前段ProfessorInformation传来的数据
    Page<Instructor> findAll(@RequestParam Integer page, @RequestParam Integer amount) {
        //构造pageable
        Pageable pageable = PageRequest.of(page, amount);
        return instructorRepository.findAll(pageable);
    }
}
