-- Create enum for user roles
CREATE TYPE user_role AS ENUM ('PATIENT', 'DOCTOR', 'ADMIN');

-- Create users table
CREATE TABLE users (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),  -- unique identifier
                       email VARCHAR(255) NOT NULL UNIQUE,             -- must be unique
                       password_hash VARCHAR(255) NOT NULL,            -- store hashed password (BCrypt fits in 60 chars, we give extra room)
                       full_name VARCHAR(255) NOT NULL,                -- display name
                       role user_role NOT NULL,                        -- role enum
                       created_at TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL
);