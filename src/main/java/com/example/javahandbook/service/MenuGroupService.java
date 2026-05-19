package com.example.javahandbook.service;

import com.example.javahandbook.entity.MenuGroup;
import com.example.javahandbook.repository.ArticleRepository;
import com.example.javahandbook.repository.MenuGroupRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuGroupService {

    private final MenuGroupRepository menuGroupRepository;
    private final ArticleRepository articleRepository;

    public MenuGroupService(MenuGroupRepository menuGroupRepository,
                            ArticleRepository articleRepository) {
        this.menuGroupRepository = menuGroupRepository;
        this.articleRepository = articleRepository;
    }

    @PostConstruct
    public void initDefaultGroups() {
        if (menuGroupRepository.count() > 0) {
            return;
        }

        menuGroupRepository.save(new MenuGroup("java", "Java Core", 1));
        menuGroupRepository.save(new MenuGroup("oop", "OOP", 2));
        menuGroupRepository.save(new MenuGroup("collections", "Collections", 3));
        menuGroupRepository.save(new MenuGroup("spring", "Spring", 4));
    }

    public List<MenuGroup> findAll() {
        return menuGroupRepository.findAllByOrderBySortOrderAscTitleAsc();
    }

    public MenuGroup findByCode(String code) {
        return menuGroupRepository.findByCode(code).orElse(null);
    }

    public String getTitleByCode(String code) {
        return menuGroupRepository.findByCode(code)
                .map(MenuGroup::getTitle)
                .orElse(code);
    }

    public void addMenuGroup(String code, String title, Integer sortOrder) {
        MenuGroup menuGroup = new MenuGroup(
                normalizeCode(code),
                title,
                sortOrder
        );

        menuGroupRepository.save(menuGroup);
    }

    @Transactional
    public void updateMenuGroup(String originalCode, String newCode, String title, Integer sortOrder) {
        String normalizedOriginalCode = normalizeCode(originalCode);
        String normalizedNewCode = normalizeCode(newCode);

        MenuGroup menuGroup = menuGroupRepository.findByCode(normalizedOriginalCode)
                .orElse(null);

        if (menuGroup == null) {
            return;
        }

        if (!normalizedOriginalCode.equals(normalizedNewCode)) {
            articleRepository.updateMenuGroupCode(normalizedOriginalCode, normalizedNewCode);
        }

        menuGroup.setCode(normalizedNewCode);
        menuGroup.setTitle(title);
        menuGroup.setSortOrder(sortOrder);

        menuGroupRepository.save(menuGroup);
    }

    @Transactional
    public boolean deleteMenuGroupIfEmpty(String code) {
        String normalizedCode = normalizeCode(code);

        long articlesCount = articleRepository.countByMenuGroup(normalizedCode);

        if (articlesCount > 0) {
            return false;
        }

        menuGroupRepository.findByCode(normalizedCode)
                .ifPresent(menuGroupRepository::delete);

        return true;
    }

    private String normalizeCode(String code) {
        return code
                .trim()
                .toLowerCase()
                .replace(" ", "-");
    }
}