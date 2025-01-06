package com.PickOne.domain.preference.model;

import com.PickOne.domain.preference.model.entity.Instrument;
import com.PickOne.domain.preference.model.entity.Preference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.Getter;

@Entity
@Getter
public class UserInstrument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userInstrumentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "preference_id", nullable = false)
    private Preference preference;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Instrument instrument; // Enum 사용


    private String proficiency;
    private LocalDate startedPlaying;
}
