package com.ohgiraffers.spring_project.repository;

import com.ohgiraffers.spring_project.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BoardRepository extends JpaRepository<Post, Integer> {
}
