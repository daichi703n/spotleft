CREATE TABLE instances (
  id VARCHAR(40) NOT NULL,
  name VARCHAR(255) NOT NULL,
  deployment VARCHAR(255) DEFAULT '-',
  require_spot BOOLEAN,
  created_at TIMESTAMP DEFAULT NOW(),
  updated_at TIMESTAMP DEFAULT NOW() ON UPDATE NOW(),

  PRIMARY KEY(id)
);

INSERT INTO instances 
  (id, name, deployment, require_spot)
  VALUES
  (1, 'test', 'test_dep', true)
;