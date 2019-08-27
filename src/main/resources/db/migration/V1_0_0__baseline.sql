CREATE TABLE instances (
  id VARCHAR(40) NOT NULL,
  name VARCHAR(255) NOT NULL,
  deployment VARCHAR(255) DEFAULT '-',
  requireSpot BOOLEAN,
  created_at TIMESTAMP DEFAULT NOW(),
  updated_at TIMESTAMP DEFAULT NOW() ON UPDATE NOW(),

  PRIMARY KEY(deployment,name)
);
