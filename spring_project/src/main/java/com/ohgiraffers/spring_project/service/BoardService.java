package com.ohgiraffers.spring_project.service;

import com.ohgiraffers.spring_project.model.dto.BoardDTO;
import com.ohgiraffers.spring_project.model.entity.Post;
import com.ohgiraffers.spring_project.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

   public void createPost(BoardDTO boardDTO){

       Post post = new Post();
       post.setBoardTitle(boardDTO.getBoardTitle());
       post.setBoardContent(boardDTO.getBoardContent());
       post.setCreateDate(boardDTO.getCreateDate());
       boardRepository.save(post);

   }
}
