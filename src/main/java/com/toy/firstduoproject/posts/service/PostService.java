package com.toy.firstduoproject.posts.service;

import com.toy.firstduoproject.image.entity.Image;
import com.toy.firstduoproject.member.entity.Member;
import com.toy.firstduoproject.postType.entity.PostType;
import com.toy.firstduoproject.image.repository.ImageRepository;
import com.toy.firstduoproject.posts.dto.PostSaveRequestDto;
import com.toy.firstduoproject.posts.dto.PostUpdateRequestDto;
import com.toy.firstduoproject.posts.entity.Posts;
import com.toy.firstduoproject.posts.repository.PostRepository;
import com.toy.firstduoproject.utils.uploadFile.FileStore;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final FileStore fileStore;

    //C
    public Posts createPost(PostSaveRequestDto requestDto, Member member) throws IOException {
//        String storeFilename = fileStore.storeFile(requestDto.getAttachFile());

        Posts post = Posts.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .member(member)
                .postType(requestDto.getPostType())
                .build();

        List<MultipartFile> attachFiles = requestDto.getAttachFiles();
        Posts savedPost = postRepository.save(post);

        for(MultipartFile file : attachFiles) {
            String storeFilename = fileStore.storeFile(file);
            Image image = Image.builder()
                    .storeFilename(storeFilename)
                    .posts(savedPost)
                    .build();
            imageRepository.save(image);
        }

        return savedPost;
    }

    //R
    public Posts findPostById(Long postId){
        return postRepository.findById(postId).orElseThrow(()->new IllegalArgumentException("?????? ???????????? ????????????."));
    }

    //Rs
    public Page<Posts> findAll(Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, pageable.getPageSize(), Sort.Direction.DESC, "id"); // <- Sort ??????
        return postRepository.findAll(pageable);
    }

    /**
     *
     * @return ????????? ????????? Post ??? List
     */
    public List<Posts> findNotice(){
        return postRepository.findAllByPostType(PostType.NOTICE);
    }

    //U
    public void updatePost(Long postId, PostUpdateRequestDto requestDto){
        Posts post = postRepository.findById(postId).orElseThrow();

        String title = requestDto.getTitle();
        String content = requestDto.getContent();

        post.update(title,content);
        post.changePostType(requestDto.getPostType());
        postRepository.save(post);
        //return post;
    }

    //D
    public void deletePost(Long postId){
        postRepository.deleteById(postId);
    }
}
