CREATE KEYSPACE user_timeline WITH REPLICATION = {'class': 'SimpleStrategy',	'replication_factor':	1};

CREATE TABLE user (
  username text,
  email text,
  encrypted_password blob,
  PRIMARY KEY (username)
);

CREATE TABLE user_status (
  username text,
  id timeuuid,
  status text,
  PRIMARY	KEY	(username, id)
);