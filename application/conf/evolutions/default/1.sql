#up
USE tsm;
CREATE TABLE users(
  userId VARCHAR(20) PRIMARY KEY NOT NULL,
  email VARCHAR(100)  NOT NULL UNIQUE,
  password VARCHAR(300) NOT NULL,
  entryCompanyDate DATETIME,
  userRole VARCHAR(10) NOT NULL,
  department VARCHAR(50),
  annualLeave FLOAT(5,2) DEFAULT 12,
  userStatus VARCHAR(20) NOT NULL
)
#down
DROP TABLE users;