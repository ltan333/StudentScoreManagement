# StudentScoreManagement
Project build Netbeans13 / JDK 17.0.1 / SDK 18 
# SETUP DB
Set DB name "TestDB"  
  
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='students' and xtype='U')   
    CREATE TABLE students (  
		rollnumber varchar(10) PRIMARY KEY,  
    name varchar(50) not null,  
		birthday varchar(15) not null,  
		class varchar(50) not null,  
		major varchar(50) not null,  
		gender int not null,  
		email varchar(50) not null,  
		phone varchar(12) not null  
    )  
GO  
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='courses' and xtype='U')  
    CREATE TABLE courses (  
		codeCourse varchar(10) PRIMARY KEY,  
		name varchar(50) not null  
    )  
GO  

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='score' and xtype='U')  
    CREATE TABLE score (  
		codeCourse varchar(10) not null,  
		rollnumber varchar(10) not null,  
		testScore float,  
		midleExamScore float,  
		finalExamScore float,  
		FOREIGN KEY (codeCourse) REFERENCES courses(codeCourse),  
		FOREIGN KEY (rollnumber) REFERENCES students(rollnumber),  
		PRIMARY KEY (codeCourse,rollnumber)  
    )  
GO  
# Ngan200
