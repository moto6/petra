package com.board.post.entity;

import com.board.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue
    Long id;

    String title;

    String contents;

    String author;

    /*
    LocalDateTime validFrom;

    LocalDateTime validUntil;

    public boolean isValidPeriod() {
        LocalDateTime standardTime = LocalDateTime.now();
        return validFrom.isAfter(standardTime) && validUntil.isBefore(standardTime);
    }
 */

}
