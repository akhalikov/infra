CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    first_name VARCHAR (128),
    last_name VARCHAR (128),
    birth_date DATE,
    created_timestamp TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    updated_timestamp TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    test_date_time TIMESTAMP WITH TIME ZONE
);

CREATE TABLE events (
    event_id SERIAL PRIMARY KEY,
    title VARCHAR (255),
    event_date TIMESTAMP without TIME ZONE,
    user_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

