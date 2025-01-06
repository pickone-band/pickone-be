package com.PickOne.domain.user.repository;

import com.PickOne.domain.user.model.SocialAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialAccountRepository extends JpaRepository<SocialAccount, Long> {
}
