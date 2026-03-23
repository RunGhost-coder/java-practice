package com.example.demo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "users") // 索引名称，相当于数据库中的表
public class EsUser {

    @Id
    private Long id; // 与 MySQL 中的用户 ID 一致

    @Field(type = FieldType.Text,analyzer = "ik_smart")
    private String name;

    @Field(type = FieldType.Integer)
    private Integer age;

    @Field(type = FieldType.Keyword) // 邮箱作为整体匹配，不分词
    private String email;

    // 必须有无参构造
    public EsUser() {}

    // getter 和 setter（IDEA 快捷键 Alt+Insert 生成）
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }



}
