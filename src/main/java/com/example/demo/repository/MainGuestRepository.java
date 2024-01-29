package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.MainGuest;

@Repository
public interface MainGuestRepository extends JpaRepository<MainGuest, Long> {
}