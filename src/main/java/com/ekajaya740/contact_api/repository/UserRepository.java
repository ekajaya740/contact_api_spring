package com.ekajaya740.contact_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ekajaya740.contact_api.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
