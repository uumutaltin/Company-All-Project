package com.works.repositories;

import com.works.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Integer> {

    Optional<Users> findByMailEqualsIgnoreCaseAllIgnoreCase(String useremail);



}
