CREATE TABLE custom (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    message JSONB,
    transaction_uuid VARCHAR(255) NOT NULL,
    is_progress_completed BOOLEAN DEFAULT FALSE,
    created BIGINT DEFAULT EXTRACT(EPOCH FROM CURRENT_TIMESTAMP)::BIGINT,
    updated BIGINT DEFAULT EXTRACT(EPOCH FROM CURRENT_TIMESTAMP)::BIGINT,
    deleted BIGINT,
    created_by VARCHAR(255),
    updated_by VARCHAR(255)
);
