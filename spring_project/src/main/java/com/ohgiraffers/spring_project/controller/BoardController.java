package com.ohgiraffers.spring_project.controller;


import com.ohgiraffers.spring_project.model.dto.BoardDTO;
import com.ohgiraffers.spring_project.model.entity.Post;
import com.ohgiraffers.spring_project.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



import java.util.List;
import java.util.Optional;


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

    // postlist 데이터가 존재하지 않으면?
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




    @PostMapping("/insert")
    public String postpage(@ModelAttribute BoardDTO boardDTO, RedirectAttributes redirectAttributes) {
        // 비즈니스 로직을 수행
        String result = boardService.createPost(boardDTO, redirectAttributes);

        // 게시글 등록 성공 여부에 따라 리다이렉션 처리
        if (result == null) {
            // 게시글 등록 실패 시
            redirectAttributes.addFlashAttribute("message", "게시글 등록에 실패하였습니다");
            return "redirect:/board";
        } else {
            //등록이 되어 있으면 success 뜨고 postlist로 뜸
            redirectAttributes.addFlashAttribute("success", "게시글 성공적으로 등록");
            return "redirect:/postlist";
        }
    }


    // 수정
    @GetMapping("postlist/{id}")
    public String getpost(@PathVariable("id") Integer id, Model model) {

        Optional<Post> post = boardService.getPostById(id);
        // 굿
        if (post.isPresent()) {
            // 엔티티를 전달하면 안되는 이유?
            // 나이 이름, 주소, 전화번호, 비밀번호, 아이디, 주민번호

            model.addAttribute("post", post.get());
            return "postDetail"; // 검증이 필요함
        } else {
            // 페이지가 없는데?
            model.addAttribute("error", "Post not found!");
            return "error";
        }
    }

    // 수정
    @GetMapping("editpost/{id}")
    public String editpost(@PathVariable("id") Integer id, Model model) {
        //ID를 사용하여 게시글 정보를 조회
        Optional<Post> post = boardService.getPostById(id);
        // 게시글이 존재하는 경우
        // 서비스에서 처리해야됨
        if (post.isPresent()) {
            BoardDTO boardDTO = new BoardDTO();
            boardDTO.setId(post.get().getId()); // 게시글 ID 설정
            boardDTO.setBoardTitle(post.get().getBoardTitle()); //제목 설정
            boardDTO.setBoardContent(post.get().getBoardContent()); //내용 설정
            // 모델에 BoardDTO 객체를 추가하여 뷰에서 사용하도록 함
            model.addAttribute("boardDTO", boardDTO);
            return "editpost";
        } else {
            // 에러페이지 없음
            model.addAttribute("error", "Post not found");
            return "error";
        }
    }




    // 위의 사례를 보고 전체 수정
    @PostMapping("/editpost")
    public String updatepost(@ModelAttribute BoardDTO boardDTO){
       // BoardDTO 객체를 사용하여 게시글 정보를 업데이트
        boardService.updatePost(boardDTO);
        return "postdetail";
   }


}






