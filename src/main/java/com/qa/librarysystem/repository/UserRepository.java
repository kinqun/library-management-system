package com.qa.librarysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.qa.librarysystem.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select u from User u where (u.username= :username and u.password= :pw)")
	public User findUserBylogin(String username, String pw);
	
	//@Query("select u from User u where u.user_email= :email")
	public User findUserByEmail(String email);
	
	//@Query("select u from User u where u.user_name= :username")
	public User findUserByUsername(String username);
	
}
