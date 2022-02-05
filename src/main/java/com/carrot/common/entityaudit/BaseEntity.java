package com.carrot.common.entityaudit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.time.LocalDateTime;


@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @JsonIgnore
    @Transient
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    @DateTimeFormat(pattern = DATETIME_FORMAT)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    @DateTimeFormat(pattern = DATETIME_FORMAT)
    private LocalDateTime modifiedAt;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "modified_by")
    private String modifiedBy;
}
