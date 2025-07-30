package com.thd.user.repository;

import com.thd.base.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserServiceRepository extends JpaRepository<User, UUID> {
}
