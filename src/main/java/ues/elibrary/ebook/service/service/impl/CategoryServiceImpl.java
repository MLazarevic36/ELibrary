package ues.elibrary.ebook.service.service.impl;

import ues.elibrary.ebook.entity.Category;

import java.util.List;

public interface CategoryServiceImpl {

    List<Category> findAll();

    Category findById(Long id);

    Category save(Category category);

    void remove(Long id);

    void addSubscribe(Long userId, Long categoryId);

    Long checkSubscribe(Long userId, Long categoryId);

}
