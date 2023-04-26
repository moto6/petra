package io.petra.favorite.repository;

import io.petra.account.domain.Account;
import io.petra.favorite.domain.Favorite;
import io.petra.article.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Optional<Favorite> findByAccountAndArticle(Account account, Article article);

    Set<Favorite> findAllArticleIdByAccount(Account account);
}
