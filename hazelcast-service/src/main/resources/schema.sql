CREATE TABLE DuplicateOperations (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    request_message JSONB,
    operation_uuid VARCHAR(255) NOT NULL,
    created_date DATE DEFAULT EXTRACT(EPOCH FROM CURRENT_TIMESTAMP)::BIGINT,
    updated_date DATE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255)
);
