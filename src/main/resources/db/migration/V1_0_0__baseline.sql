CREATE TABLE instances (
  id VARCHAR(40) NOT NULL,
  name VARCHAR(255),
  deployment VARCHAR(255),
  require_spot BOOLEAN,
  created_at TIMESTAMP DEFAULT NOW(),
  updated_at TIMESTAMP DEFAULT NOW() ON UPDATE NOW(),

  PRIMARY KEY(id)
);
