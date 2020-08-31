create schema critter;

create user 'sa'@'localhost' identified by 'sa1234'; -- Create the user

grant all on critter.* to 'sa'@'localhost'; -- Gives all privileges to that user on new db

drop schema critter;
