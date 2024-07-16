package com.ohgiraffers.spring_project.model.entity;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "board_post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer boardNo;


    @Column(name = "board_title",unique = true, nullable = false)
    private String boardTitle;


    @Column(name = "board_content",nullable = false,length = 5000)
    private String boardContent;

    @Column(name = "board_createDate")
    private Date createDate;

    public Post() {
    }

    public Post(Integer boardNo, String boardTitle, String boardContent, Date createDate) {
        this.boardNo = boardNo;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.createDate = createDate;
    }

    public Integer getBoardNo() {
        return boardNo;
    }

    public void setBoardNo(Integer boardNo) {
        this.boardNo = boardNo;
    }

    public String getBoardTitle() {
        return boardTitle;
    }

    public void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }

    public String getBoardContent() {
        return boardContent;
    }

    public void setBoardContent(String boardContent) {
        this.boardContent = boardContent;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Post{" +
                "boardNo=" + boardNo +
                ", boardTitle='" + boardTitle + '\'' +
                ", boardContent='" + boardContent + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
