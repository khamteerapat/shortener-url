CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    username VARCHAR UNIQUE,
    password VARCHAR NOT NULL,
    email VARCHAR NOT NULL,
    status BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);
CREATE INDEX idx_users_username ON users(username);

CREATE TABLE links_mapping (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    short_code VARCHAR UNIQUE,
    original_link TEXT, 
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR NOT NULL,
    updated_at TIMESTAMP,
    updated_by VARCHAR
);
CREATE INDEX idx_links_mapping_short_code ON links_mapping(short_code);