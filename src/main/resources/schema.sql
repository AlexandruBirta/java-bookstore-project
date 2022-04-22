DROP TABLE IF EXISTS auth.authorities;
DROP TABLE IF EXISTS auth.users;

CREATE TABLE auth.users
(
    username VARCHAR(50)  NOT NULL,
    password VARCHAR(100) NOT NULL,
    enabled  boolean      NOT NULL DEFAULT 'false',
    PRIMARY KEY (username)
);

CREATE TABLE auth.authorities
(
    username  VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    FOREIGN KEY (username) REFERENCES auth.users (username)
);