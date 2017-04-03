CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    first_name VARCHAR (128),
    last_name VARCHAR (128),
    created_timestamp TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    updated_timestamp TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    date_time TIMESTAMP WITH TIME ZONE
);

CREATE TABLE events (
    event_id SERIAL PRIMARY KEY,
    title VARCHAR (255),
    event_date TIMESTAMP without TIME ZONE
);
