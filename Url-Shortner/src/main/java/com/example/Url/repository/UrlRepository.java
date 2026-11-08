package com.example.Url.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Url.model.UrlEntity;

@Repository
public interface UrlRepository extends JpaRepository<UrlEntity,Long>{

}
