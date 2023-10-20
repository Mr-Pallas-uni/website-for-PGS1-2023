PRAGMA foreign_keys = OFF;
drop table if exists LGA;
drop table if exists Population;
drop table if exists StateTerritory;
drop table if exists TeamMember;
drop table if exists Persona;
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

CREATE TABLE StateTerritory (
    StCode          INTEGER NOT NULL,
    StName            TEXT NOT NULL,
    abbreviation    TEXT NOT NULL,
    PRIMARY KEY (StCode)
);

CREATE TABLE TeamMember (
    mmbID           TEXT NOT NULL,
    firstName       TEXT NOT NULL,
    lastName        TEXT NOT NULL,
    PRIMARY KEY (mmbID)
);

CREATE TABLE Persona (
    perID           INTEGER NOT NULL,
    perName         TEXT NOT NULL,
    perBackground   TEXT NOT NULL,
    perNeed        TEXT NOT NULL,
    perGoal        TEXT NOT NULL,
    perSkillExp     TEXT NOT NULL,
    perImagePath    TEXT NOT NULL,
    PRIMARY KEY (perID)
);

