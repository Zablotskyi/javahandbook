package com.example.javahandbook.repository;

import com.example.javahandbook.entity.MenuGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuGroupRepository extends JpaRepository<MenuGroup, Long> {

    Optional<MenuGroup> findByCode(String code);

    List<MenuGroup> findAllByOrderBySortOrderAscTitleAsc();

    boolean existsByCode(String code);
}