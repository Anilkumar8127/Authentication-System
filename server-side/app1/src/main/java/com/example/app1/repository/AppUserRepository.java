package com.example.app1.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.app1.entity.AppUser;

public interface AppUserRepository extends CrudRepository<AppUser, String>
{

	public AppUser findByTokenAndEmail(String token, String email);

}
