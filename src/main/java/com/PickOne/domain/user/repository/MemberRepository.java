package com.PickOne.domain.user.repository;

import com.PickOne.domain.user.model.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByLoginId(String loginId);
    Optional<Member> findByMemberId(Long memberId);

}