package com.jari.jari.favorite.repository;

import com.jari.jari.account.entity.Account;
import com.jari.jari.article.entity.Article;
import com.jari.jari.favorite.entitiy.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Optional<Favorite> findByAccountAndArticle(Account account, Article article);

    Set<Favorite> findAllArticleIdByAccount(Account account);
}
