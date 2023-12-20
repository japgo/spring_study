package com.team10.backoffice.domain.admin.controller;

import com.team10.backoffice.domain.admin.service.AdminService;
import com.team10.backoffice.domain.users.dto.UserRequestDto;
import com.team10.backoffice.etc.response.ApiResponse;
import com.team10.backoffice.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users/{userId}") // 사용자 조회(비밀번호 포함)
    public ResponseEntity<ApiResponse<?>> getUser(@PathVariable long userId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        var userResponseDto = this.adminService.getUser(userId, userDetails.getUser());
        var userRole = userDetails.getUser().getRole();

        return ResponseEntity.ok(ApiResponse.ok(userResponseDto));
    }

    @PatchMapping("/{userId}") // 사용자 프로필 수정
    public ResponseEntity<ApiResponse<?>> updateUserProfile(@PathVariable long userId,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                            @Valid @RequestBody UserRequestDto userRequestDto) {
        adminService.updateUser(userId, userDetails.getUser(), userRequestDto);

        return ResponseEntity.ok(ApiResponse.ok("USER UPDATE SUCCESS"));
    }

    @DeleteMapping("/{userId}") // 사용자 삭제
    public ResponseEntity<ApiResponse<?>> deleteUser(@PathVariable Long userId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        adminService.removeUser(userId, userDetails.getUser());
        return ResponseEntity.ok(ApiResponse.ok("SUCCESS_DELETE_USER"));
    }

    @PatchMapping("/upgrade/{userId}") // 사용자 권한 승급
    public ResponseEntity<ApiResponse<?>> upgradeUserRole(@PathVariable long userId,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        adminService.upgradeUser(userId, userDetails.getUser());
        return ResponseEntity.ok(ApiResponse.ok("USER UPGRADE SUCCESS"));
    }

    @PatchMapping("/downgrade/{userId}") // 사용자 권한 강등
    public ResponseEntity<ApiResponse<?>> downgradeUserRole(@PathVariable long userId,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        adminService.downgradeUser(userId, userDetails.getUser());
        return ResponseEntity.ok(ApiResponse.ok("USER UPGRADE SUCCESS"));
    }

    @PatchMapping("/block/{userId}") // 사용자 차단
    public ResponseEntity<String> blockUser(@PathVariable long userId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        adminService.blockUser(userId, userDetails.getUser());
        return ResponseEntity.ok("USER BLOCKED SUCCESS");
    }
}
