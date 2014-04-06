USE [bie40d12_MantechHelpdesk]
GO
/****** Object:  Table [dbo].[Department]    Script Date: 4/6/2014 10:43:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Department](
	[DepartmentID] [int] NOT NULL,
	[DepartmentName] [nvarchar](50) NOT NULL,
	[DepartmentDesc] [nvarchar](150) NULL,
 CONSTRAINT [PK_Department] PRIMARY KEY CLUSTERED 
(
	[DepartmentID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[UserAccount]    Script Date: 4/6/2014 10:43:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
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
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[UserRole]    Script Date: 4/6/2014 10:43:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UserRole](
	[RoleID] [int] NOT NULL,
	[RoleName] [nvarchar](50) NULL,
	[RoleDesc] [nvarchar](150) NULL,
 CONSTRAINT [PK_UserRole] PRIMARY KEY CLUSTERED 
(
	[RoleID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [Unique_DepartmentName]    Script Date: 4/6/2014 10:43:12 AM ******/
CREATE UNIQUE NONCLUSTERED INDEX [Unique_DepartmentName] ON [dbo].[Department]
(
	[DepartmentName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [Unique_UserName]    Script Date: 4/6/2014 10:43:12 AM ******/
CREATE UNIQUE NONCLUSTERED INDEX [Unique_UserName] ON [dbo].[UserAccount]
(
	[Username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [Unique_RoleName]    Script Date: 4/6/2014 10:43:12 AM ******/
CREATE UNIQUE NONCLUSTERED INDEX [Unique_RoleName] ON [dbo].[UserRole]
(
	[RoleName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
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
INSERT INTO [dbo].[Department] VALUES 
(1, 'Educational Services','Educational Services'),
(2, 'Management Services','Management Services'),
(3, 'Learning Services','Learning Services'),
(4, 'Internal Systems','Internal Systems'),
(5, 'Human Resources','Human Resources');

INSERT INTO [dbo].[UserRole] VALUES
(1, 'admin', 'administrator'),
(2, 'user', 'registered user');

INSERT INTO [dbo].[UserAccount] VALUES
(1, 'truong','1',1,1,'Le Quoc Truong','1989-01-11',1,'Ho Chi Minh','0909686868','codewebsites@gmail.com',1),
(2, 'truong2','1',1,2,'Le Quoc Truong','1989-01-11',1,'Ho Chi Minh','0909686868','codewebsites@gmail.com',1),
(3, 'anh','1',2,2,'Nguyen Ngoc Anh','1989-01-11',1,'Ho Chi Minh','0909686868','codewebsites@gmail.com',1),
(4, 'bao','1',3,2,'Hoang Hoai Bao','1980-01-11',1,'Ho Chi Minh','0909686868','codewebsites@gmail.com',1),
(5, 'hao','1',4,2,'Nguyen Duong Kim Hao','1990-01-11',1,'Ho Chi Minh','0909686868','codewebsites@gmail.com',1),
(6, 'hao2','1',5,2,'Nguyen Ngoc Anh','1989-01-11',1,'Ho Chi Minh','0909686868','codewebsites@gmail.com',1);
