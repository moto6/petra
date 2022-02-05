package com.carrot.favorite.repository;

import com.carrot.account.entity.Account;
import com.carrot.comment.Article;
import com.carrot.favorite.entitiy.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Optional<Favorite> findByAccountAndArticle(Account account, Article article);

    Set<Favorite> findAllArticleIdByAccount(Account account);
}
