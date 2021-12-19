package com.test.champion.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "group")
@ToString(onlyExplicitlyIncluded = true)
public class Group {
    @Id
    @Column(name = "id", nullable = false)
    @Getter
    @Setter
    private String id;

    @Column(name = "name", nullable = false)
    @Getter
    @Setter
    @ToString.Include
    private String name;
    @OneToMany(mappedBy = "group",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonManagedReference
    @Getter
    @Setter
    private List<Participant> participants;

    public Group() {
    }

    public Group(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group)) return false;
        Group group = (Group) o;
        return id.equals(group.id) && name.equals(group.name)
                && Objects.equals(participants, group.participants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, participants);
    }

    public static class Request {
        @Getter
        private String name;
    }

    public static class GroupParticipants {
        @Getter
        private String groupID;
        @Getter
        private List<String> participantIDs;
    }
}
