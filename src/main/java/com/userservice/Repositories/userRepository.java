package com.userservice.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.userservice.Entities.Users;

@Repository
public interface userRepository extends JpaRepository<Users, String>{

}
