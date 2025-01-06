package com.PickOne.domain.user.repository;

import com.PickOne.domain.user.model.MemberTerm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberTermRepository extends JpaRepository<MemberTerm, Long> {
}
