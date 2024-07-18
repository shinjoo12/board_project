package com.ohgiraffers.spring_project.service;

import com.ohgiraffers.spring_project.model.dto.BoardDTO;
import com.ohgiraffers.spring_project.model.entity.Post;
import com.ohgiraffers.spring_project.repository.BoardRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    private static BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 비즈니스 로직을 작성함
    @Transactional
    public String createPost(BoardDTO boardDTO, RedirectAttributes redirectAttributes) { // 반환타입 정하기
        // 만약 게시글 제목이 중복된다면? findPost 게시글 제목
        Post findPost = boardRepository.findByBoardTitle(boardDTO.getBoardTitle());
        // findPost 중복됨
        if (findPost != null) {
            redirectAttributes.addFlashAttribute("message", "게시글 제목 중복");
            return "redirect:/board";

            //boardRepository.save(findPost);
            // 등록 안됨 ?

        }

        // 사용자 입력 데이터를 DTO로 담아줌
        Post post = new Post();
        post.setCreateDate(new Date());
        post.setBoardContent(boardDTO.getBoardContent());
        post.setBoardTitle(boardDTO.getBoardTitle());

        boardRepository.save(post);
        redirectAttributes.addFlashAttribute("success", "게시글 성공적으로 등록");
        return "redirect:/board";


    }


    // 모든 게시물 목록을 반환
    public List<Post> getAllPosts() {
        List<Post> lists = boardRepository.findAll();
        //조회한 게시글의 list가 없으면?
        // if문?
        // (조건은 어떤걸 넣어야하지?)
        // 리스트가 제대로 넘어왔다면?
        // 넘어온 리스트를 컨트롤러로 넘긴다.
        // 안넘어 오면?
        // 오류 메시지를 보낸다.
        // 순서대로 코드 작성
        if (lists.isEmpty()) {
            System.out.println("조회된 게시글의 리스트가 없습니다");
        }

        return lists;
    }

    // 게시물 ID로 게시물을 조회하는 메서드
    public Optional<Post> getPostById(Integer id) {
        // DTO 변환 필요

        // 데이터베이스에서 ID로 게시물을 조회하여 Optional로 반환
        return boardRepository.findById(id);
    }


    public Optional<Post> updatePost(BoardDTO boardDTO) {
        // 게시글 ID로 데이터베이스에서 게시글을 조회
        Optional<Post> post = boardRepository.findById(boardDTO.getId());
        //조회한 게시글이 없으면?
        //게시글이 존재한다면
        if (post.isPresent()) {
            return post;
        } else {
            System.out.println("게시글이 존재하지 않습니다.");

        }
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
        // boardRepository.save(post);
        return post;
    }


}
