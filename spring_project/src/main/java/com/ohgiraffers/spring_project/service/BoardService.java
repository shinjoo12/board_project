package com.ohgiraffers.spring_project.service;

import com.ohgiraffers.spring_project.model.dto.BoardDTO;
import com.ohgiraffers.spring_project.model.entity.Post;
import com.ohgiraffers.spring_project.repository.BoardRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
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
        //조회한 게시글의 목록list가 없으면?
        // 넘어온 리스트를 컨트롤러로 넘긴다.
        // 안넘어 오면?
        // 오류 메시지
        //게시글 목록이 비어있다면
        if (lists.isEmpty()) {
            throw new RuntimeException("게시글 목록이 비어 있습니다.");
        }
        return lists;
    }

    public BoardDTO find(int id){
        Optional<Post> post = boardRepository.findById(id);

        if(!post.isPresent()){
            return null;
        }
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBoardTitle(post.get().getBoardTitle());
        boardDTO.setBoardContent(post.get().getBoardContent());
        return boardDTO;
    }


    public Post getPostById(int id) {
        return boardRepository.findById(id).get();
    }
}



