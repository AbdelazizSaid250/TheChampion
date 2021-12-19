package com.test.champion.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "winner")
@ToString(onlyExplicitlyIncluded = true)
public class Winner {
    @Id
    @Column(name = "id", nullable = false)
    @Getter
    @Setter
    private String id;

    @Column(name = "participant_name", nullable = false)
    @Getter
    @Setter
    @ToString.Include
    private String participantName;

    @Column(name = "points", nullable = false)
    @Getter
    @Setter
    @ToString.Include
    private int points;
    @ManyToMany
    @JoinTable(
            name = "participant_winner",
            joinColumns = @JoinColumn(name = "winner_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    @Getter
    @Setter
    private List<Participant> participants;

    public Winner() {
    }

    public Winner(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Winner)) return false;
        Winner winner = (Winner) o;
        return points == winner.points && id.equals(winner.id)
                && participantName.equals(winner.participantName)
                && participants.equals(winner.participants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, participantName, points, participants);
    }
}
