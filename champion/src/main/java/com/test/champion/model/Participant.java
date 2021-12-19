package com.test.champion.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "participant")
@ToString(onlyExplicitlyIncluded = true)
public class Participant {
    @Id
    @Column(name = "id", nullable = false)
    @Getter
    @Setter
    private String id;

    @Column(name = "group_id")
    @Getter
    @Setter
    private String groupID;

    @Column(name = "name", nullable = false)
    @Getter
    @Setter
    @ToString.Include
    private String name;

    @Column(name = "gender", nullable = false)
    @Getter
    @Setter
    @ToString.Include
    private String gender;

    @Column(name = "played_matches", nullable = false)
    @Getter
    @Setter
    private int playedMatches;

    @Column(name = "win_matches", nullable = false)
    @Getter
    @Setter
    private int winMatches;

    @Column(name = "lose_matches", nullable = false)
    @Getter
    @Setter
    private int loseMatches;

    @Column(name = "points", nullable = false)
    @Getter
    @Setter
    private int points;


    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "group_id", insertable = false, updatable = false)
    @JsonBackReference
    @Getter
    @Setter
    private Group group;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "participant_winner",
            joinColumns = @JoinColumn(name = "participant_id"),
            inverseJoinColumns = @JoinColumn(name = "winner_id")
    )
    @Getter
    @Setter
    private List<Winner> winners;


    public Participant() {
    }

    public Participant(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Participant)) return false;
        Participant that = (Participant) o;
        return playedMatches == that.playedMatches && winMatches == that.winMatches
                && loseMatches == that.loseMatches && points == that.points
                && id.equals(that.id) && Objects.equals(groupID, that.groupID)
                && name.equals(that.name) && gender.equals(that.gender)
                && Objects.equals(group, that.group)
                && Objects.equals(winners, that.winners);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, groupID, name, gender, playedMatches, winMatches, loseMatches, points, group, winners);
    }

    public static class Request {
        @Getter
        private String name;
        @Getter
        private String gender;
    }
}
