CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    first_name VARCHAR (128),
    last_name VARCHAR (128)
);

CREATE TABLE events (
    event_id SERIAL PRIMARY KEY,
    title VARCHAR (255),
    event_date TIMESTAMP without TIME ZONE
);
