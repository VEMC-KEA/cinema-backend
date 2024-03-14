USE cinema;

INSERT INTO cinema (name, reservation_fee, group_discount, movie_base_price)
VALUES ('Cinema Royale', 12, 0.15, 60),
       ('Movie Magic', 10, 0.1, 55),
       ('Star Cinema', 15, 0.2, 65);

INSERT INTO movie (title, run_time, is_classic, genre, pg13, image_url)
VALUES ('The Matrix', 136, FALSE, 'Sci-Fi', TRUE, "https://i.etsystatic.com/20217829/r/il/16b095/2467136209/il_fullxfull.2467136209_lutd.jpg"),
       ('The Shawshank Redemption', 142, TRUE, 'Drama', FALSE, "https://i.ytimg.com/vi/fLAL_d3DqO0/maxresdefault.jpg?sqp=-oaymwEmCIAKENAF8quKqQMa8AEB-AH-CYAC0AWKAgwIABABGGUgWChHMA8=&rs=AOn4CLCs8Wc7ANaIG44oFZ_a30tW1eqs1Q"),
       ('Inception', 148, FALSE, 'Sci-Fi', TRUE, " https://resizing.flixster.com/-XZAfHZM39UwaGJIFWKAE8fS0ak=/v3/t/assets/p7825626_p_v8_af.jpg"),
       ('Forrest Gump', 142, TRUE, 'Drama', TRUE, "https://i5.walmartimages.com/seo/Running-Forrest-Gump-Costume_16263de0-e423-4c2b-8514-7ee4989c8d44_1.f00d571b0cd0b44c98d45dd9dc200ee9.jpeg?odnHeight=768&odnWidth=768&odnBg=FFFFFF"),
       ('The Dark Knight', 152, FALSE, 'Action', TRUE, "https://m.media-amazon.com/images/M/MV5BMTk4ODQzNDY3Ml5BMl5BanBnXkFtZTcwODA0NTM4Nw@@._V1_FMjpg_UX1000_.jpg");

INSERT INTO hall (number, amount_of_front_row_discounted)
VALUES (1, 0.25),
       (2, 0.2),
       (3, 0.2),
       (4, 0.15);

/*INSERT INTO screening (is3d, movie_id, datetime, cinema_id, hall_id)
VALUES (FALSE, 1, NOW() + INTERVAL 1 DAY, 1, 1),
       (TRUE, 2, NOW() + INTERVAL 2 DAY, 1, 1),
       (FALSE, 3, NOW() + INTERVAL 3 DAY, 2, 2),
       (FALSE, 4, NOW() + INTERVAL 4 DAY, 3, 3),
       (TRUE, 5, NOW() + INTERVAL 5 DAY, 2, 2);*/

INSERT INTO screening (is3d, movie_id, datetime, cinema_id, hall_id)
VALUES (FALSE, 1, NOW() + INTERVAL 1 DAY, 1, 1),
       (TRUE, 2, NOW() + INTERVAL 2 DAY, 1, 1),
       (FALSE, 3, NOW() + INTERVAL 3 DAY, 2, 2),
       (FALSE, 4, NOW() + INTERVAL 4 DAY, 3, 3),
       (TRUE, 5, NOW() + INTERVAL 5 DAY, 2, 2);

INSERT INTO seat (row_letter, number)
VALUES ('A', 1),
       ('B', 2),
       ('A', 1),
       ('B', 2),
       ('A', 1),
       ('B', 2),
       ('A', 1),
       ('B', 2),
       ('A', 1),
       ('B', 2);

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
       (5, 10, 40);

INSERT INTO cinema_hall (cinema_id, hall_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (2, 4);

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
       (1, 10);



INSERT INTO hall_screening (hall_id, screening_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4);

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
       (5, 10);

INSERT INTO cinema_movies (cinema_id, movies_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (2, 3),
       (2, 4),
       (3, 1);

INSERT INTO reservation (screening_id, is_completed)
VALUES (1, FALSE),
       (2, FALSE),
       (3, FALSE),
       (4, FALSE),
       (5, FALSE);

INSERT INTO reservation_tickets(reservation_id, tickets_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4);