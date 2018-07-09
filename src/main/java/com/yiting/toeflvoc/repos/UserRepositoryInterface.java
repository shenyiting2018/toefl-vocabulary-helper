package com.yiting.toeflvoc.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yiting.toeflvoc.models.User;

public interface UserRepositoryInterface extends JpaRepository<User, Long> {

}
