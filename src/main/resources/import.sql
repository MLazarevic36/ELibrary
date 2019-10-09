INSERT INTO users(user_id, firstname, lastname, username, password, role) VALUES(1, "Marko", "Lazarevic", "a", "a", "ADMINISTRATOR");
INSERT INTO users(user_id, firstname, lastname, username, password, role) VALUES(2, "Slavoljub", "Civot", "b", "b", "PRETPLATNIK");
INSERT INTO users(user_id, firstname, lastname, username, password, role) VALUES(3, "Mirko", "Market", "c", "c", "PRETPLATNIK");

INSERT INTO categories(category_id, name) VALUES(1, "Document");
INSERT INTO categories(category_id, name) VALUES(2, "Thriller");
INSERT INTO categories(category_id, name) VALUES(3, "Short Story");
INSERT INTO categories(category_id, name) VALUES(4, "Drama");

INSERT INTO languages(language_id, name) VALUES(1, "Serbian");
INSERT INTO languages(language_id, name) VALUES(2, "English");

INSERT INTO subscribes(user_id, category_id) VALUES(2, 1);
