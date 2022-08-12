package com.smk.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smk.blog.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
