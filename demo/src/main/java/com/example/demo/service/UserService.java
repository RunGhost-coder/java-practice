package com.example.demo.service;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 缓存 key 前缀
    private static final String USER_CACHE_KEY_PREFIX = "user:";
    // 缓存过期时间（秒）
    private static final long CACHE_TTL = 60;

    public List<User> getAllUsers() {
        return userMapper.findAll();
    }

    public User getUserById(Long id) {
        String cacheKey = USER_CACHE_KEY_PREFIX + id;
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();

        // 1. 尝试从缓存获取
        User user = (User) ops.get(cacheKey);
        if (user != null) {
            return user; // 缓存命中
        }

        // 2. 缓存未命中，查数据库
        user = userMapper.findById(id);
        if (user != null) {
            // 3. 写入缓存，并设置过期时间
            System.out.println("数据库查到用户，写入Redis缓存：" + cacheKey);
            ops.set(cacheKey, user, CACHE_TTL, TimeUnit.SECONDS);
        }
        return user;
    }

    public int createUser(User user) {
        return userMapper.insert(user);
    }

    // 更新用户：需要删除缓存
    public int updateUser(User user) {
        int result = userMapper.update(user);
        if (result > 0 && user.getId() != null) {
            String cacheKey = USER_CACHE_KEY_PREFIX + user.getId();
            redisTemplate.delete(cacheKey); // 删除旧缓存
        }
        return result;
    }

    public int deleteUser(Long id) {
        int result = userMapper.deleteById(id);
        if (result > 0) {
            String cacheKey = USER_CACHE_KEY_PREFIX + id;
            redisTemplate.delete(cacheKey);
        }
        return result;
    }
}