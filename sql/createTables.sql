CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username varchar NOT NULL,
                       password varchar NOT NULL
);

CREATE TABLE messages (
                       message_id SERIAL PRIMARY KEY,
                       from_user_id int NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                       content varchar,
                       to_user_id int REFERENCES users(id) ON DELETE CASCADE
);