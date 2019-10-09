package ues.elibrary.ebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ues.elibrary.ebook.entity.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
}
