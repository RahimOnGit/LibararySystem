package com.example.libararysystem.service;

import com.example.libararysystem.entity.User;
import com.example.libararysystem.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LoginAttemptService {
private final UserRepo userRepo;
private final int max_attempts =5;
private final int lock_time_minutes = 5;

public void loginFailed(User user) {
    user.setFailedAttempts(user.getFailedAttempts() + 1);
    if(user.getFailedAttempts() >= max_attempts) {
        user.setLockedUntil(LocalDateTime.now().plusMinutes(lock_time_minutes));
    }
    userRepo.save(user);
}

public void loginSucceeded(User user) {
    user.setFailedAttempts(0);
    user.setLockedUntil(null);
    userRepo.save(user);

}

public boolean isLocked(User user) {
    return user.getLockedUntil()!= null && user.getLockedUntil().isAfter(LocalDateTime.now());
}


    public void checkLock(User user) {
        if (user.getLockedUntil() != null && user.getLockedUntil().isAfter(LocalDateTime.now())) {
            throw new LockedException("Account locked until " + user.getLockedUntil().toString());
        }
    }
}
