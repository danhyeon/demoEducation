package com.example.demo.repository;

import com.example.demo.entity.Board;
import com.example.demo.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByBoard(Board board);
}
