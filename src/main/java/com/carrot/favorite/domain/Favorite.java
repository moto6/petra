package com.carrot.favorite.domain;

import com.carrot.account.domain.Account;
import com.carrot.article.domain.Article;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.carrot.common.entityaudit.BaseEntity.DATETIME_FORMAT;


@Entity
@Table(name = "favorite", indexes = {
        @Index(name = "favorite_article", columnList = "article_id"),
        @Index(name = "favorite_account", columnList = "account_id")
})
@NoArgsConstructor
@Getter
public class Favorite {
    @Id
    @GeneratedValue
    @Column(name = "favorite_id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    @DateTimeFormat(pattern = DATETIME_FORMAT)
    private LocalDateTime createAt;

    public Favorite(Article article, Account account) {
        this.article = article;
        this.account = account;
        this.createAt = LocalDateTime.now();
    }
}
