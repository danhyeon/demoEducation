package com.example.demo.repository;

import com.example.demo.entity.DupReply;
import com.example.demo.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DupReplyRepository extends JpaRepository<DupReply, Long> {
    List<DupReply> findByReply(Reply reply);
}
