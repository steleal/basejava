CREATE TABLE resume
(
    uuid      CHAR(36) PRIMARY KEY,
    full_name TEXT NOT NULL
);

CREATE TABLE contact
(
    id          SERIAL PRIMARY KEY,
    resume_uuid TEXT NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
    type        TEXT NOT NULL,
    value       TEXT NOT NULL
);

CREATE TABLE section
(
    id          SERIAL PRIMARY KEY,
    resume_uuid TEXT NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
    type        TEXT NOT NULL,
    value       TEXT NOT NULL
);

CREATE UNIQUE INDEX contact_resume_uuid_type_index
    ON contact (resume_uuid, type);

CREATE INDEX section_resume_uuid_index
    ON section (resume_uuid);
