package com.example.demo.repository;

import com.example.demo.entity.EsUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface EsUserRepository extends ElasticsearchRepository<EsUser, Long> {

    // 根据姓名或邮箱搜索（框架会自动实现）
    List<EsUser> findByNameOrEmail(String name, String email);
}