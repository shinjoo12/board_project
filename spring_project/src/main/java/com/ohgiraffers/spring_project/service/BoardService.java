package com.ohgiraffers.spring_project.service;

import com.ohgiraffers.spring_project.model.dto.BoardDTO;
import com.ohgiraffers.spring_project.model.entity.Post;
import com.ohgiraffers.spring_project.repository.BoardRepository;
import jakarta.persistence.EntityNotFoundException;
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


    // 모든 게시물 목록 조회
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

//    public BoardDTO find(int id) {
//        Optional<Post> post = boardRepository.findById(id);
//
//        if (!post.isPresent()) {
//            return null;
//        }
//        BoardDTO boardDTO = new BoardDTO();
//        boardDTO.setBoardTitle(post.get().getBoardTitle());
//        boardDTO.setBoardContent(post.get().getBoardContent());
//        return boardDTO;
//    }

    // 게시글 상세 조회
    public BoardDTO getPostById(int id) {
        Post post = boardRepository.findById(id).get();
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(post.getId());
        boardDTO.setBoardTitle(post.getBoardTitle());
        boardDTO.setBoardContent(post.getBoardContent());
        return boardDTO;

    }


    // 게시글 수정
    public BoardDTO updatePost(int id, BoardDTO boardDTO) {
        // 게시글 업데이트 로직
        // 게시글 ID를 사용하여 데이터베이스에서 게시글을 조회
       Post updatedPost = boardRepository.findById(id).get();
        // optionalPost.isPresent() 메서드는 optionalPost가 값(게시글)을 가지고 있는지 확인

        if (updatedPost == null) {
            return null;
        }
        // optionalPost가 값(게시글)을 가지고 있다면, 해당 게시글 객체를 get으로 가져옴
        // 클라이언트가 전송한 업데이트된 게시글 제목과 내용으로 기존 게시글을 업데이트
        // 업데이트된 제목 설정
        updatedPost.setBoardTitle(boardDTO.getBoardTitle());
        // 업데이트된 내용 설정
        updatedPost.setBoardContent(boardDTO.getBoardContent());
        // 변경된 내용을 existingPost 데이터베이스에 저장
        // 수정 성공 여부가 없음
        Post modifyPost = boardRepository.save(updatedPost);

        BoardDTO modifyDTO = new BoardDTO();
        if(modifyPost != null){
            modifyDTO.setId(modifyPost.getId());
            modifyDTO.setBoardContent(modifyPost.getBoardContent());
            modifyDTO.setBoardTitle(modifyPost.getBoardTitle());
        }

          return modifyDTO;
    }

    public boolean deletePost(int id) {
        if (boardRepository.existsById(id)) {
            boardRepository.deleteById(id);
            return true;
        }
        return false;
    }
}




