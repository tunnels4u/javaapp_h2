CREATE SCHEMA IF NOT EXISTS tunneldataschema;
SET SCHEMA tunneldataschema;
CREATE TABLE Users (ID int NOT NULL,
                    First_Name VARCHAR(100) NOT NULL, 
                    Last_Name VARCHAR(100) NOT NULL,
                    PRIMARY KEY (ID));
CREATE TABLE Personal_Details (
    PD_ID int NOT NULL,
    Address VARCHAR(100) NOT NULL,
    Having_Pets BOOLEAN,
    ID int NOT NULL,
    PRIMARY KEY (PD_ID),
    FOREIGN KEY (ID) REFERENCES Users(ID)
);