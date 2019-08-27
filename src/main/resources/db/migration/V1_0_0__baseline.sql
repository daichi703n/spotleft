CREATE TABLE instances (
  id varchar(40) NOT NULL,
  name varchar(255) NOT NULL,
  deployment varchar(255) DEFAULT '-',
  requireSpot boolean,

  PRIMARY KEY(deployment,name)
);
