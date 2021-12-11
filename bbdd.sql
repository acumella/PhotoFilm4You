----Customer table-----

DROP TABLE if EXISTS practicalcase.customer CASCADE;

DROP SEQUENCE if EXISTS customer_seq;

CREATE SEQUENCE customer_seq START 4;

CREATE TABLE practicalcase.customer(
	id INTEGER,
	nif VARCHAR (50) NOT NULL,
	name VARCHAR (50) NOT NULL,
	surname VARCHAR (50) NOT NULL,
	phone VARCHAR (20) NOT NULL,
	address VARCHAR (255) NOT NULL,
	PRIMARY KEY (id)
);

----User table----

DROP TABLE if EXISTS practicalcase.user CASCADE;

DROP SEQUENCE if EXISTS user_seq;

CREATE SEQUENCE user_seq START 5;

CREATE TABLE practicalcase.user(
	id INTEGER,
	email VARCHAR (50) NOT NULL,
	password VARCHAR (20) NOT NULL,
	role VARCHAR (20) NOT NULL,
	createdAt date NOT NULL,
	customer INTEGER,
	PRIMARY KEY (id),
	FOREIGN KEY (customer) REFERENCES practicalcase.customer (id) ON UPDATE CASCADE,
	UNIQUE (email)
);

insert into practicalcase.customer(id, nif, name, surname, phone, address)
values (1, '11111111A','Jonathan','Uoc', '666666660', 'Bcn');

insert into practicalcase.user(id, email, password, role, createdAt, customer)
values (1, 'jsierrah@uoc.edu','test123','ROLE_CUSTOMER', '2020-12-01', 1);

insert into practicalcase.customer(id, nif, name, surname, phone, address)
values (2, '11111111B','Manuel','Uoc', '666666661', 'Bcn');

insert into practicalcase.user(id, email, password, role, createdAt, customer)
values (2, 'monolo15@uoc.edu','test123','ROLE_CUSTOMER', '2020-12-01', 2);

insert into practicalcase.customer(id, nif, name, surname, phone, address)
values (3, '11111111C','Albert','Uoc', '666666662', 'Mallorca');

insert into practicalcase.user(id, email, password, role, createdAt, customer)
values (3, 'acumella@uoc.edu','test123','ROLE_CUSTOMER', '2020-12-01', 3);

insert into practicalcase.user(id, email, password, role, createdAt, customer)
values (4, 'admin@uoc.edu','test123','ROLE_ADMIN', '2020-12-01', null);

----Rent table----

drop table if exists practicalcase.rent cascade;

create table practicalcase.rent(
id varchar(64) not null,
fromDate date,
toDate date,
customer INTEGER,
status int,
totalPrice double precision,
primary key(id),
CONSTRAINT fk_customer
	FOREIGN KEY(customer) 
		REFERENCES practicalcase.customer(id));

insert into practicalcase.rent(id, fromDate, toDate, customer, status, totalPrice)
values ('1','01-12-2020','01-01-2021', 1, 0, 0.0);

insert into practicalcase.rent(id, fromDate, toDate, customer, status, totalPrice)
values ('2','16-12-2020','18-12-2020', 2, 1, 30.0);

insert into practicalcase.rent(id, fromDate, toDate, customer, status, totalPrice)
values ('3','16-12-2020','17-12-2020', 3, 2, 100.0);

----Category Table----
DROP TABLE if EXISTS practicalcase.category CASCADE;

DROP SEQUENCE if EXISTS category_seq;

CREATE SEQUENCE category_seq START 10;

CREATE TABLE practicalcase.category(
	id INTEGER,
	name VARCHAR (20) NOT NULL,
	parentCategory INTEGER,
	PRIMARY KEY (id),
	FOREIGN KEY (parentCategory) REFERENCES practicalcase.category (id) ON UPDATE CASCADE,
	UNIQUE (name)
);

insert into practicalcase.category(id, name, parentCategory)
values (1,'Nature', null);

insert into practicalcase.category(id, name, parentCategory)
values (2,'Tec', null);

insert into practicalcase.category(id, name, parentCategory)
values (3,'Sky', 1);

insert into practicalcase.category(id, name, parentCategory)
values (4,'Photo', 2);

----Brand Table----
DROP TABLE if EXISTS practicalcase.brand CASCADE;

DROP SEQUENCE if EXISTS brand_seq;

CREATE SEQUENCE brand_seq START 10;

CREATE TABLE practicalcase.brand(
	id INTEGER,
	name VARCHAR (20) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE (name)
);

insert into practicalcase.brand(id, name)
values (1,'Sony');

insert into practicalcase.brand(id, name)
values (2,'Nico');

insert into practicalcase.brand(id, name)
values (3,'Cannon');

----Model Table----
DROP TABLE if EXISTS practicalcase.model CASCADE;

DROP SEQUENCE if EXISTS model_seq;

CREATE SEQUENCE model_seq START 10;

CREATE TABLE practicalcase.model(
	id INTEGER,
	name VARCHAR (20) NOT NULL,
	brand INTEGER NOT NULL,
	FOREIGN KEY (brand) REFERENCES practicalcase.brand (id) ON UPDATE CASCADE,
	PRIMARY KEY (id),
	UNIQUE (name)
);

insert into practicalcase.model(id, name, brand)
values (1,'Expensive', 1);

insert into practicalcase.model(id, name, brand)
values (2,'Normal', 2);

insert into practicalcase.model(id, name, brand)
values (3,'Cheap', 3);

insert into practicalcase.model(id, name, brand)
values (4,'Advanced', 1);

insert into practicalcase.model(id, name, brand)
values (5,'User', 2);

insert into practicalcase.model(id, name, brand)
values (6,'Initiation', 3);

----Product Table----
DROP TABLE if EXISTS practicalcase.product CASCADE;

DROP SEQUENCE if EXISTS product_seq;

CREATE SEQUENCE product_seq START 10;

CREATE TABLE practicalcase.product(
	id INTEGER,
	category INTEGER,
	name VARCHAR (20) NOT NULL,
	brand INTEGER,
	model INTEGER,
	dailyPrice INTEGER,
	description VARCHAR (20) NOT NULL,
	availableItems INTEGER,
	productRatting REAL,
	PRIMARY KEY (id),
	FOREIGN KEY (category) REFERENCES practicalcase.category (id) ON UPDATE CASCADE,
	FOREIGN KEY (brand) REFERENCES practicalcase.brand (id) ON UPDATE CASCADE,
	FOREIGN KEY (model) REFERENCES practicalcase.model (id) ON UPDATE CASCADE
);


insert into practicalcase.product(id, category, name, brand, model, dailyPrice, description, availableItems, productRatting)
values (1,1,'lens',1,1,10,'Lens of camera',1,0.0);

insert into practicalcase.product(id, category, name, brand, model, dailyPrice, description, availableItems, productRatting)
values (2,2,'Camera',2,2,15,'Take photos',10,0.0);
		
insert into practicalcase.product(id, category, name, brand, model, dailyPrice, description, availableItems, productRatting)
values (3,3,'BackPack',3,3,20,'Store things',100,0.0);

insert into practicalcase.product(id, category, name, brand, model, dailyPrice, description, availableItems, productRatting)
values (4,3,'Other',1,4,25,'Other things',10,0.0);

----Item table ----

DROP TABLE if EXISTS practicalcase.item CASCADE;

CREATE TABLE practicalcase.item(
	serialNumber varchar(255) not null,
	status VARCHAR (12) NOT NULL,
	product INTEGER,
	FOREIGN KEY (product) REFERENCES practicalcase.product (id) ON UPDATE CASCADE,
	PRIMARY KEY (serialNumber)
);

insert into practicalcase.item(serialNumber, status, product)
values ('SN010101','OPERATIONAL',1);

insert into practicalcase.item(serialNumber, status, product)
values ('SN010102','OPERATIONAL',2);

insert into practicalcase.item(serialNumber, status, product)
values ('SN010103','OPERATIONAL',3);

insert into practicalcase.item(serialNumber, status, product)
values ('SN010104','OPERATIONAL',4);


----RentItem table ----

drop table if exists practicalcase.rentItem;
create table practicalcase.rentItem(
id serial not null,
rentID varchar(64),
serialNumber varchar(255),
primary key(id),
CONSTRAINT fk_rentID
	FOREIGN KEY(rentID) 
		REFERENCES practicalcase.rent(id),
CONSTRAINT fk_serialNumber
	FOREIGN KEY(serialNumber) 
		REFERENCES practicalcase.item(serialNumber));

insert into practicalcase.rentItem(rentID, serialNumber)
values ('1', 'SN010101');

insert into practicalcase.rentItem(rentID, serialNumber)
values ('1', 'SN010102');

insert into practicalcase.rentItem(rentID, serialNumber)
values ('1', 'SN010103');

insert into practicalcase.rentItem(rentID, serialNumber)
values ('2', 'SN010102');

insert into practicalcase.rentItem(rentID, serialNumber)
values ('3', 'SN010103');

insert into practicalcase.rentItem(rentID, serialNumber)
values ('3', 'SN010101');

---Cancellation table ----

drop table if exists practicalcase.cancellation cascade;

create table practicalcase.cancellation(
creationDate date,
penalization double precision,
penalizationStatus int,
rent varchar(64),
primary key(rent),
CONSTRAINT fk_rent
	FOREIGN KEY(rent) 
		REFERENCES practicalcase.rent(id));
		
insert into practicalcase.cancellation (creationDate, penalization, penalizationStatus, rent)
values ('01-12-2020', 3.5, 0, '1');

insert into practicalcase.cancellation (creationDate, penalization, penalizationStatus, rent)
values ('16-12-2020', 13.5, 1, '2');

----Reservation table ----

drop table if exists practicalcase.reservation;
create table practicalcase.reservation(
id serial not null,
rentID varchar(64),
serialNumber varchar(255),
primary key(id),
CONSTRAINT fk_serialNumber
	FOREIGN KEY(serialNumber) 
		REFERENCES practicalcase.item(serialNumber));

----Product Rating table -----

DROP TABLE if EXISTS practicalcase.productRatting CASCADE;

DROP SEQUENCE if EXISTS productrating_seq;

CREATE SEQUENCE productrating_seq START 1;

CREATE TABLE practicalcase.productRatting(
	id INTEGER NOT NULL,
	comment VARCHAR (20),
	ratting INTEGER,
	product INTEGER,
	customer INTEGER,
	PRIMARY KEY (id),
	CONSTRAINT fk_product
		FOREIGN KEY(product) 
			REFERENCES practicalcase.product(id),
	CONSTRAINT fk_customer
		FOREIGN KEY(customer) 
			REFERENCES practicalcase.customer(id)
);

----Question table -----

drop table if exists practicalcase.question cascade;

DROP SEQUENCE if EXISTS question_seq;

CREATE SEQUENCE question_seq START 10;

create table practicalcase.question(
	id INTEGER not null,
	title varchar(255),
	message varchar(255),
	customer INTEGER,
	primary key(id),
	CONSTRAINT fk_customer
		FOREIGN KEY(customer) 
			REFERENCES practicalcase.customer(id)
);

insert into practicalcase.question(id, title, message, customer)
values (1, 'Titol original', 'Fa un bon dia', 1);

insert into practicalcase.question(id, title, message, customer)
values (2, 'Titol super original', 'Fa un mal dia', 2);

insert into practicalcase.question(id, title, message, customer)
values (3, 'Titol original: La sequela', 'Bon dia fa', 3);

----Response table -----

drop table if exists practicalcase.response;

DROP SEQUENCE if EXISTS response_seq;

CREATE SEQUENCE response_seq START 10;

create table practicalcase.response(
	id INTEGER not null,
	message varchar(255),
	status INTEGER,
	question INTEGER,
	userId INTEGER,
	primary key(id),
	CONSTRAINT fk_question
		FOREIGN KEY(question) 
			REFERENCES practicalcase.question(id),
	CONSTRAINT fk_userId
		FOREIGN KEY(userId) 
			REFERENCES practicalcase.user(id)
);

insert into practicalcase.response(id, message, status, question)
values (1, 'Jo crec que ploura', 1, 2);

insert into practicalcase.response(id, message, status, question)
values (2, 'Nevara avui?', 1, 3);

----Response image table -----

drop table if exists practicalcase.responseimage;

DROP SEQUENCE if EXISTS responseimage_seq;

CREATE SEQUENCE responseimage_seq START 10;

create table practicalcase.responseimage(
	id INTEGER not null,
	response INTEGER,
	path varchar(255),
	primary key(id),
	CONSTRAINT fk_response
		FOREIGN KEY(response) 
			REFERENCES practicalcase.response(id)
);

insert into practicalcase.responseimage(id, response, path)
values (1, 1, 'paisatge1.jpg');

insert into practicalcase.responseimage(id, response, path)
values (2, 2, 'paisatge2.jpg');