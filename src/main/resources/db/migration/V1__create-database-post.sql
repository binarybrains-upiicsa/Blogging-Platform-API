CREATE TABLE post (
                        id INTEGER NOT NULL AUTO_INCREMENT UNIQUE,
                        title CHAR(16) NOT NULL,
                        content CHAR(50) NOT NULL,
                        category CHAR(20) NOT NULL,
                        tags CHAR(20),
                        PRIMARY KEY(id)
);


