package com.example.demo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "user_logs")  // 指定 MongoDB 中的集合名
public class UserLog {

    @Id
    private String id;                // MongoDB 的 _id 是 String 类型
    private Long userId;              // 操作用户的 ID（对应 MySQL 中的用户）
    private String operation;         // 操作类型：QUERY, INSERT, UPDATE, DELETE
    private String details;           // 操作详情，比如查询的参数、修改的内容
    private Date createTime;           // 操作时间

    // 必须包含无参构造（默认存在）

    // 生成 getter 和 setter（可以用 IDEA 快捷键 Alt+Insert）
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getOperation() { return operation; }
    public void setOperation(String operation) { this.operation = operation; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
}