package com.ohgiraffers.spring_project.controller;

import ch.qos.logback.core.joran.event.BodyEvent;
import com.ohgiraffers.spring_project.model.dto.BoardDTO;
import com.ohgiraffers.spring_project.model.entity.Post;
import com.ohgiraffers.spring_project.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.Date;


@Controller

public class BoardController {

    private final BoardService boardService;

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

    @PostMapping("/insert")
    public String postpage(@ModelAttribute BoardDTO boardDTO, Model model) {
        Post post = new Post();
        post.setCreateDate(new Date());
        post.setBoardContent(boardDTO.getBoardContent());
        post.setBoardTitle(boardDTO.getBoardTitle());


        boardService.createPost(boardDTO);
        model.addAttribute("post", post);

        return "/postpage";
    }
}