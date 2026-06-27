package com.flashcard.english_card.controller;

import com.flashcard.english_card.service.VocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vocabularies")
public class VocabularyController {
    @Autowired
    private VocabularyService vocabularyService;

    //ta từ vựng thuộc danh mục
    @PostMapping("/category/{categoryId}")
    

}
