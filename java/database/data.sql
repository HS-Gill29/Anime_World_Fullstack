BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');

--INSERT INTO anime (title, description, release_date, genres, image_url) VALUES
--('Naruto', 'A young ninja who seeks recognition from his peers and dreams of becoming the Hokage', '2002-10-03', 'Action, Adventure, Fantasy', 'https://example.com/images/naruto.jpg'),
--('Attack on Titan', 'In a world where giant creatures attack humanity, Eren Yeager vows to destroy them all', '2013-04-07', 'Action, Dark Fantasy, Post-apocalyptic', 'https://example.com/images/aot.jpg'),
--('My Hero Academia', 'A boy born without superpowers in a world where they are common aims to become a superhero himself', '2016-04-03', 'Superhero, Adventure', 'https://example.com/images/mha.jpg');
--
---- Inserting watchlist data
--INSERT INTO watchlist (user_id, anime_id) VALUES
--(1, 1), -- User 'user' adds 'Naruto' to their watchlist
--(1, 2); -- User 'user' adds 'Attack on Titan' to their watchlist
--
---- Inserting review data
--INSERT INTO review (user_id, anime_id, rating, review_text) VALUES
--(1, 1, 5, 'Absolutely thrilling from start to finish!'), -- User 'user' reviews 'Naruto' with a rating of 5
--(1, 2, 4, 'Great story but a bit dark for my taste.');
COMMIT TRANSACTION;
