package com.ohgiraffers.spring_project.controller;


import com.ohgiraffers.spring_project.model.dto.BoardDTO;
import com.ohgiraffers.spring_project.model.entity.Post;
import com.ohgiraffers.spring_project.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



import java.util.List;





@Controller
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/")
    public String mainpage() {
        return "mainpage";
    }

    @GetMapping("/board")
    public String board() {
        return "board";
    }

    // postlist 데이터가 존재하지 않으면?  목록리스트 데이터
    @GetMapping("/postlist")
    public String postlist(Model model) {
        List<Post> postlist = boardService.getAllPosts();

        if (postlist == null || postlist.size() < 0) {
            model.addAttribute("noPost", true);
        } else {
            // 게시물 목록을 모델에 추가
            model.addAttribute("postlist", postlist);
        }
        return "postlist";
    }


    //게시글 등록 성공 여부 확인
    @PostMapping("/insert")
    public String postpage(@ModelAttribute BoardDTO boardDTO, RedirectAttributes redirectAttributes) {
        // 비즈니스 로직을 수행
        String result = boardService.createPost(boardDTO, redirectAttributes);

        // 게시글 등록 성공 여부에 따라 리다이렉션 처리
        if (result == null) {
            // 게시글 등록 실패 시
            redirectAttributes.addAttribute("message", "게시글 등록에 실패하였습니다");
            return "redirect:/board";
        } else {
            //등록이 되어 있으면 success 뜨고 postlist로 뜸
            redirectAttributes.addAttribute("success", "게시글 성공적으로 등록");
            return "redirect:/postlist";
        }
    }

    //postdetail로 상세페이지 보여지게 하긔

    @GetMapping("/postdetail")
    public String postdetail(@PathVariable("id") int id, Model model) {
        Post post = boardService.getPostById(id);
        if (post.isPresent()) {
            model.addAttribute("post", post);
            return "postdetail";
        } else {
            model.addAttribute("error", "not found");
            return "error";
        }
    }
//    private BoardDTO boardPage;
//    // 게시물페이지가 null이 아닌경우 블로그 제목과 내용을 추가
//    @GetMapping("postdetail")
//    public String postdetail2(Model model){
//
//        if(boardPage != null){
//            model.addAttribute("boardTitle", boardPage.getBoardTitle());
//            model.addAttribute("boardContent", boardPage.getBoardContent());
//        }
//        return "editpost";
//    }
    //게시글 제목이나 내용이 없다면??
    // 없다면 에러메시지 발생
    @GetMapping("/postdetail/{id}")
    public ModelAndView getPostById(@PathVariable("id") int id , ModelAndView mv){
        BoardDTO post =boardService.find(id);
        if(post.getBoardTitle() == null || post.getBoardTitle().isEmpty()){
            mv.setViewName("redirect:/postlist");
            return mv;
        }
        if(post.getBoardContent() == null || post.getBoardContent().isEmpty()) {
            mv.setViewName("redirect:/postlist");
            return mv;
        }else{
            mv.addObject("message","완료");
        }
        return mv;

    }



    @PostMapping
    public ModelAndView getPostById(BoardDTO boardDTO , ModelAndView mv) {
        //게시물이 등록됬을때 제목이나 내용이 없다면
        //if 문
        // 경고메시지
        if (boardDTO.getBoardTitle() == null || boardDTO.getBoardTitle().isEmpty() ||
            boardDTO.getBoardContent() == null || boardDTO.getBoardContent().isEmpty()) {
            mv.setViewName("postdetail");
            mv.addObject("error","게시글 제목 또는 내용이 없습니다");
        }else{
            mv.setViewName("postdetail");
            mv.addObject("success","게시글 등록 성공");
        }
        return mv;


    }


}











