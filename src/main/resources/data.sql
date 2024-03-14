USE cinema;

INSERT INTO cinema (name, reservation_fee, group_discount, movie_base_price, image_url)
VALUES ('Cinema Royale', 12, 0.15, 140, "https://images.rawpixel.com/image_800/cHJpdmF0ZS9sci9pbWFnZXMvd2Vic2l0ZS8yMDIyLTA1L25zODIzMC1pbWFnZS5qcGc.jpg"),
       ('Movie Magic', 10, 0.1, 140, "https://images.rawpixel.com/image_800/cHJpdmF0ZS9sci9pbWFnZXMvd2Vic2l0ZS8yMDIyLTA1L25zODIzMC1pbWFnZS5qcGc.jpg"),
       ('Star Cinema', 15, 0.2, 150, "https://images.rawpixel.com/image_800/cHJpdmF0ZS9sci9pbWFnZXMvd2Vic2l0ZS8yMDIyLTA1L25zODIzMC1pbWFnZS5qcGc.jpg"),
       ('The Gentalman', 20, 0.2, 130, "https://images.rawpixel.com/image_800/cHJpdmF0ZS9sci9pbWFnZXMvd2Vic2l0ZS8yMDIyLTA1L25zODIzMC1pbWFnZS5qcGc.jpg");

INSERT INTO movie (title, run_time, is_classic, genre, pg13, image_url)
VALUES ('The Matrix', 136, FALSE, 'Sci-Fi', TRUE, "https://i.etsystatic.com/20217829/r/il/16b095/2467136209/il_fullxfull.2467136209_lutd.jpg"),
       ('The Shawshank Redemption', 142, TRUE, 'Drama', FALSE, "https://i.ytimg.com/vi/fLAL_d3DqO0/maxresdefault.jpg?sqp=-oaymwEmCIAKENAF8quKqQMa8AEB-AH-CYAC0AWKAgwIABABGGUgWChHMA8=&rs=AOn4CLCs8Wc7ANaIG44oFZ_a30tW1eqs1Q"),
       ('Inception', 148, FALSE, 'Sci-Fi', TRUE, " https://resizing.flixster.com/-XZAfHZM39UwaGJIFWKAE8fS0ak=/v3/t/assets/p7825626_p_v8_af.jpg"),
       ('Forrest Gump', 142, TRUE, 'Drama', TRUE, "https://i5.walmartimages.com/seo/Running-Forrest-Gump-Costume_16263de0-e423-4c2b-8514-7ee4989c8d44_1.f00d571b0cd0b44c98d45dd9dc200ee9.jpeg?odnHeight=768&odnWidth=768&odnBg=FFFFFF"),
       ('The Dark Knight', 152, FALSE, 'Action', TRUE, "https://m.media-amazon.com/images/M/MV5BMTk4ODQzNDY3Ml5BMl5BanBnXkFtZTcwODA0NTM4Nw@@._V1_FMjpg_UX1000_.jpg"),
       ('Pulp Fiction', 154, TRUE, 'Crime, Drama', TRUE, "https://image.tmdb.org/t/p/original/A3NXS5e7Zc3FRyi63b7TdPGjFwI.jpg"),
       ('The Godfather', 175, TRUE, 'Crime, Drama', TRUE, "https://image.tmdb.org/t/p/original/3Tf8vXykYhzHdT0BtsYTp570JGQ.jpg"),
       ('Schindler''s List', 195, TRUE, 'Drama, War', TRUE, "https://resizing.flixster.com/-XZAfHZM39UwaGJIFWKAE8fS0ak=/v3/t/assets/p15227_v_v8_bf.jpg"),
       ('The Gentalman', 113, TRUE, 'Action, Comedy', FALSE, "https://image.tmdb.org/t/p/original/jtrhTYB7xSrJxR1vusu99nvnZ1g.jpg"),
       ('Fight Club', 139, TRUE, 'Drama', TRUE, "https://i.etsystatic.com/5601395/r/il/0ffcf4/4045621114/il_fullxfull.4045621114_3yut.jpg");


INSERT INTO hall (number, amount_of_front_row_discounted)
VALUES (1, 0.25),
       (2, 0.2),
       (3, 0.2),
       (4, 0.15),
       (5, 0.15),
       (6, 0.3),
       (7, 0.2),
       (8, 0.15),
       (9, 0.25),
       (10, 0.15);


INSERT INTO screening (is3d, movie_id, date, time, cinema_id, hall_id)
VALUES (FALSE, 1, NOW() + INTERVAL 1 DAY, '15:30:00', 1, 1),
       (TRUE, 2, NOW() + INTERVAL 2 DAY, '15:30:00', 1, 1),
       (FALSE, 3, NOW() + INTERVAL 3 DAY, '17:30:00',2, 2),
       (FALSE, 4, NOW() + INTERVAL 4 DAY,'15:30:00',3, 3),
       (TRUE, 5, NOW() + INTERVAL 5 DAY, '15:30:00',2, 2),
        (FALSE, 6, NOW() + INTERVAL 6 DAY, '15:30:00',1, 1),
        (TRUE, 7, NOW() + INTERVAL 7 DAY, '15:30:00',1, 1),
        (FALSE, 8, NOW() + INTERVAL 8 DAY, '15:30:00',2, 2),
        (FALSE, 9, NOW() + INTERVAL 9 DAY, '15:30:00',3, 3),
        (TRUE, 10, NOW() + INTERVAL 10 DAY, '15:30:00',2, 2);


INSERT INTO seat (row_letter, number)
VALUES
    ('A', 1), ('A', 2), ('A', 3), ('A', 4), ('A', 5), ('A', 6), ('A', 7), ('A', 8), ('A', 9), ('A', 10),
    ('B', 1), ('B', 2), ('B', 3), ('B', 4), ('B', 5), ('B', 6), ('B', 7), ('B', 8), ('B', 9), ('B', 10),
    ('C', 1), ('C', 2), ('C', 3), ('C', 4), ('C', 5), ('C', 6), ('C', 7), ('C', 8), ('C', 9), ('C', 10),
    ('D', 1), ('D', 2), ('D', 3), ('D', 4), ('D', 5), ('D', 6), ('D', 7), ('D', 8), ('D', 9), ('D', 10),
    ('E', 1), ('E', 2), ('E', 3), ('E', 4), ('E', 5), ('E', 6), ('E', 7), ('E', 8), ('E', 9), ('E', 10),
    ('F', 1), ('F', 2), ('F', 3), ('F', 4), ('F', 5), ('F', 6), ('F', 7), ('F', 8), ('F', 9), ('F', 10),
    ('G', 1), ('G', 2), ('G', 3), ('G', 4), ('G', 5), ('G', 6), ('G', 7), ('G', 8), ('G', 9), ('G', 10),
    ('H', 1), ('H', 2), ('H', 3), ('H', 4), ('H', 5), ('H', 6), ('H', 7), ('H', 8), ('H', 9), ('H', 10),
    ('I', 1), ('I', 2), ('I', 3), ('I', 4), ('I', 5), ('I', 6), ('I', 7), ('I', 8), ('I', 9), ('I', 10),
    ('J', 1), ('J', 2), ('J', 3), ('J', 4), ('J', 5), ('J', 6), ('J', 7), ('J', 8), ('J', 9), ('J', 10),
    ('K', 1), ('K', 2), ('K', 3), ('K', 4), ('K', 5), ('K', 6), ('K', 7), ('K', 8), ('K', 9), ('K', 10),
    ('L', 1), ('L', 2), ('L', 3), ('L', 4), ('L', 5), ('L', 6), ('L', 7), ('L', 8), ('L', 9), ('L', 10),
    ('M', 1), ('M', 2), ('M', 3), ('M', 4), ('M', 5), ('M', 6), ('M', 7), ('M', 8), ('M', 9), ('M', 10),
    ('N', 1), ('N', 2), ('N', 3), ('N', 4), ('N', 5), ('N', 6), ('N', 7), ('N', 8), ('N', 9), ('N', 10),
    ('O', 1), ('O', 2), ('O', 3), ('O', 4), ('O', 5), ('O', 6), ('O', 7), ('O', 8), ('O', 9), ('O', 10),
    ('P', 1), ('P', 2), ('P', 3), ('P', 4), ('P', 5), ('P', 6), ('P', 7), ('P', 8), ('P', 9), ('P', 10),
    ('Q', 1), ('Q', 2), ('Q', 3), ('Q', 4), ('Q', 5), ('Q', 6), ('Q', 7), ('Q', 8), ('Q', 9), ('Q', 10),
    ('R', 1), ('R', 2), ('R', 3), ('R', 4), ('R', 5), ('R', 6), ('R', 7), ('R', 8), ('R', 9), ('R', 10),
    ('S', 1), ('S', 2), ('S', 3), ('S', 4), ('S', 5), ('S', 6), ('S', 7), ('S', 8), ('S', 9), ('S', 10),
    ('T', 1), ('T', 2), ('T', 3), ('T', 4), ('T', 5), ('T', 6), ('T', 7), ('T', 8), ('T', 9), ('T', 10),
    ('U', 1), ('U', 2), ('U', 3), ('U', 4), ('U', 5), ('U', 6), ('U', 7), ('U', 8), ('U', 9), ('U', 10),
    ('V', 1), ('V', 2), ('V', 3), ('V', 4), ('V', 5), ('V', 6), ('V', 7), ('V', 8), ('V', 9), ('V', 10),
    ('W', 1), ('W', 2), ('W', 3), ('W', 4), ('W', 5), ('W', 6), ('W', 7), ('W', 8), ('W', 9), ('W', 10),
    ('X', 1), ('X', 2), ('X', 3), ('X', 4), ('X', 5), ('X', 6), ('X', 7), ('X', 8), ('X', 9), ('X', 10),
    ('Y', 1), ('Y', 2), ('Y', 3), ('Y', 4), ('Y', 5), ('Y', 6), ('Y', 7), ('Y', 8), ('Y', 9), ('Y', 10),
    ('Z', 1), ('Z', 2), ('Z', 3), ('Z', 4), ('Z', 5), ('Z', 6), ('Z', 7), ('Z', 8), ('Z', 9), ('Z', 10);

INSERT INTO ticket (screening_id, seat_id, price)
VALUES (1, 1, 30),
       (1, 2, 30),
       (2, 3, 25),
       (2, 4, 25),
       (3, 5, 20),
       (3, 6, 20),
       (4, 7, 35),
       (4, 8, 35),
       (5, 9, 40),
       (5, 10, 40),
        (6, 11, 30),
        (6, 12, 30),
        (7, 13, 25),
        (7, 14, 25),
        (8, 15, 20),
        (8, 16, 20),
        (9, 17, 35),
        (9, 18, 35),
        (10, 19, 40),
        (10, 20, 40);

INSERT INTO cinema_hall (cinema_id, hall_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (2, 4),
       (3, 5),
       (3, 6),
       (4, 7),
       (4, 8),
       (1, 9),
       (1, 10);

INSERT INTO hall_seat (hall_id, seat_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (2, 4),
       (3, 5),
       (3, 6),
       (4, 7),
       (4, 8),
       (1, 9),
       (1, 10),
       (2, 11),
       (2, 12),
       (3, 13),
       (3, 14),
       (4, 15),
       (4, 16),
       (5, 17),
       (5, 18),
       (6, 19),
       (6, 20),
       (7, 21),
       (7, 22),
       (8, 23),
       (8, 24),
       (9, 25),
       (9, 26),
       (10, 27),
       (10, 28);



INSERT INTO hall_screening (hall_id, screening_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
        (5, 5),
        (6, 6),
         (7, 7),
         (8, 8),
         (9, 9),
         (10, 10);

INSERT INTO screening_tickets (screening_id, tickets_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (2, 4),
       (3, 5),
       (3, 6),
       (4, 7),
       (4, 8),
       (5, 9),
       (5, 10),
       (6, 11),
       (6, 12),
       (7, 13),
       (7, 14),
       (8, 15),
       (8, 16),
       (9, 17),
       (9, 18),
       (10, 19),
       (10, 20);

INSERT INTO cinema_movies (cinema_id, movies_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (2, 3),
       (2, 4),
       (3, 1),
       (3, 2),
       (3, 3),
       (3, 4);

INSERT INTO reservation (screening_id, is_completed)
VALUES (1, FALSE),
       (2, FALSE),
       (3, FALSE),
       (4, FALSE),
       (5, FALSE),
       (6, TRUE),
       (7, FALSE),
       (8, FALSE),
       (9, FALSE),
       (10, FALSE);

INSERT INTO reservation_tickets(reservation_id, tickets_id)
VALUES (1, 1),
       (1, 6),
       (2, 2),
       (3, 3),
       (4, 4),
         (5, 5),
         (6, 7),
         (7, 8),
         (8, 9),
         (9, 10),
         (10, 11);