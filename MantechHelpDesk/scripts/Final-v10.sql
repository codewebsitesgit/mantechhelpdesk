USE [bie40d12_MantechHelpdesk]
GO
DROP TABLE [dbo].[Complaint];
DROP TABLE [dbo].[ComplaintCategory];
DROP TABLE [dbo].[ComplaintPriority];
DROP TABLE [dbo].[ComplaintStatus];
DROP TABLE [dbo].[ArticleRate];
DROP TABLE [dbo].[Article];
DROP TABLE [dbo].[Rate];
DROP TABLE [dbo].[Faqs];
DROP TABLE [dbo].[UserAccount];
DROP TABLE [dbo].[Department];
DROP TABLE [dbo].[UserRole];
GO
CREATE TABLE [dbo].[Article](
	[ArticleID] [int] NOT NULL,
	[ArticleSubject] [nvarchar](150) NOT NULL,
	[ArticleContents] [nvarchar](1000) NOT NULL,
	[CreationDate] [datetime] NOT NULL,
	[Status] [bit] NOT NULL,
	[ArticleOwner] [int] NOT NULL,
 CONSTRAINT [PK_Article] PRIMARY KEY CLUSTERED 
(
	[ArticleID] ASC
))
GO
CREATE TABLE [dbo].[ArticleRate](
	[ArticleID] [int] NOT NULL,
	[RateID] [int] NOT NULL,
	[CreationDate] [datetime] NOT NULL,
	[RateOwner] [int] NOT NULL,
 CONSTRAINT [PK_ArticleRate] PRIMARY KEY CLUSTERED 
(
	[ArticleID] ASC,
	[RateID] ASC
))
GO
CREATE TABLE [dbo].[Complaint](
	[ComplaintID] [int] NOT NULL,
	[ComplaintSubject] [nvarchar](150) NOT NULL,
	[ComplaintCategory] [int] NOT NULL,
	[Priority] [int] NOT NULL,
	[ComplaintContents] [nvarchar](1000) NOT NULL,
	[Status] [int] NOT NULL,
	[ComplaintOwner] [int] NOT NULL,
	[LodgingDate] [datetime] NOT NULL,
	[ClosingDate] [datetime] NULL,
	[Technician] [int] NULL,
 CONSTRAINT [PK_Complaint] PRIMARY KEY CLUSTERED 
(
	[ComplaintID] ASC
))
GO
CREATE TABLE [dbo].[ComplaintCategory](
	[CategoryID] [int] NOT NULL,
	[CategoryName] [nvarchar](50) NOT NULL,
	[CategoryDesc] [nvarchar](200) NOT NULL,
 CONSTRAINT [PK_ComplaintCategory] PRIMARY KEY CLUSTERED 
(
	[CategoryID] ASC
))
GO
CREATE TABLE [dbo].[ComplaintPriority](
	[PriorityID] [int] NOT NULL,
	[PriorityName] [nvarchar](50) NOT NULL,
	[PriorityDesc] [nvarchar](200) NOT NULL,
 CONSTRAINT [PK_ComplaintPriority] PRIMARY KEY CLUSTERED 
(
	[PriorityID] ASC
))
GO
CREATE TABLE [dbo].[ComplaintStatus](
	[StatusID] [int] NOT NULL,
	[StatusName] [nvarchar](50) NOT NULL,
	[StatusDesc] [nvarchar](200) NOT NULL,
 CONSTRAINT [PK_ComplaintStatus] PRIMARY KEY CLUSTERED 
(
	[StatusID] ASC
))
GO
CREATE TABLE [dbo].[Department](
	[DepartmentID] [int] NOT NULL,
	[DepartmentName] [nvarchar](50) NOT NULL,
	[DepartmentDesc] [nvarchar](200) NOT NULL,
 CONSTRAINT [PK_Department] PRIMARY KEY CLUSTERED 
(
	[DepartmentID] ASC
))
GO
CREATE TABLE [dbo].[Faqs](
	[FaqID] [int] NOT NULL,
	[FaqSubject] [nvarchar](150) NOT NULL,
	[FaqContents] [nvarchar](1000) NOT NULL,
	[CreationDate] [datetime] NOT NULL,
	[Status] [bit] NOT NULL,
 CONSTRAINT [PK_Faqs] PRIMARY KEY CLUSTERED 
(
	[FaqID] ASC
))
GO
CREATE TABLE [dbo].[Rate](
	[RateID] [int] NOT NULL,
	[RateName] [nvarchar](50) NOT NULL,
	[RateDesc] [nvarchar](200) NOT NULL,
 CONSTRAINT [PK_Rate] PRIMARY KEY CLUSTERED 
(
	[RateID] ASC
))
GO
CREATE TABLE [dbo].[UserAccount](
	[AccountID] [int] NOT NULL,
	[Username] [nvarchar](50) NOT NULL,
	[Password] [varchar](150) NOT NULL,
	[DepartmentID] [int] NOT NULL,
	[RoleID] [int] NOT NULL,
	[Name] [nvarchar](100) NOT NULL,
	[Birthday] [date] NOT NULL,
	[Gender] [smallint] NOT NULL,
	[Address] [nvarchar](200) NOT NULL,
	[Phone] [nvarchar](20) NOT NULL,
	[Email] [nvarchar](100) NOT NULL,
	[Status] [bit] NOT NULL,
 CONSTRAINT [PK_UserAccount] PRIMARY KEY CLUSTERED 
(
	[AccountID] ASC
))
GO
CREATE TABLE [dbo].[UserRole](
	[RoleID] [int] NOT NULL,
	[RoleName] [nvarchar](50) NOT NULL,
	[RoleDesc] [nvarchar](200) NOT NULL,
 CONSTRAINT [PK_UserRole] PRIMARY KEY CLUSTERED 
(
	[RoleID] ASC
))
GO
/****** Object:  Index [Unique_CategoryName]    Script Date: 4/7/2014 9:18:35 PM ******/
CREATE UNIQUE NONCLUSTERED INDEX [Unique_CategoryName] ON [dbo].[ComplaintCategory]
(
	[CategoryName] ASC
)
GO
/****** Object:  Index [Unique_PriorityName]    Script Date: 4/7/2014 9:18:35 PM ******/
CREATE UNIQUE NONCLUSTERED INDEX [Unique_PriorityName] ON [dbo].[ComplaintPriority]
(
	[PriorityName] ASC
)
GO
/****** Object:  Index [Unique_StatusName]    Script Date: 4/7/2014 9:18:35 PM ******/
CREATE UNIQUE NONCLUSTERED INDEX [Unique_StatusName] ON [dbo].[ComplaintStatus]
(
	[StatusName] ASC
)
GO
/****** Object:  Index [Unique_DepartmentName]    Script Date: 4/7/2014 9:18:35 PM ******/
CREATE UNIQUE NONCLUSTERED INDEX [Unique_DepartmentName] ON [dbo].[Department]
(
	[DepartmentName] ASC
)
GO
/****** Object:  Index [Unique_RateName]    Script Date: 4/7/2014 9:18:35 PM ******/
CREATE UNIQUE NONCLUSTERED INDEX [Unique_RateName] ON [dbo].[Rate]
(
	[RateName] ASC
)
GO
/****** Object:  Index [Unique_UserName]    Script Date: 4/7/2014 9:18:35 PM ******/
CREATE UNIQUE NONCLUSTERED INDEX [Unique_UserName] ON [dbo].[UserAccount]
(
	[Username] ASC
)
GO
/****** Object:  Index [Unique_RoleName]    Script Date: 4/7/2014 9:18:35 PM ******/
CREATE UNIQUE NONCLUSTERED INDEX [Unique_RoleName] ON [dbo].[UserRole]
(
	[RoleName] ASC
)
GO
ALTER TABLE [dbo].[Article]  WITH CHECK ADD  CONSTRAINT [FK_Article_UserAccount] FOREIGN KEY([ArticleOwner])
REFERENCES [dbo].[UserAccount] ([AccountID])
GO
ALTER TABLE [dbo].[Article] CHECK CONSTRAINT [FK_Article_UserAccount]
GO
ALTER TABLE [dbo].[ArticleRate]  WITH CHECK ADD  CONSTRAINT [FK_ArticleRate_Article] FOREIGN KEY([ArticleID])
REFERENCES [dbo].[Article] ([ArticleID])
GO
ALTER TABLE [dbo].[ArticleRate] CHECK CONSTRAINT [FK_ArticleRate_Article]
GO
ALTER TABLE [dbo].[ArticleRate]  WITH CHECK ADD  CONSTRAINT [FK_ArticleRate_Rate] FOREIGN KEY([RateID])
REFERENCES [dbo].[Rate] ([RateID])
GO
ALTER TABLE [dbo].[ArticleRate] CHECK CONSTRAINT [FK_ArticleRate_Rate]
GO
ALTER TABLE [dbo].[Complaint]  WITH CHECK ADD  CONSTRAINT [FK_Complaint_ComplaintCategory] FOREIGN KEY([ComplaintCategory])
REFERENCES [dbo].[ComplaintCategory] ([CategoryID])
GO
ALTER TABLE [dbo].[Complaint] CHECK CONSTRAINT [FK_Complaint_ComplaintCategory]
GO
ALTER TABLE [dbo].[Complaint]  WITH CHECK ADD  CONSTRAINT [FK_Complaint_ComplaintPriority] FOREIGN KEY([Priority])
REFERENCES [dbo].[ComplaintPriority] ([PriorityID])
GO
ALTER TABLE [dbo].[Complaint] CHECK CONSTRAINT [FK_Complaint_ComplaintPriority]
GO
ALTER TABLE [dbo].[Complaint]  WITH CHECK ADD  CONSTRAINT [FK_Complaint_ComplaintStatus] FOREIGN KEY([Status])
REFERENCES [dbo].[ComplaintStatus] ([StatusID])
GO
ALTER TABLE [dbo].[Complaint] CHECK CONSTRAINT [FK_Complaint_ComplaintStatus]
GO
ALTER TABLE [dbo].[Complaint]  WITH CHECK ADD  CONSTRAINT [FK_Complaint_UserAccount] FOREIGN KEY([ComplaintOwner])
REFERENCES [dbo].[UserAccount] ([AccountID])
GO
ALTER TABLE [dbo].[Complaint] CHECK CONSTRAINT [FK_Complaint_UserAccount]
GO
ALTER TABLE [dbo].[Complaint]  WITH CHECK ADD  CONSTRAINT [FK_Complaint_UserAccount1] FOREIGN KEY([Technician])
REFERENCES [dbo].[UserAccount] ([AccountID])
GO
ALTER TABLE [dbo].[Complaint] CHECK CONSTRAINT [FK_Complaint_UserAccount1]
GO
ALTER TABLE [dbo].[UserAccount]  WITH CHECK ADD  CONSTRAINT [FK_UserAccount_Department] FOREIGN KEY([DepartmentID])
REFERENCES [dbo].[Department] ([DepartmentID])
GO
ALTER TABLE [dbo].[UserAccount] CHECK CONSTRAINT [FK_UserAccount_Department]
GO
ALTER TABLE [dbo].[UserAccount]  WITH CHECK ADD  CONSTRAINT [FK_UserAccount_UserRole] FOREIGN KEY([RoleID])
REFERENCES [dbo].[UserRole] ([RoleID])
GO
ALTER TABLE [dbo].[UserAccount] CHECK CONSTRAINT [FK_UserAccount_UserRole]

GO
INSERT INTO [dbo].[Rate] VALUES
(1,'Level 1','Poor'),
(2,'Level 2','Satisfied'),
(3,'Level 3','Good'),
(4,'Level 4','Very good'),
(5,'Level 5','Excellent');
GO
INSERT INTO [dbo].[ComplaintStatus] VALUES
(1,'Pending','The complaint is pending and is not ready to be resolved'),
(2,'Resolving','The complaint is being processed and resolved'),
(3,'Done','The complaint is resolved');

GO
INSERT INTO [dbo].[ComplaintPriority] VALUES
(1,'High','High priority and needs to be resolved first'),
(2,'Normal','Normal priority, the default priority of the complaint'),
(3,'Low','Low priority');
GO
INSERT INTO [dbo].[ComplaintCategory] VALUES
(1,'Hard Disk Failure','Hard Disk Failure'),
(2,'Floppy Disk Failure','Floppy Disk Failure');

INSERT INTO [dbo].[Department] VALUES 
(1, 'Educational Services','Educational Services'),
(2, 'Management Services','Management Services'),
(3, 'Learning Services','Learning Services'),
(4, 'Internal Systems','Internal Systems'),
(5, 'Human Resources','Human Resources');

INSERT INTO [dbo].[UserRole] VALUES
(1, 'admin', 'administrator'),
(2, 'technician', 'technician user'),
(3, 'user', 'registered user');

INSERT INTO [dbo].[UserAccount] VALUES
(1, 'truong','1',1,1,'Le Quoc Truong','1989-01-11',1,'Ho Chi Minh','0909686868','codewebsites@gmail.com',1),
(2, 'truong2','1',1,2,'Le Quoc Truong','1989-01-11',1,'Ho Chi Minh','0909686868','codewebsites@gmail.com',1),
(3, 'anh','1',2,3,'Nguyen Ngoc Anh','1989-01-11',1,'Ho Chi Minh','0909686868','codewebsites@gmail.com',1),
(4, 'anh2','1',2,2,'Nguyen Ngoc Anh','1989-01-11',1,'Ho Chi Minh','0909686868','codewebsites@gmail.com',1),
(5, 'bao','1',3,3,'Hoang Hoai Bao','1980-01-11',1,'Ho Chi Minh','0909686868','codewebsites@gmail.com',1),
(6, 'bao2','1',3,2,'Hoang Hoai Bao','1980-01-11',1,'Ho Chi Minh','0909686868','codewebsites@gmail.com',1),
(7, 'hao','1',4,3,'Nguyen Duong Kim Hao','1990-01-11',1,'Ho Chi Minh','0909686868','codewebsites@gmail.com',1),
(8, 'hao2','1',4,2,'Nguyen Duong Kim Hao','1990-01-11',1,'Ho Chi Minh','0909686868','codewebsites@gmail.com',1),
(9, 'tech','1',5,2,'Nguyen Others','1989-01-11',1,'Ho Chi Minh','0909686868','codewebsites@gmail.com',1);
