package com.board.attachfile;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "attach_file")
@Getter
@Entity
@NoArgsConstructor
public class attachFile {

    @Id
    @GeneratedValue
    @Column(name = "attach_file_id")
    private Long id;

    private String originalFileName;

    private String

}
