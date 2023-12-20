package com.team10.backoffice.domain.post.service;

import com.team10.backoffice.domain.post.dto.FileDetail;
import com.team10.backoffice.domain.post.dto.PostRequestDto;
import com.team10.backoffice.domain.post.dto.PostResponseDto;
import com.team10.backoffice.domain.post.entity.Post;
import com.team10.backoffice.domain.post.repository.PostQueryRepository;
import com.team10.backoffice.domain.post.repository.PostRepository;
import com.team10.backoffice.domain.users.entity.User;
import com.team10.backoffice.domain.users.entity.UserRoleEnum;
import com.team10.backoffice.domain.users.repository.UserRepository;
import com.team10.backoffice.etc.amazon.AmazonS3ResourceStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostQueryRepository postQueryRepository;
    private final UserRepository userRepository;

    private final AmazonS3ResourceStorage amazonS3ResourceStorage;

    public void addPost(PostRequestDto postRequestDTO, User user) {
        Post post = new Post(postRequestDTO, user);
        postRepository.save(post);
    }

    @Transactional
    public void updatePost(Long id, PostRequestDto postRequestDto, User user) {

        Post post = postRepository.findById(id).orElseThrow( // 게시글 존재 여부 검증
                () -> new NullPointerException("게시글이 존재하지 않습니다.")
        );

        if( user.getRole() != UserRoleEnum.ADMIN ) {
            if( !post.getUser().getUsername().equals(user.getUsername()))
            { // 사용자 검증
                throw new IllegalArgumentException("게시글을 작성한 사용자가 아닙니다.");
            }
        }

        post.update(postRequestDto);
    }

    public void removePost(Long id, User user) {

        Post post = postRepository.findById(id).orElseThrow( // 게시글 존재 여부 검증
                () -> new NullPointerException("게시글이 존재하지 않습니다.")
        );

        if( user.getRole() != UserRoleEnum.ADMIN ) {
            if(!post.getUser().getUsername().equals(user.getUsername()))
            { // 사용자 검증
                throw new IllegalArgumentException("게시글을 작성한 사용자가 아닙니다.");
            }
        }

        postRepository.delete(post);
    }

    public List<PostResponseDto> getPosts() {
        return postRepository.findAll().stream().map(PostResponseDto::new).toList();
    }

    public List< PostResponseDto > getPostsOrderByContentLengthDesc() {
        return postQueryRepository.findOrderByContentLengthDesc().stream().map( PostResponseDto::new ).toList();
    }

    public List< PostResponseDto > getPostsByPage( Pageable pageable ) {
        return postQueryRepository.search( 0, pageable ).stream().map( PostResponseDto::new ).toList();
    }


    public List<PostResponseDto> getMyPosts(User user) {
        User dbuser = userRepository.findById(user.getId()).orElseThrow(()
                -> new IllegalArgumentException("유저 정보를 찾을 수 없습니다.")
        );

        return postRepository.findAllByUser(dbuser).stream().map(PostResponseDto::new).toList();
    }

    public PostResponseDto findPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NullPointerException("존재하지 않는 게시물입니다."));
	    return new PostResponseDto(post);
    }

    public FileDetail uploadFile( MultipartFile multipartFile ) {
        FileDetail fileDetail = FileDetail.multipartOf(multipartFile);
        amazonS3ResourceStorage.store(fileDetail.getPath(), multipartFile);
        return fileDetail;
    }
}
