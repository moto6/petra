package com.carrot.comment.entity;

import com.carrot.account.entity.Account;
import com.carrot.common.TimeEntity;
import com.carrot.article.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Table(name = "table_comment")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
public class Comment extends TimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_article"))
    private Post article;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_account"))
    private Account account;

    @NotBlank(message = "공백이 허용되지 않습니다")
    @Column(name = "contents", length = 200)
    private String contents;

    public void setRelation(Post article, Account account) {
        this.article = article;
        this.account = account;
    }
}
