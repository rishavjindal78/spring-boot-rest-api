create table article (
	id UUID NOT NULL,
	title VARCHAR(128),
	description VARCHAR(128),
	body VARCHAR(128),
	createdAt TIMESTAMP,
	updatedAt TIMESTAMP,
	tags Array,
	favorited BOOLEAN,
	favoritesCount INT,
	createdBy VARCHAR(15),
	PRIMARY KEY (id)
        
);