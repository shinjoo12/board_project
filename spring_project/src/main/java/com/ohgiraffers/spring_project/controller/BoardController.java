package com.ohgiraffers.spring_project.controller;


import com.ohgiraffers.spring_project.model.dto.BoardDTO;
import com.ohgiraffers.spring_project.model.entity.Post;
import com.ohgiraffers.spring_project.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;


@Controller
public class BoardController {

    // BoardService 인터페이스를 구현한 객체를 주입받기 위한 필드
    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        // 생성자를 통해 BoardService 객체를 주입받음
        this.boardService = boardService;
    }

    // 메인 페이지 요청 처리
    @GetMapping("/")
    public String mainpage() {
        // mainpage.html을 반환하여 메인 페이지를 표시
        return "mainpage";
    }

    // 게시판 페이지 요청 처리
    @GetMapping("/board")
    public String board() {
        // board.html을 반환하여 게시판 페이지를 표시
        return "board";
    }

    // 게시물 목록 페이지 요청 처리
    // postlist 데이터가 존재하지 않으면?  목록리스트 데이터
    //[등록]
    //게시글 등록 성공 여부 확인
    @PostMapping("/insert")
    public String postpage(@ModelAttribute BoardDTO boardDTO, RedirectAttributes redirectAttributes) {
        // 비즈니스 로직을 수행
        // 게시글 등록을 처리하고 결과를 받음
        String result = boardService.createPost(boardDTO, redirectAttributes);

        // 게시글 등록 성공 여부에 따라 리다이렉션 처리
        if (result == null) {
            // 등록 실패 시 메시지 추가
            redirectAttributes.addAttribute("message", "게시글 등록에 실패하였습니다");
            return "redirect:/board"; // board 페이지로 리다이렉트
        } else {
            //등록이 되어 있으면 success 뜨고 postlist로 리다이렉트
            // 등록 성공 시 성공 메시지 추가
            redirectAttributes.addAttribute("success", "게시글 성공적으로 등록");
            return "redirect:/postlist";
        }
    }


    /* [전체 조회]*/
    @GetMapping("/postlist")
    public String postlist(Model model) {
        // 게시물 목록을 BoardService를 통해 가져옴
        List<Post> postlist = boardService.getAllPosts();

        // 게시물 목록이 비어있으면 noPost 속성을 true로 설정하여 처리
        if (postlist == null || postlist.size() < 0) {
            model.addAttribute("noPost", true);
        } else {
            //게시물 목록을 모델에 추가하여 postlist.html에 전달
            model.addAttribute("postlist", postlist);
        }
        // postlist.html을 반환하여 게시물 목록 페이지를 표시
        return "postlist";
    }

    //[상세조회]
    //postdetail로 상세페이지 보여지게 하기
    @GetMapping("/postlist/{id}")
    public String getPostDetail(@PathVariable("id") int id, Model model) {

        BoardDTO boardDTO = boardService.getPostById(id); // id에 해당하는 게시물을 BoardService를 통해 가져옴

        if (boardDTO == null) { // 게시물이 없으면 에러 메시지 추가
            model.addAttribute("error", "게시물을 찾을 수 없습니다");
//            return "error"; // error.html을 반환하여 에러 페이지를 표시
            return "";
        } else {
            model.addAttribute("post", boardDTO); // 게시물 정보를 모델에 추가하여 postdetail.html에 전달
            return "postdetail"; // postdetail.html을 반환하여 게시물 상세 페이지를 표시
        }
    }







    //[수정]
    // 게시글 수정 페이지 요청 처리
    @GetMapping("/postlist/editpost/{id}")
    public String editPost(@PathVariable("id") int id, Model model) {
        // id에 해당하는 게시물을 BoardService를 통해 가져옴
        BoardDTO edits = boardService.getPostById(id);
        // 존재하지 않는 게시글이면 게시판 목록으로 리다이렉트

        if (edits == null) {
            return "redirect:/postList";
        }
        model.addAttribute("boardDTO", edits); // boardDTO가 실제로는 Post 객체를 의미하는 것으로 가정한다.
        return "/editpost"; // editpost.html로 반환하여 게시글 수정페이지를 표시
    }



    //사용자가 수정 내용 입력하고 저장 버튼 누르면 경로 변수 id와 수정된 게시글 데이터가 함께 전달!!
    @PostMapping("/postlist/editpost/{id}")
    //id는 경로변수, 수정할 게시글의 ID를 나타냄
    //요청 전달된 데이터를 Post 객체로 바인딩하여 updatedPost 변수에 저장
    // 수정할 게시글(ID) 의 내용 넣기
    public String updatePost(@PathVariable("id") int id, @ModelAttribute BoardDTO boardDTO, RedirectAttributes redirectAttributes) {
        //  수정된 내용 게시물(id)을 업데이트

        BoardDTO modifyPost = boardService.updatePost(id, boardDTO);

        if (modifyPost == null) {
            redirectAttributes.addFlashAttribute("error", "게시글 수정 실패");
        } else {
            redirectAttributes.addFlashAttribute("success", "게시글 수정 완료");
        }

        // spring > 페이지의 이름으로 인식함.
        return "redirect:/postlist/"+modifyPost.getId();
    }


}
















