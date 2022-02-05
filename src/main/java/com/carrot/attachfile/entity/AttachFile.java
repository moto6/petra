package com.carrot.attachfile.entity;

import com.carrot.article.entity.Article;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "attach_file", indexes = {
        @Index(name = "", columnList = "post_id", unique = false)
})
@Getter
@Entity
@NoArgsConstructor
public class AttachFile {

    @Builder
    public AttachFile(String originalFileName, String verifiedFileName, Article article) {
        this.originalFileName = originalFileName;
        this.verifiedFileName = verifiedFileName;
        this.article = article;
    }

    @Id
    @GeneratedValue
    @Column(name = "attach_file_id")
    private Long id;

    @Column(name = "original_file_name")
    private String originalFileName;

    @Column(name = "verified_file_name")
    private String verifiedFileName;

    @ManyToOne(fetch = FetchType.LAZY) //N-1 단방향 관계로 맵핑
    @JoinColumn(name = "post_id")
    private Article article;

    public String getImgUrl() {
        return "not yet";
        //스프링설정에서 읽어온 url에서 스테틱 제공
    }

    public void updateAttachFile(String originalFileName, String verifiedFileName) {
        this.originalFileName = originalFileName;
        this.verifiedFileName = verifiedFileName;
    }
}
