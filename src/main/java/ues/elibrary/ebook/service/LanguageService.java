package ues.elibrary.ebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ues.elibrary.ebook.entity.Language;
import ues.elibrary.ebook.repository.LanguageRepository;
import ues.elibrary.ebook.service.service.impl.LanguageServiceImpl;

import java.util.List;

@Service
public class LanguageService implements LanguageServiceImpl {

    @Autowired
    private LanguageRepository languageRepository;

    @Override
    public List<Language> findAll() {
        return languageRepository.findAll();
    }
}
