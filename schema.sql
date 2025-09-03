DROP TABLE IF EXISTS loan_applications CASCADE;
DROP TABLE IF EXISTS loan_types CASCADE;
DROP TABLE IF EXISTS statuses CASCADE;

CREATE TABLE statuses (
  id_status BIGSERIAL PRIMARY KEY,
  name VARCHAR(100) NOT NULL UNIQUE,
  description TEXT
);

CREATE TABLE loan_types (
  id_loan_type BIGSERIAL PRIMARY KEY,
  name VARCHAR(120) NOT NULL UNIQUE,
  min_amount NUMERIC(12,2) NOT NULL CHECK (min_amount >= 0),
  max_amount NUMERIC(12,2) NOT NULL CHECK (max_amount >= min_amount),
  interest_rate NUMERIC(6,4) NOT NULL CHECK (interest_rate >= 0),
  automatic_validation BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE loan_applications (
  id_loan_application BIGSERIAL PRIMARY KEY,
  amount NUMERIC(12,2) NOT NULL CHECK (amount > 0),
  term INTEGER NOT NULL CHECK (term > 0),
  email VARCHAR(254),
  id_status BIGINT,
  id_loan_type BIGINT NOT NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT fk_loan_applications_status
    FOREIGN KEY (id_status) REFERENCES statuses (id_status)
    ON UPDATE CASCADE ON DELETE RESTRICT,
  CONSTRAINT fk_loan_applications_loan_type
    FOREIGN KEY (id_loan_type) REFERENCES loan_types (id_loan_type)
    ON UPDATE CASCADE ON DELETE RESTRICT,
  CONSTRAINT chk_loan_applications_email_format
    CHECK (email IS NULL OR email ~* '^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$')
);

CREATE INDEX idx_loan_applications_email ON loan_applications (email);
CREATE INDEX idx_loan_applications_status ON loan_applications (id_status);
CREATE INDEX idx_loan_applications_loan_type ON loan_applications (id_loan_type);

CREATE OR REPLACE FUNCTION set_updated_at()
RETURNS TRIGGER LANGUAGE plpgsql AS $$
BEGIN
  NEW.updated_at := now();
  RETURN NEW;
END $$;

DROP TRIGGER IF EXISTS trg_loan_applications_updated_at ON loan_applications;
CREATE TRIGGER trg_loan_applications_updated_at
BEFORE UPDATE ON loan_applications
FOR EACH ROW EXECUTE FUNCTION set_updated_at();

INSERT INTO statuses (name, description) VALUES
  ('pending', 'Application has been submitted and is awaiting review'),
  ('approved', 'Application has been approved by the system or an agent'),
  ('rejected', 'Application has been rejected'),
  ('cancelled', 'Application was cancelled by the applicant'),
  ('disbursed', 'Loan has been approved and funds have been disbursed');

INSERT INTO loan_types (name, min_amount, max_amount, interest_rate, automatic_validation) VALUES
  ('Personal Loan', 500.00, 20000.00, 0.2500, TRUE),
  ('Micro Loan', 100.00, 2000.00, 0.3500, TRUE),
  ('Business Loan', 1000.00, 50000.00, 0.1800, FALSE);