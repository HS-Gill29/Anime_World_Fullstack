BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS anime;
DROP TABLE IF EXISTS watchlist;
DROP TABLE IF EXISTS review;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE watchlist (
    watchlist_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    anime_title VARCHAR(255) NOT NULL,
    img_url VARCHAR(300),
    duration VARCHAR(255),
    episodes INT,
    studio_name VARCHAR(255),
    studio_url VARCHAR(300),
    genres TEXT,
    background TEXT,
    synopsis TEXT,
    UNIQUE (user_id, anime_title),  -- Ensuring a user cannot add the same anime multiple times
    CONSTRAINT FK_watchlist_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);


CREATE TABLE review (
    review_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    anime_title VARCHAR(255) NOT NULL,
    img_url VARCHAR(300),
    rating INT CHECK (rating >= 1 AND rating <= 5),
    review_text TEXT,
    duration VARCHAR(255),
    episodes INT,
    studio_name VARCHAR(255),
    studio_url VARCHAR(300),
    genres TEXT,
    background TEXT,
    synopsis TEXT,
     UNIQUE (user_id, anime_title),
    CONSTRAINT FK_reviews_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);



COMMIT TRANSACTION;
