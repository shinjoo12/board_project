package com.ohgiraffers.spring_project.service;

import com.ohgiraffers.spring_project.model.dto.BoardDTO;
import com.ohgiraffers.spring_project.model.entity.Post;
import com.ohgiraffers.spring_project.repository.BoardRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    private static BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }


    public void createPost(BoardDTO boardDTO){
        // 등록이 실패하면?
        // 만약 게시글 제목이 중복된다면?
        // 본문 내용이 중복된다면?
       Post post = new Post();
       post.setBoardTitle(boardDTO.getBoardTitle());
       post.setBoardContent(boardDTO.getBoardContent());
       post.setCreateDate(boardDTO.getCreateDate());
       boardRepository.save(post);

   }


    // 모든 게시물 목록을 반환
    public List<Post> getAllPosts() {
        // 조회한 게시글의 list가 없으면?
        return boardRepository.findAll();
    }

    // 게시물 ID로 게시물을 조회하는 메서드
    public Optional<Post> getPostById(Integer id) {
        // DTO 변환 필요

        // 데이터베이스에서 ID로 게시물을 조회하여 Optional로 반환
        return boardRepository.findById(id);
    }


    public void updatePost(BoardDTO boardDTO) {
        // 게시글 ID로 데이터베이스에서 게시글을 조회
        Optional<Post> post = boardRepository.findById(boardDTO.getId());
        //조회한 게시글이 없으면?
        post.isPresent(); // 존재하면 true를 반환함
        Post findPost = post.get();// null이면 에러가 발생함

        findPost.setBoardTitle(boardDTO.getBoardTitle());
        findPost.setBoardContent(boardDTO.getBoardContent());

        // 게시글 제목이 null이 아닌 경우, 게시글 객체의 제목을 업데이트
        // 컨트롤러
        if (boardDTO.getBoardTitle() != null) {

        }
        if (boardDTO.getBoardContent() != null) {
        }
        //게시글 정보 저장
        boardRepository.save(post);
    }


}
