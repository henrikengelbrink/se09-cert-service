CREATE TABLE certificates (
    id uuid NOT NULL PRIMARY KEY,
    client_id uuid NOT NULL,
    client_type text NOT NULL,
    request_id text NOT NULL,
    serial_number text NOT NULL,
    expiration float NOT NULL,
    deleted_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
