package com.board.attachfile.repository;

import com.board.attachfile.entity.AttachFile;
import com.board.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttachFileRepository extends JpaRepository<AttachFile, Long> {
    Optional<List<AttachFile>> findAllByPost(Post post);
}
