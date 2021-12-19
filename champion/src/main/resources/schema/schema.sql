create table `group`
(
    id    varchar(255) not null
        primary key,
    name  text         not null,
    round int          null
);

create table participant
(
    id             varchar(255) not null
        primary key,
    group_id       varchar(255) null,
    name           text         not null,
    gender         text         not null,
    played_matches int          null,
    win_matches    int          null,
    lose_matches   int          null,
    points         int          null,
    constraint participant_group_id_fk
        foreign key (group_id) references `group` (id)
            on update cascade
);

create table report
(
    id             varchar(255) not null
        primary key,
    played_matches int          null
);

create table winner
(
    id               varchar(255) not null
        primary key,
    participant_name text         not null,
    points           int          not null
);

create table participant_winner
(
    id             varchar(255) not null,
    participant_id varchar(255) null,
    winner_id      varchar(255) null,
    constraint winner_participant_participant_id_fk
        foreign key (participant_id) references participant (id)
            on update cascade,
    constraint winner_participant_winner_id_fk
        foreign key (winner_id) references winner (id)
            on update cascade
);

