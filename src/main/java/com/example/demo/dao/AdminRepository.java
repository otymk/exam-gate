package com.example.demo.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, String> {
	Optional<Admin> findByAdminIdAndPass(
			String adminId, String pass);
}
