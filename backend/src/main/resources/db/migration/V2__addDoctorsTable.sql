CREATE TABLE doctors_profile(
    user_id UUID PRIMARY KEY REFERENCES users(id),
    speciality varchar(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    bio VARCHAR(255) NOT NULL,
    price_min_cents INT NOT NULL,
    price_max_cents INT NOT NULL,
    verified BOOLEAN NOT NULL DEFAULT false,
    rating_avg NUMERIC(3,2) NOT NULL DEFAULT 0,
    rating_count INT NOT NULL DEFAULT 0
);