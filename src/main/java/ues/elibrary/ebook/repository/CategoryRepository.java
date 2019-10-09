package ues.elibrary.ebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ues.elibrary.ebook.entity.Category;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Modifying
    @Query(value = "INSERT INTO subscribes VALUES(:userId, :categoryId)", nativeQuery = true)
    void addSubscribe(Long userId, Long categoryId);

    @Query(value = "SELECT category_id as categoryId FROM subscribes WHERE user_id = :userId AND category_id = :categoryId OR "
            + "user_id = :userId AND category_id = 0", nativeQuery = true)

    Long checkSubscribe(Long userId, Long categoryId);
}
