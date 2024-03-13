USE cinema;

INSERT INTO cinema (name, reservation_fee, group_discount, movie_base_price)
VALUES ('Cinema Royale', 12, 0.15, 60),
       ('Movie Magic', 10, 0.1, 55),
       ('Star Cinema', 15, 0.2, 65);

INSERT INTO movie (title, run_time, is_classic, genre, pg13)
VALUES ('The Matrix', 136, FALSE, 'Sci-Fi', TRUE),
       ('The Shawshank Redemption', 142, TRUE, 'Drama', FALSE),
       ('Inception', 148, FALSE, 'Sci-Fi', TRUE),
       ('Forrest Gump', 142, TRUE, 'Drama', TRUE),
       ('The Dark Knight', 152, FALSE, 'Action', TRUE);

INSERT INTO hall (number, amount_of_front_row_discounted)
VALUES (1, 0.25),
       (2, 0.2),
       (3, 0.2),
       (4, 0.15);

INSERT INTO screening (is3d, movie_id, datetime, cinema_id)
VALUES (FALSE, 1, NOW() + INTERVAL 1 DAY, 1),
       (TRUE, 2, NOW() + INTERVAL 2 DAY, 1),
       (FALSE, 3, NOW() + INTERVAL 3 DAY, 2),
       (FALSE, 4, NOW() + INTERVAL 4 DAY, 3),
       (TRUE, 5, NOW() + INTERVAL 5 DAY, 2);

INSERT INTO seat (row_num, number)
VALUES (1, 1),
       (1, 2),
       (2, 1),
       (2, 2),
       (3, 1),
       (3, 2),
       (4, 1),
       (4, 2),
       (5, 1),
       (5, 2);

INSERT INTO ticket (screening_id, seat_id, is_completed, price)
VALUES (1, 1, FALSE, 30),
       (1, 2, FALSE, 30),
       (2, 3, FALSE, 25),
       (2, 4, FALSE, 25),
       (3, 5, FALSE, 20),
       (3, 6, FALSE, 20),
       (4, 7, FALSE, 35),
       (4, 8, FALSE, 35),
       (5, 9, FALSE, 40),
       (5, 10, FALSE, 40);

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

INSERT INTO screening_hall (hall_id, screening_id)
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
