CREATE TABLE users (
   id BIGSERIAL PRIMARY KEY,
   name VARCHAR(100) NOT NULL,
   last_name VARCHAR(100) NOT NULL,
   email VARCHAR(100) NOT NULL UNIQUE,
   base_salary DECIMAL(12,2) NOT NULL CHECK (base_salary > 0 AND base_salary <= 15000000),
   document_number VARCHAR(8),
   birth_date DATE,
   address VARCHAR(255),
   phone VARCHAR(20),
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_document_number ON users(document_number);