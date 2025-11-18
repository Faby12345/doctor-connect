-- If the old table exists, rename it; otherwise create the correct one.
DO $$
BEGIN
  IF to_regclass('public.doctors_profile') IS NULL THEN
    IF to_regclass('public."DoctorsProfile"') IS NOT NULL THEN
ALTER TABLE "DoctorsProfile" RENAME TO doctors_profile;
ELSIF to_regclass('public.doctorsprofile') IS NOT NULL THEN
ALTER TABLE doctorsprofile RENAME TO doctors_profile;
ELSE
CREATE TABLE doctors_profile (
                                 user_id UUID PRIMARY KEY REFERENCES users(id),
                                 speciality VARCHAR(255) NOT NULL,
                                 city VARCHAR(255) NOT NULL,
                                 bio VARCHAR(255) NOT NULL,
                                 price_min_cents INT NOT NULL,
                                 price_max_cents INT NOT NULL,
                                 verified BOOLEAN NOT NULL DEFAULT false,
                                 rating_avg NUMERIC(3,2) NOT NULL DEFAULT 0,
                                 rating_count INT NOT NULL DEFAULT 0
);
END IF;
END IF;
END $$;
