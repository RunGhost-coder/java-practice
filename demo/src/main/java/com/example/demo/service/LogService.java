package com.example.demo.service;

import com.example.demo.entity.UserLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class LogService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 保存操作日志到 MongoDB
     */
    public void saveLog(UserLog log) {
        mongoTemplate.save(log);
    }
}