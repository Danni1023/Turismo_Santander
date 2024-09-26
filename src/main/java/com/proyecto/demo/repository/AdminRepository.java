package com.proyecto.demo.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.proyecto.demo.models.Admin;

public interface AdminRepository extends MongoRepository<Admin, String> {

	Optional<Admin> findByUserAndPassword(String user, String password);

	Admin findByDocumento(int documento);

}