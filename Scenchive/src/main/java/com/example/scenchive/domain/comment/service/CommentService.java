package com.example.scenchive.domain.comment.service;

import com.example.scenchive.domain.board.repository.Board;
import com.example.scenchive.domain.board.repository.BoardRepository;
import com.example.scenchive.domain.comment.dto.CommentDto;
import com.example.scenchive.domain.comment.repository.Comment;
import com.example.scenchive.domain.comment.repository.CommentRepository;
import com.example.scenchive.domain.member.repository.Member;
import com.example.scenchive.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public CommentService(CommentRepository commentRepository, MemberRepository memberRepository, BoardRepository boardRepository) {
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
    }

    // 댓글 생성
    @Transactional
    public CommentDto createComment(Long boardId, Long memberId, String content) {
        Board board = findBoard(boardId);
        Member member = findMember(memberId);

        Comment comment = Comment.builder()
                .member(member)
                .board(board)
                .content(content)
                .build();

        commentRepository.save(comment);
        return mapToDto(comment);
    }

   // 대댓글 생성
    @Transactional
    public CommentDto createReply(Long boardId, Long memberId, Long parentId, String content) {
        Board board = findBoard(boardId);
        Member member = findMember(memberId);
        Comment parentComment = findComment(parentId);

        Comment reply = Comment.builder()
                .member(member)
                .board(board)
                .content(content)
                .parentComment(parentComment)
                .build();

        commentRepository.save(reply);
        return mapToDto(reply);
    }

    // 댓글 조회
    public CommentDto getComment(Long commentId) {
        Comment comment = findComment(commentId);
        return mapToDto(comment);
    }

    // 게시글에 속한 댓글 목록 조회
    public List<CommentDto> getCommentsByBoard(Long boardId) {
        Board board = findBoard(boardId);
        return board.getComments().stream()
//                .filter(comment -> !comment.isDeleted())
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = findComment(commentId);
        comment.delete();
    }

    private Comment findComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
    }

    private Board findBoard(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }

    private CommentDto mapToDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setMemberName(comment.getMember().getName());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreated_at().toString());
        dto.setDeleted(comment.isDeleted());
        if (comment.getParentComment() != null) {
            dto.setParentId(comment.getParentComment().getId());
        }
        return dto;
    }
}