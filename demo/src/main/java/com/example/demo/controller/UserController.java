package com.example.demo.controller;
import com.example.demo.entity.User;
import com.example.demo.entity.EsUser;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    //查询所有用户
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    //更具ID查询用户
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    //创建用户
    @PostMapping
    public  String createUser(@RequestBody User user){
        int rows = userService.createUser(user);
        return rows > 0 ? "创建成功，ID:" + user.getId():"创建失败！";
    }

    //更新用户
    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, @RequestBody User user){
        user.setId(id);
        int rows = userService.updateUser(user);
        return rows > 0 ? "更新成功！" :"更新失败！";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id){
        int rows = userService.deleteUser(id);
        return rows > 0 ? "删除成功！" :"删除失败！";
    }

    @GetMapping("/search")
    public List<EsUser> searchUsers(@RequestParam String keyword) {
        return userService.searchUsers(keyword);
    }

}
