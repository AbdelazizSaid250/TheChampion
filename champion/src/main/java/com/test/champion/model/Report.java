package com.test.champion.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "report")
@ToString(onlyExplicitlyIncluded = true)
public class Report {
    @Id
    @Column(name = "id", nullable = false)
    @Getter
    @Setter
    private String id;

    @Column(name = "played_matches", nullable = false)
    @Getter
    @Setter
    @ToString.Include
    private int playedMatches;

    public Report() {
    }

    public Report(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Report)) return false;
        Report report = (Report) o;
        return playedMatches == report.playedMatches && id.equals(report.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, playedMatches);
    }
}
