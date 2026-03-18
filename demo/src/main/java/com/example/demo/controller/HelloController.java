package com.example.demo.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.demo.entity.User;
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String sayHello(){
        return "Hello, Spring Boot!";
    }
    @GetMapping("/user/{id}")
    public String getUserById(@PathVariable Long id){
        return "查询用户ID：" + id;
    }
    @GetMapping("/user")
    public String getUserByName(@RequestParam String name){
        return "查询用户姓名" + name;
    }
    @PostMapping("/user")
    public String createUser(@RequestBody User user){
        return "创建用户:"+user.getName() +",年龄:"+user.getAge();
    }

}
