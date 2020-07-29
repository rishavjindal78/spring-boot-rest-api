create table article (
	id BIGINT NOT NULL AUTO_INCREMENT,
	title VARCHAR(128),
	description VARCHAR(128),
	body VARCHAR(128),
	createdAt TIMESTAMP,
	updatedAt TIMESTAMP,
	PRIMARY KEY (id)
        
);