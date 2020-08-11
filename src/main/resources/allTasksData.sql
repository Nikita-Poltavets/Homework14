create table locations
(
    id serial
        constraint locations_pk
            primary key,
    name varchar(255) not null
);

create unique index locations_name_uindex
    on locations (name);

INSERT INTO locations VALUES (1, 'gdansk'),
                             (2, 'bydgoszcz'),
                             (3, 'torun'),
                             (4, 'warszawa');



create table routes
(
    id serial
        constraint routes_pk
            primary key,
    from_id int not null
        constraint routes_locations_from_id_fk
            references locations,
    to_id int not null
        constraint routes_locations_to_id_fk
            references locations,
    cost int not null
);

INSERT INTO routes VALUES (1, 1, 2, 1),
                          (2, 1, 3, 3),
                          (3, 2, 1, 1),
                          (4, 2, 3, 1),
                          (5, 2, 4, 4),
                          (6, 3, 1, 3),
                          (7, 3, 2, 1),
                          (8, 3, 4, 1),
                          (9, 4, 2, 4),
                          (10, 4, 3, 1);


create table problems
(
    id serial
        constraint problems_pk
            primary key,
    from_id int not null
        constraint problems_locations_from_id_fk
            references locations,
    to_id int not null
        constraint problems_locations_to_id_fk
            references locations
);



INSERT INTO problems VALUES (1, 1, 4),
                            (2, 2, 4);


create table solutions
(
    problem_id int
        constraint solutions_pk
            primary key
        constraint solutions_problems_id_fk
            references problems,
    cost int
);