PRAGMA foreign_keys = OFF;
drop table if exists LGAs;
drop table if exists Population;
drop table if exists States;
drop table if exists TeamMembers;
drop table if exists Personas;
drop table if exists PersonaAttributes;
drop table if exists Demographics;
drop table if exists LTHCs;
drop table if exists NonSchoolCompletion;

PRAGMA foreign_keys = ON;

CREATE TABLE LGAs (
    lga_code          INTEGER NOT NULL,
    lga_year          INTEGER NOT NULL,
    name              TEXT NOT NULL,
    StateCode         INTEGER NOT NULL,
    PRIMARY KEY (lga_code, lga_year)
);

CREATE TABLE States (
    StateCode       INTEGER NOT NULL,
    StateName       TEXT NOT NULL,
    abbreviation    TEXT NOT NULL,
    PRIMARY KEY (StateCode)
);

CREATE TABLE TeamMembers (
    MemberID        TEXT NOT NULL,
    FirstName       TEXT NOT NULL,
    LastName        TEXT NOT NULL,
    PRIMARY KEY (MemberID)
);

CREATE TABLE Personas (
    PerName         TEXT NOT NULL,
    PerImagePath    TEXT NOT NULL,
    PerImageCredit  TEXT,
    PRIMARY KEY (PerName)
);

CREATE TABLE PersonaAttributes (
    ID              INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    PerName         TEXT NOT NULL,
    AttributeType   TEXT NOT NULL,
    Description     TEXT NOT NULL,
    FOREIGN KEY (PerName) REFERENCES Personas(PerName)
);

CREATE TABLE Demographics (
    lga_code          INTEGER NOT NULL,
    lga_year          INTEGER NOT NULL,
    indigenous_status TEXT NOT NULL,
    sex               CHAR (1) NOT NULL,
    PRIMARY KEY (lga_code, lga_year, indigenous_status, sex)
    FOREIGN KEY (lga_code, lga_year) REFERENCES LGAs(code, year)
);

CREATE TABLE LTHCs (
    lga_code          INTEGER NOT NULL,
    lga_year          INTEGER NOT NULL,
    indigenous_status TEXT NOT NULL,
    sex               CHAR (1) NOT NULL,
    condition         TEXT NOT NULL,
    count             INTEGER NOT NULL,
    PRIMARY KEY (lga_code, lga_year, indigenous_status, sex, condition)
    FOREIGN KEY (lga_code, lga_year, indigenous_status, sex) REFERENCES Demographics(lga_code, lga_year, indigenous_status, sex)  
);

CREATE TABLE NonSchoolCompletion (
    lga_code          INTEGER NOT NULL,
    lga_year          INTEGER NOT NULL,
    indigenous_status TEXT NOT NULL,
    sex               CHAR (1) NOT NULL,
    nonSchoolBracket  TEXT NOT NULL,
    count             INTEGER NOT NULL,
    PRIMARY KEY (lga_code, lga_year, indigenous_status, sex, nonSchoolBracket)
    FOREIGN KEY (lga_code, lga_year, indigenous_status, sex) REFERENCES Demographics(lga_code, lga_year, indigenous_status, sex)  
);

CREATE TABLE SchoolCompletion (
    lga_code          INTEGER NOT NULL,
    lga_year          INTEGER NOT NULL,
    indigenous_status TEXT NOT NULL,
    sex               CHAR (1) NOT NULL,
    SchoolCompletion  TEXT NOT NULL,
    count             INTEGER NOT NULL,
    PRIMARY KEY (lga_code, lga_year, indigenous_status, sex, SchoolCompletion)
    FOREIGN KEY (lga_code, lga_year, indigenous_status, sex) REFERENCES Demographics(lga_code, lga_year, indigenous_status, sex)
);

CREATE TABLE Population (
    lga_code          INTEGER NOT NULL,
    lga_year          INTEGER NOT NULL,
    indigenous_status TEXT NOT NULL,
    sex               CHAR (1) NOT NULL,
    age_min           INTEGER NOT NULL,
    age_max           INTEGER NOT NULL,
    count             INTEGER NOT NULL,
    PRIMARY KEY (lga_code, lga_year, indigenous_status, sex, age_category)
    FOREIGN KEY (lga_code, lga_year, indigenous_status, sex) REFERENCES Demographics(lga_code, lga_year, indigenous_status, sex)
);

