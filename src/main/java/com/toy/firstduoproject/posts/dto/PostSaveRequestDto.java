package com.toy.firstduoproject.posts.dto;

import com.toy.firstduoproject.postType.entity.PostType;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
public class PostSaveRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;

//    private MultipartFile attachFile;
    private List<MultipartFile> attachFiles;

    private PostType postType;

//    public PostSaveRequestDto(String title, String content, MultipartFile attachFile, PostType postType) {
//        this.title = title;
//        this.content = content;
//        this.attachFile = attachFile;
//        this.postType = Objects.requireNonNullElse(postType, PostType.CHAT);
//    }

    public PostSaveRequestDto(String title, String content, List<MultipartFile> attachFiles, PostType postType) {
        this.title = title;
        this.content = content;
        this.attachFiles = attachFiles;
        this.postType = postType;
    }
}
