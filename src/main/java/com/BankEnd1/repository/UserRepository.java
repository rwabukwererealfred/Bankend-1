package com.BankEnd1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BankEnd1.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

	
}
