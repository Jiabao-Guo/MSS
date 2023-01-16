package com.example.munselfservice.controller;


import com.example.munselfservice.entity.Instructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        // 搜数据库，看谁的名字含有 name ，把他们返回
        return instructorRepository.findByNameContaining(name);
    }

    @RequestMapping("/instructor")
                            //@Request..前段传来的数据
    Page<Instructor> findAll(@RequestParam Integer page, @RequestParam Integer amount) {
        //构造pageable
        Pageable pageable = PageRequest.of(page, amount);
        return instructorRepository.findAll(pageable);
    }
}
