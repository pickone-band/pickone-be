package com.PickOne.domain.preference.model.entity;

import com.PickOne.domain.preference.model.UserArtist;
import com.PickOne.domain.preference.model.UserGenre;
import com.PickOne.domain.preference.model.UserInstrument;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Preference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long preferenceId;

    // Preference와 UserInstrument의 1:N 관계
    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "preference", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserInstrument> userInstruments = new ArrayList<>();

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "preference", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserGenre> userGenre = new ArrayList<>();

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "preference", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserArtist> userArtist = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Mbti mbti;   // 사용자 MBTI

    private String selfDescription; //자기 소개

    private String region; //선호 활동 지역

    // 대학교 정보
    private String university; // 대학교 이름
    private String major;      // 전공


}
