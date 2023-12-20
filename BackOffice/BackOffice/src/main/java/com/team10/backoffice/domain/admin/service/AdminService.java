package com.team10.backoffice.domain.admin.service;

import com.team10.backoffice.domain.post.repository.PostRepository;
import com.team10.backoffice.domain.admin.dto.AdminResponseDto;
import com.team10.backoffice.domain.users.dto.UserRequestDto;
import com.team10.backoffice.domain.users.entity.User;
import com.team10.backoffice.domain.users.entity.UserRoleEnum;
import com.team10.backoffice.domain.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;


    public AdminResponseDto getUser(long userId, User user) {
        user = checkUser(userId, user);
        AdminResponseDto adminResponseDto = new AdminResponseDto();
        adminResponseDto.setUsername(user.getUsername());
        adminResponseDto.setNickname(user.getNickName());
        adminResponseDto.setPassword(user.getPassword());
        adminResponseDto.setEmail(user.getEmail());
        adminResponseDto.setIntroduction(user.getIntroduction());

        return adminResponseDto;
    }

    @Transactional
    public void updateUser(long userId, User user, UserRequestDto userRequestDto) {
        user = checkUser(userId, user);
        user.setNickName(userRequestDto.getNickname());
        user.setPassword(userRequestDto.getPassword());
        user.setIntroduction(userRequestDto.getIntroduction());
        user.setEmail(userRequestDto.getEmail());
    }

    public void removeUser(Long userId, User user) {
        user = checkUser(userId, user);
        userRepository.delete(user);
    }

    public void upgradeUser(long userId, User user) {
        user = checkUser(userId, user);
        if (user.getRole() == UserRoleEnum.ADMIN) {
            throw new IllegalArgumentException("이미 관리자 권한을 가진 유저입니다");
        }
        user.setRole(UserRoleEnum.ADMIN);
        userRepository.save(user);
    }

    public void downgradeUser(long userId, User user) {
        user = checkUser(userId, user);
        if (user.getRole() == UserRoleEnum.USER) {
            throw new IllegalArgumentException("이미 관리자 권한이 없는 유저입니다");
        }
        user.setRole(UserRoleEnum.USER);
        userRepository.save(user);
    }

    public void blockUser(Long userId, User user) {
        user = checkUser(userId, user);

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            user = userOptional.get();
            user.setBlocked(true);
            userRepository.save(user);
        }
    }

    public User checkUser(long userId, User user) {
        if (user.getRole() != UserRoleEnum.ADMIN) {
            throw new IllegalArgumentException("권한이 없습니다");
        }
        user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));
        return user;
    }
}
