CREATE SCHEMA api_rest_tp2;
CREATE USER 'apiador'@'%' IDENTIFIED BY 'api123';
GRANT ALL PRIVILEGES ON api_rest_tp2.* TO 'apiador'@'%';
USE api_rest_tp2;

