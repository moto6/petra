package io.petra.comment.exception;

public class CommentDeleteUnauthorized extends RuntimeException {
    public CommentDeleteUnauthorized() {

        super("댓글 작성자만이 삭제할 수 있습니다");
    }
}
