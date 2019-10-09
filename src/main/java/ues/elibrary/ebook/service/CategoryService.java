package ues.elibrary.ebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ues.elibrary.ebook.entity.Category;
import ues.elibrary.ebook.repository.CategoryRepository;
import ues.elibrary.ebook.service.service.impl.CategoryServiceImpl;

import java.util.List;

@Service
public class CategoryService implements CategoryServiceImpl {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.getOne(id);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void remove(Long id) {
        categoryRepository.deleteById(id);

    }

    @Override
    public void addSubscribe(Long userId, Long categoryId) {
        categoryRepository.addSubscribe(userId, categoryId);
    }

    @Override
    public Long checkSubscribe(Long userId, Long categoryId) {
        return categoryRepository.checkSubscribe(userId, categoryId);
    }
}
