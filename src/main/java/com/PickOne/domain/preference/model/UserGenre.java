package com.PickOne.domain.preference.model;

import com.PickOne.domain.preference.model.entity.Genre;
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
import lombok.Getter;

@Entity
@Getter
public class UserGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userGenreId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "preference_id", nullable = false)
    private Preference preference;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genre genre;
}
