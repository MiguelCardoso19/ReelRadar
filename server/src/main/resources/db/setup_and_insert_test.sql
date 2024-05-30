CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
    );

INSERT INTO users (id, username, password, email) VALUES
    ('22222222-2222-2222-2222-222222222222', 'user1', 'password1', 'user1@example.com'),
    ('44444444-4444-4444-4444-444444444444', 'user2', 'password2', 'user2@example.com'),
    ('66666666-6666-6666-6666-666666666666', 'user3', 'password3', 'user3@example.com');


CREATE TABLE IF NOT EXISTS favorites (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    tvShows TEXT[],
    movies TEXT[],
    people TEXT[],
    FOREIGN KEY (user_id) REFERENCES users (id)
    );

INSERT INTO favorites (id, user_id, tvShows, movies, people) VALUES
    ('11111111-1111-1111-1111-111111111111', '22222222-2222-2222-2222-222222222222',
    ARRAY['tt1234567', 'tt2345678', 'tt3456789'],
    ARRAY['tt4567890', 'tt5678901', 'tt6789012'],
    ARRAY['tt7890123', 'tt8901234', 'tt9012345']),
    ('33333333-3333-3333-3333-333333333333', '44444444-4444-4444-4444-444444444444',
    ARRAY['tt2234567', 'tt3345678', 'tt4456789'],
    ARRAY['tt5567890', 'tt6678901', 'tt7789012'],
    ARRAY['tt8890123', 'tt9901234', 'tt1012345']),
    ('55555555-5555-5555-5555-555555555555', '66666666-6666-6666-6666-666666666666',
    ARRAY['tt3234567', 'tt4345678', 'tt5456789'],
    ARRAY['tt6567890', 'tt7678901', 'tt8789012'],
    ARRAY['tt9890123', 'tt10901234', 'tt1112345']);


-- psql -h localhost -U postgres -d reelRadarDB -f setup_and_insert_test.sql