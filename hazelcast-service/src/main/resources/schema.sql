CREATE TABLE custom (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    message VARCHAR(255),
    transaction_uuid UUID,
    is_progress_completed BOOLEAN DEFAULT FALSE,
    created BIGINT DEFAULT EXTRACT(EPOCH FROM CURRENT_TIMESTAMP)::BIGINT,
    updated BIGINT DEFAULT EXTRACT(EPOCH FROM CURRENT_TIMESTAMP)::BIGINT,
    deleted BIGINT,
    created_by VARCHAR(255),
    updated_by VARCHAR(255)
);
