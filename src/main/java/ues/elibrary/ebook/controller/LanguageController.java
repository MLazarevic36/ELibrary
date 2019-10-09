package ues.elibrary.ebook.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ues.elibrary.ebook.dto.LanguageDTO;
import ues.elibrary.ebook.entity.Language;
import ues.elibrary.ebook.service.LanguageService;



@RestController
@RequestMapping(value = "/api/language")
public class LanguageController {

    @Autowired
    private LanguageService languageService;

    @GetMapping()
    public ResponseEntity<List<LanguageDTO>> getAll() {
         List<LanguageDTO> dtos = new ArrayList<>();
         List<Language> languages = languageService.findAll();
         for (Language language : languages) {
             dtos.add(new LanguageDTO(language));
         }

         return new ResponseEntity<List<LanguageDTO>>(dtos, HttpStatus.OK);
    }


}