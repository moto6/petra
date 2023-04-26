package io.petra.attachfile.repository;

import io.petra.attachfile.domain.AttachFile;
import io.petra.article.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttachFileRepository extends JpaRepository<AttachFile, Long> {

    Optional<List<AttachFile>> findAllByArticle(Article article);
}
