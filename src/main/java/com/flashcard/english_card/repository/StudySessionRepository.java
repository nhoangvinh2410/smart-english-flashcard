package com.flashcard.english_card.repository;

import com.flashcard.english_card.entity.StudySession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudySessionRepository extends JpaRepository<StudySession, Long> {
    //lấy tất cả các phiên học của user
    //(để tổng thời gian học, số thẻ đã thuộc,..)
    List<StudySession> findByUserId(Long userId);
}
