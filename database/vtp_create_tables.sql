-- Create tables for Semester 1 2023 CTG example ER Model
PRAGMA foreign_keys = OFF;
drop table if exists LGA;
drop table if exists Population;
PRAGMA foreign_keys = ON;

CREATE TABLE LGA (
    code              INTEGER NOT NULL,
    year              INTEGER NOT NULL,
    name              TEXT NOT NULL,
    PRIMARY KEY (code, year)
);

CREATE TABLE Population (
    lga_code          INTEGER NOT NULL,
    lga_year          INTEGER NOT NULL,
    indigenous_status TEXT NOT NULL,
    sex               CHAR (1) NOT NULL,
    age_category      TEXT NOT NULL,
    count             INTEGER NOT NULL,
    age_min           INTEGER,
    age_max           INTEGER,
    PRIMARY KEY (lga_code, lga_year, indigenous_status, sex, age_category)
    FOREIGN KEY (lga_code) REFERENCES LGA(lga_code)
    FOREIGN KEY (lga_year) REFERENCES LGA(lga_year)
);

CREATE TABLE State (
    StateCode       INTEGER NOT NULL,
    StateName       TEXT NOT NULL,
    abbreviation    TEXT NOT NULL,
    PRIMARY KEY (StateCode)
);

CREATE TABLE TeamMember (
    MemberID        TEXT NOT NULL,
    FirstName       TEXT NOT NULL,
    LastName        TEXT NOT NULL,
    PRIMARY KEY (mmbID)
);

CREATE TABLE Persona (
    PerName         TEXT NOT NULL,
    PerImagePath    TEXT NOT NULL,
    PRIMARY KEY (PerName)
);

CREATE TABLE PersonaAttribute (
    ID              INTEGER NOT NULL,
    AttributeType   TEXT NOT NULL,
    Description     TEXT NOT NULL,
    PRIMARY KEY (ID)

);

