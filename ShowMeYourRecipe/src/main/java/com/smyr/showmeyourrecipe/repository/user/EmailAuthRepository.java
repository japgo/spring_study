package com.smyr.showmeyourrecipe.repository.user;

import com.smyr.showmeyourrecipe.entity.user.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailAuthRepository extends JpaRepository< EmailAuth, String > {
}
