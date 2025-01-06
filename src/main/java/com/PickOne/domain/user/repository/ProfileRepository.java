package com.PickOne.domain.user.repository;

import com.PickOne.domain.user.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
