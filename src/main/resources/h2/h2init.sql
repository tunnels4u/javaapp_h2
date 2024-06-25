CREATE SCHEMA IF NOT EXISTS TUNNELDATASCHEMA;
SET SCHEMA TUNNELDATASCHEMA;
CREATE TABLE Employee (ID int auto_increment NOT NULL,
                    First_Name VARCHAR(100) NOT NULL, 
                    Last_Name VARCHAR(100) NOT NULL,
                    PRIMARY KEY (ID));
CREATE TABLE Personal_Details (
    PD_ID int auto_increment NOT NULL,
    Address VARCHAR(100),
    Having_Pets BOOLEAN,
    ID int NOT NULL,
    PRIMARY KEY (PD_ID),
    FOREIGN KEY (ID) REFERENCES Employee(ID)
);