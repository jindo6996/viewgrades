#up
CREATE TABLE subjects (
  code VARCHAR(10) PRIMARY KEY NOT NULL,
  name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE years(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  year VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE semesters(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  semester VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE  grades(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  file VARCHAR(200) UNIQUE,
  code_sub VARCHAR(10) ,
  id_year INT,
  id_semester INT,
  FOREIGN KEY (code_sub) REFERENCES subjects(code),
  FOREIGN KEY (id_year) REFERENCES years(id),
  FOREIGN KEY (id_semester) REFERENCES semesters(id)
);

CREATE TABLE classes(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  id_user VARCHAR(20),
  id_grade INT,
  FOREIGN KEY (id_user) REFERENCES users(userId),
  FOREIGN KEY (id_grade) REFERENCES grades(id)
);
ALTER TABLE grades ADD COLUMN upload_at TIMESTAMP ;
-- Down

DROP TABLE classes;
DROP TABLE grades;
DROP TABLE subjects;
DROP TABLE semesters;
DROP TABLE years;
