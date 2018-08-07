#up
CREATE TABLE subjects (
  code VARCHAR(10) PRIMARY KEY NOT NULL,
  name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE years(
  id INT PRIMARY KEY NOT NULL,
  year VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE semesters(
  id INT PRIMARY KEY NOT NULL,
  semester VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE  grades(
  id INT PRIMARY KEY NOT NULL,
  file VARCHAR(200) UNIQUE,
  code_sub VARCHAR(10) ,
  id_year INT,
  id_semester INT,
  FOREIGN KEY (code_sub) REFERENCES subjects(code),
  FOREIGN KEY (id_year) REFERENCES years(id),
  FOREIGN KEY (id_semester) REFERENCES semesters(id)
);