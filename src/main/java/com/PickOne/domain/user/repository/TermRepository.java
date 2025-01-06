package com.PickOne.domain.user.repository;

import com.PickOne.domain.user.model.Term;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermRepository extends JpaRepository<Term, Long> {
}
