CREATE TABLE users (
    username varchar(20) PRIMARY KEY,
    password varchar(200) NOT NULL
);

CREATE TABLE messages (
    message_id SERIAL PRIMARY KEY,
    from_user varchar(20) NOT NULL REFERENCES users(username) ON DELETE CASCADE,
    content varchar(2000),
    to_user varchar(20) REFERENCES users(username) ON DELETE CASCADE,
    sent_at timestamp DEFAULT current_timestamp
);