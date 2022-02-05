package com.carrot.attachfile.repository;

import com.carrot.attachfile.domain.AttachFile;
import com.carrot.article.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttachFileRepository extends JpaRepository<AttachFile, Long> {

    Optional<List<AttachFile>> findAllByArticle(Article article);
}
