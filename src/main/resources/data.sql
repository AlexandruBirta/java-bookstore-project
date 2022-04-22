INSERT INTO auth.users (username, password, enabled)
values ('jdbc', 'jdbc123', 'true');

INSERT INTO auth.authorities (username, authority)
values ('jdbc', 'ROLE_USER');