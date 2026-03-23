package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.entity.UserLog;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private LogService logService;



    // 缓存 key 前缀
    private static final String USER_CACHE_KEY_PREFIX = "user:";
    // 缓存过期时间（秒）
    private static final long CACHE_TTL = 60;

    public List<User> getAllUsers() {
        recordLog(null,"QUERY", "批量查询所有用户");
        System.out.println("批量查询所有用户");
        return userMapper.findAll();
    }

    public User getUserById(Long id) {
        String cacheKey = USER_CACHE_KEY_PREFIX + id;
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();

        // 1. 尝试从缓存获取
        User user = (User) ops.get(cacheKey);
        if (user != null) {
            recordLog(id, "QUERY", "从缓存查询用户ID: " + id);
            return user; // 缓存命中
        }

        // 2. 缓存未命中，查数据库
        user = userMapper.findById(id);
        if (user != null) {
            // 3. 写入缓存，并设置过期时间
            System.out.println("数据库查到用户，写入Redis缓存：" + cacheKey);
            ops.set(cacheKey, user, CACHE_TTL, TimeUnit.SECONDS);
        }
        recordLog(id, "QUERY", "从数据库查询用户ID: " + id);
        return user;
    }

    public int createUser(User user) {
        int result = userMapper.insert(user);
        if (result > 0) {
            recordLog(user.getId(),"INSERT","新增用户信息为："+user.getId());
        }
        return result;
    }

    // 更新用户：需要删除缓存
    public int updateUser(User user) {
        int result = userMapper.update(user);
        if (result > 0 && user.getId() != null) {
            String cacheKey = USER_CACHE_KEY_PREFIX + user.getId();
            redisTemplate.delete(cacheKey); // 删除旧缓存
            recordLog(user.getId(), "UPDATE", "更新用户信息为: " + user.toString());
        }
        return result;
    }

    public int deleteUser(Long id) {
        int result = userMapper.deleteById(id);
        if (result > 0) {
            String cacheKey = USER_CACHE_KEY_PREFIX + id;
            redisTemplate.delete(cacheKey);
            recordLog(id, "DELETE", "删除用户ID: " + id);
        }
        return result;
    }

    private  void recordLog(Long userId, String operation, String details){
        UserLog log = new UserLog();
        log.setUserId(userId);
        log.setOperation(operation);
        log.setDetails(details);
        log.setCreateTime(new Date());
        logService.saveLog(log);

    }
}