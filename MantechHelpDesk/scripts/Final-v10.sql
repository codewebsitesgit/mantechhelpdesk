USE [bie40d12_MantechHelpdesk]
GO
DROP TABLE [dbo].[ComplaintHistory];
DROP TABLE [dbo].[ComplaintImages];
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
DROP TABLE [dbo].[Activity];
GO
CREATE TABLE [dbo].[Article](
	[ArticleID] [int] NOT NULL,
	[ArticleSubject] [nvarchar](150) NOT NULL,
	[ArticleContents] [nvarchar](1000) NOT NULL,
	[CreationDate] [datetime] NOT NULL,
	[Status] [bit] NOT NULL,
	[ArticleOwner] [int] NOT NULL,
        [ImageLocation] [nvarchar](350) NULL,
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
	[ComplaintContents] [nvarchar](1000) NULL,
	[Status] [int] NOT NULL,
	[ComplaintOwner] [int] NOT NULL,
	[LodgingDate] [datetime] NOT NULL,
	[ClosingDate] [datetime] NULL,
	[Technician] [int] NULL,
        [Reasons] [nvarchar](300) NULL, 
        [LastModified] [datetime] NOT NULL,
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
CREATE TABLE [dbo].[Activity](
	[ActionID] [int] NOT NULL,
	[ActionDesc] [nvarchar](150) NULL,
 CONSTRAINT [PK_Activity] PRIMARY KEY CLUSTERED 
(
	[ActionID] ASC
))
GO
CREATE TABLE [dbo].[ComplaintHistory](
	[HistoryID] [int] NOT NULL,
	[UserAccountID] [int] NOT NULL,
	[ComplaintID] [int] NOT NULL,
	[ActionID] [int] NOT NULL,
	[LastModifiedDate] [datetime] NOT NULL,
        [Details] [nvarchar](300) NULL,
 CONSTRAINT [PK_ComplaintHistory] PRIMARY KEY CLUSTERED 
(
	[HistoryID] ASC
))
GO
CREATE TABLE [dbo].[ComplaintImages](
	[ComplaintImageID] [int] NOT NULL,
	[ComplaintID] [int] NOT NULL,
	[ImageLocation] [nvarchar](350) NOT NULL,
	[CreationDate] [datetime] NOT NULL,
 CONSTRAINT [PK_ComplaintImages] PRIMARY KEY CLUSTERED 
(
	[ComplaintImageID] ASC
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
ALTER TABLE [dbo].[ComplaintHistory]  WITH CHECK ADD  CONSTRAINT [FK_ComplaintHistory_Activity] FOREIGN KEY([ActionID])
REFERENCES [dbo].[Activity] ([ActionID])
GO
ALTER TABLE [dbo].[ComplaintHistory] CHECK CONSTRAINT [FK_ComplaintHistory_Activity]
GO
ALTER TABLE [dbo].[ComplaintHistory]  WITH CHECK ADD  CONSTRAINT [FK_ComplaintHistory_Complaint] FOREIGN KEY([ComplaintID])
REFERENCES [dbo].[Complaint] ([ComplaintID])
GO
ALTER TABLE [dbo].[ComplaintHistory] CHECK CONSTRAINT [FK_ComplaintHistory_Complaint]
GO
ALTER TABLE [dbo].[ComplaintHistory]  WITH CHECK ADD  CONSTRAINT [FK_ComplaintHistory_UserAccount] FOREIGN KEY([UserAccountID])
REFERENCES [dbo].[UserAccount] ([AccountID])
GO
ALTER TABLE [dbo].[ComplaintHistory] CHECK CONSTRAINT [FK_ComplaintHistory_UserAccount]
GO
ALTER TABLE [dbo].[ComplaintImages]  WITH CHECK ADD  CONSTRAINT [FK_ComplaintImages_Complaint] FOREIGN KEY([ComplaintID])
REFERENCES [dbo].[Complaint] ([ComplaintID])
GO
ALTER TABLE [dbo].[ComplaintImages] CHECK CONSTRAINT [FK_ComplaintImages_Complaint]
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
(3,'Handing','The complaint was handing and need the right solution. The technician cannot solve it normaly.'),
(4,'Done','The complaint is resolved');

GO
INSERT INTO [dbo].[ComplaintPriority] VALUES
(1,'High','High priority and needs to be resolved first'),
(2,'Normal','Normal priority, the default priority of the complaint'),
(3,'Low','Low priority');
GO
INSERT INTO [dbo].[ComplaintCategory] VALUES
(1,'Hardware Failure','Hardware Failure'),
(2,'Software Failure','Software Failure'),
(3,'Unknown','Unknown problem');

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

/*'User'/'password' = 'User'/'1'*/
INSERT INTO [dbo].[UserAccount] VALUES
(1, 'truong','c4ca4238a0b923820dcc509a6f75849b',1,1,'Le Quoc Truong','1989-01-11',1,'Ho Chi Minh','0909686868','codewebsites@gmail.com',1),
(2, 'truong2','c4ca4238a0b923820dcc509a6f75849b',1,2,'Le Quoc Truong','1989-01-11',1,'Ho Chi Minh','0909686868','codewebsites@gmail.com',1),
(3, 'anh','c4ca4238a0b923820dcc509a6f75849b',2,3,'Nguyen Ngoc Anh','1989-01-11',1,'Ho Chi Minh','0909686868','codewebsites@gmail.com',1),
(4, 'anh2','c4ca4238a0b923820dcc509a6f75849b',2,2,'Nguyen Ngoc Anh','1989-01-11',1,'Ho Chi Minh','0909686868','codewebsites@gmail.com',1),
(5, 'bao','c4ca4238a0b923820dcc509a6f75849b',3,3,'Hoang Hoai Bao','1980-01-11',1,'Ho Chi Minh','0909686868','codewebsites@gmail.com',1),
(6, 'bao2','c4ca4238a0b923820dcc509a6f75849b',3,2,'Hoang Hoai Bao','1980-01-11',1,'Ho Chi Minh','0909686868','codewebsites@gmail.com',1),
(7, 'hao','c4ca4238a0b923820dcc509a6f75849b',4,3,'Nguyen Duong Kim Hao','1990-01-11',1,'Ho Chi Minh','0909686868','codewebsites@gmail.com',1),
(8, 'hao2','c4ca4238a0b923820dcc509a6f75849b',4,2,'Nguyen Duong Kim Hao','1990-01-11',1,'Ho Chi Minh','0909686868','codewebsites@gmail.com',1),
(9, 'tech','c4ca4238a0b923820dcc509a6f75849b',5,2,'Nguyen Others','1989-01-11',1,'Ho Chi Minh','0909686868','codewebsites@gmail.com',1);

INSERT INTO [dbo].[Activity] VALUES
(1,'Re-sends complaint'),
(2,'Changes complaints status'),
(3,'Creates new complaint'),
(4,'Updates complaint root cause'),
(5,'Changes complaints priority'),
(6,'Assigns technician'),
(7,'Changes complaints category');

INSERT INTO [dbo].[Faqs] VALUES
(1, 'How do I double space in Microsoft Word?', 
'1.Open Microsoft Word and the document you want to modify.
2.Highlight the text you want to have double spaced or select all.
3.Right-click the selected text and click Format. and then Paragraph or just Paragraph depending on your version of Word.
4.In the Paragraph window under spacing change from Single to Double and then click Ok. Below is an example of what this window may look like while in Microsoft Word.'
,'2014-04-13 21:02:42.227', 
1);
INSERT INTO [dbo].[Faqs] VALUES
(2, 'How do I get into Windows Safe Mode ?', 
'To get into the Windows Vista and Windows 7 Safe Mode, as the computer is booting press and hold your "F8 Key" which should bring up the "Windows Advanced Options Menu" as shown below. Use your arrow keys to move to "Safe Mode" and press your Enter key'
,'2014-04-11 20:02:42.227', 
1);
INSERT INTO [dbo].[Faqs] VALUES
(3, 'My computer is running slow what steps can I do to fix it ?', 
'If your computer has not been rebooted recently make sure to reboot it before following any of the steps below.
1. Background programs.
2. Free hard drive space.
3. Bad, corrupted or fragmented hard drive.
4. Update your drivers.'
,'2014-04-12 06:03:42.227', 
1);
INSERT INTO [dbo].[Faqs] VALUES
(4, 'How to erase my hard drive and start over ?', 
'1. Place the bootable diskette or disc inside the computer and reboot to get to the DOS prompt. In some cases you may need to boot the computer with CD-ROM support or press a key to boot from the disk.
2. At the MS-DOS prompt type format c: /s
3. The computer will ask you "Are you sure you want to erase everything on drive c:" if you are ok with doing this press "Y" for yes and press enter'
,'2014-04-08 05:17:42.227', 
1);
INSERT INTO [dbo].[Faqs] VALUES
(5, 'How do I password protect my files and folders in Windows ?', 
'1.Select the folder you want to encrypt.
2.Right-click the folder and click Properties.
3.Click the Advanced button.
4.Check "Encrypt contents to secure data" option.
5.Click Apply and then Ok.'
,'2014-04-09 10:08:42.227', 
1);

INSERT [dbo].[Article] ([ArticleID], [ArticleSubject], [ArticleContents], [CreationDate], [Status], [ArticleOwner], [ImageLocation]) VALUES (1, N'Damaged Virtual Memory Manager', N'This behavior may occur if either of the following
		  conditions exist: 
		  <ul><li>One or more of the random access memory (RAM) modules that
				are installed in your computer are faulty, or the memory modules are not
				compatible with the chip set on your computer mainboard. <br></li><li>The Page file that is used by the Virtual Memory Manager
				may be damaged.</li></ul><p><span style="font-weight: bold;">Resolutions</span>:<br></p><ol><li>Click <strong class="uiterm">Start</strong>, right-click <strong class="uiterm">My
				Computer</strong>, and then click <strong class="uiterm">Properties</strong>.</li><li>Click the <strong class="uiterm">Advanced</strong> tab.</li><li>Under <strong class="uiterm">Performance</strong>, click
				<strong class="uiterm">Settings</strong>.</li><li>Click the <strong class="uiterm">Advanced</strong> tab.<br></li></ol>', CAST(0x0000A31E010B6A12 AS DateTime), 1, 9, N'upload/article/ram_error_article.png')
INSERT [dbo].[Article] ([ArticleID], [ArticleSubject], [ArticleContents], [CreationDate], [Status], [ArticleOwner], [ImageLocation]) VALUES (2, N'3 steps to install Microsoft word', N'<b class="whb">1. Purchase a key.</b> When you 
buy a copy of Microsoft Office, you are actually purchasing a product 
key. This key will allow you to download your Office install files. You 
can purchase a key directly from Microsoft, or in a box from a store.<br><span style="font-weight: bold;">2. </span><b class="whb">Download your Office install file.</b> Once you have your product key, you can download your copy of Office at the <a rel="nofollow" class="external text" href="http://office.microsoft.com/getoffice">Microsoft website</a>. You will need to enter your key to access the downloads.<br><span style="font-weight: bold;">3. </span><b class="whb">Start the install.</b> If you have a DVD insert it and 
the installation option should appear automatically. If you downloaded 
Office, run the file that downloaded and your installation will begin.', CAST(0x0000A31E010D213D AS DateTime), 1, 9, N'upload/article/microsoft_office.png')
INSERT [dbo].[Article] ([ArticleID], [ArticleSubject], [ArticleContents], [CreationDate], [Status], [ArticleOwner], [ImageLocation]) VALUES (3, N'How to install printer HP-LASER-JETP1007', N'<span style="font-weight: bold;">Local printers</span><br>The most common way to install a printer is to connect it  directly to your computer. This is known as a <span class="newTerm">local printer</span>.If your printer is a universal serial bus (USB) 
model, Windows should automatically detect it and begin installation 
when you plug it in.<br><span style="font-weight: bold;"><br>Network printers</span><br>In the workplace, many printers are <span class="newTerm">network printers</span>.
 These connect directly to a network as stand-alone devices. Inexpensive
 network printers are also made for the home. To learn more, see <a href="http://windows.microsoft.com/en-us/windows7/install-a-printer-on-a-home-network" class="navigationLink" data-id="pageContainer0_ID0EBDBHBA">Install a printer on a home network</a>.<br><span style="font-weight: bold;"><br>Removing a printer</span><br>If you no longer use a printer, you can remove it from Devices and Printers.<br>', CAST(0x0000A31E010F2BD6 AS DateTime), 1, 9, N'upload/article/HP-LASER-JETP1007.jpg')
INSERT [dbo].[ArticleRate] ([ArticleID], [RateID], [CreationDate], [RateOwner]) VALUES (1, 1, CAST(0x0000A31E0110434D AS DateTime), 7)
INSERT [dbo].[ArticleRate] ([ArticleID], [RateID], [CreationDate], [RateOwner]) VALUES (1, 5, CAST(0x0000A31E01106B50 AS DateTime), 1)
INSERT [dbo].[ArticleRate] ([ArticleID], [RateID], [CreationDate], [RateOwner]) VALUES (2, 1, CAST(0x0000A31E0110881E AS DateTime), 3)
INSERT [dbo].[ArticleRate] ([ArticleID], [RateID], [CreationDate], [RateOwner]) VALUES (2, 2, CAST(0x0000A31E0110656F AS DateTime), 1)
INSERT [dbo].[ArticleRate] ([ArticleID], [RateID], [CreationDate], [RateOwner]) VALUES (2, 4, CAST(0x0000A31E01103B57 AS DateTime), 7)
INSERT [dbo].[ArticleRate] ([ArticleID], [RateID], [CreationDate], [RateOwner]) VALUES (3, 3, CAST(0x0000A31E01105CD8 AS DateTime), 1)
INSERT [dbo].[ArticleRate] ([ArticleID], [RateID], [CreationDate], [RateOwner]) VALUES (3, 4, CAST(0x0000A31E01108094 AS DateTime), 3)
INSERT [dbo].[ArticleRate] ([ArticleID], [RateID], [CreationDate], [RateOwner]) VALUES (3, 5, CAST(0x0000A31E011033D3 AS DateTime), 7)
INSERT [dbo].[Complaint] ([ComplaintID], [ComplaintSubject], [ComplaintCategory], [Priority], [ComplaintContents], [Status], [ComplaintOwner], [LodgingDate], [ClosingDate], [Technician], [Reasons], [LastModified]) VALUES (1, N'Error Message:  Lowest Error Addess', 1, 1, N'Error blue screen displayed and i cannot continue to work<br>', 1, 7, CAST(0x0000A31E0102523A AS DateTime), NULL, NULL, NULL, CAST(0x0000A31E0102523A AS DateTime))
INSERT [dbo].[Complaint] ([ComplaintID], [ComplaintSubject], [ComplaintCategory], [Priority], [ComplaintContents], [Status], [ComplaintOwner], [LodgingDate], [ClosingDate], [Technician], [Reasons], [LastModified]) VALUES (2, N'Word cannot display the contents', 2, 2, N'The microsoft word <span style="color: rgb(255, 0, 0);"><span style="font-weight: bold;">cannot</span></span> display the content whenever i open the file.<br>', 1, 7, CAST(0x0000A31B0103630D AS DateTime), NULL, NULL, NULL, CAST(0x0000A31B0103630D AS DateTime))
INSERT [dbo].[Complaint] ([ComplaintID], [ComplaintSubject], [ComplaintCategory], [Priority], [ComplaintContents], [Status], [ComplaintOwner], [LodgingDate], [ClosingDate], [Technician], [Reasons], [LastModified]) VALUES (3, N'Cannot open excel file on my machine', 2, 2, N'Hi,<br><br>I cannot open the excel file. Please see the attachment for details.<br><br>Best regards,<br>Truong.<br><br>', 2, 7, CAST(0x0000A31E010563D6 AS DateTime), NULL, 4, NULL, CAST(0x0000A31E010786CF AS DateTime))
INSERT [dbo].[Complaint] ([ComplaintID], [ComplaintSubject], [ComplaintCategory], [Priority], [ComplaintContents], [Status], [ComplaintOwner], [LodgingDate], [ClosingDate], [Technician], [Reasons], [LastModified]) VALUES (4, N'Problem when opening pdf file', 2, 1, N'I cannot open the pdf file. So that i cannot read any requirements from customer in form of pdf.<br><br>Please help me to fix that.<br>', 1, 5, CAST(0x0000A31E0105EB67 AS DateTime), NULL, NULL, NULL, CAST(0x0000A31E0105EB67 AS DateTime))
INSERT [dbo].[Complaint] ([ComplaintID], [ComplaintSubject], [ComplaintCategory], [Priority], [ComplaintContents], [Status], [ComplaintOwner], [LodgingDate], [ClosingDate], [Technician], [Reasons], [LastModified]) VALUES (5, N'Unable to install printer', 3, 2, N'Please help me to install the printer driver. I got the prblem.<br><br>Best regards,<br>Anh<br>', 1, 3, CAST(0x0000A31E010647D6 AS DateTime), NULL, NULL, NULL, CAST(0x0000A31E010647D6 AS DateTime))
INSERT [dbo].[Complaint] ([ComplaintID], [ComplaintSubject], [ComplaintCategory], [Priority], [ComplaintContents], [Status], [ComplaintOwner], [LodgingDate], [ClosingDate], [Technician], [Reasons], [LastModified]) VALUES (6, N'Office stops working', 2, 2, N'Hi, <br>I cannot read the word file anymore. The word office stops working.<br><br>Best regards,<br>Truong.<br><br>', 4, 7, CAST(0x0000A31E0106D1D1 AS DateTime), CAST(0x0000A31E01076B50 AS DateTime), 2, N'Re-install the microsoft word and register new license. The old license has been expired.<br>', CAST(0x0000A31E01076B50 AS DateTime))

INSERT [dbo].[ComplaintHistory] ([HistoryID], [UserAccountID], [ComplaintID], [ActionID], [LastModifiedDate], [Details]) VALUES (1, 7, 1, 3, CAST(0x0000A31E0102523A AS DateTime), N'Creates new complaint')
INSERT [dbo].[ComplaintHistory] ([HistoryID], [UserAccountID], [ComplaintID], [ActionID], [LastModifiedDate], [Details]) VALUES (2, 7, 2, 3, CAST(0x0000A31B0103630D AS DateTime), N'Creates new complaint')
INSERT [dbo].[ComplaintHistory] ([HistoryID], [UserAccountID], [ComplaintID], [ActionID], [LastModifiedDate], [Details]) VALUES (3, 7, 3, 3, CAST(0x0000A31E010563D6 AS DateTime), N'Creates new complaint')
INSERT [dbo].[ComplaintHistory] ([HistoryID], [UserAccountID], [ComplaintID], [ActionID], [LastModifiedDate], [Details]) VALUES (4, 5, 4, 3, CAST(0x0000A31E0105EB67 AS DateTime), N'Creates new complaint')
INSERT [dbo].[ComplaintHistory] ([HistoryID], [UserAccountID], [ComplaintID], [ActionID], [LastModifiedDate], [Details]) VALUES (5, 3, 5, 3, CAST(0x0000A31E010647D6 AS DateTime), N'Creates new complaint')
INSERT [dbo].[ComplaintHistory] ([HistoryID], [UserAccountID], [ComplaintID], [ActionID], [LastModifiedDate], [Details]) VALUES (6, 7, 6, 3, CAST(0x0000A31E0106D1D1 AS DateTime), N'Creates new complaint')
INSERT [dbo].[ComplaintHistory] ([HistoryID], [UserAccountID], [ComplaintID], [ActionID], [LastModifiedDate], [Details]) VALUES (7, 1, 6, 6, CAST(0x0000A31E01071D05 AS DateTime), N'Assigns technician')
INSERT [dbo].[ComplaintHistory] ([HistoryID], [UserAccountID], [ComplaintID], [ActionID], [LastModifiedDate], [Details]) VALUES (8, 1, 6, 2, CAST(0x0000A31E01071D2D AS DateTime), N'Changes complaints status')
INSERT [dbo].[ComplaintHistory] ([HistoryID], [UserAccountID], [ComplaintID], [ActionID], [LastModifiedDate], [Details]) VALUES (9, 2, 6, 2, CAST(0x0000A31E01076B7A AS DateTime), N'Changes complaints status')
INSERT [dbo].[ComplaintHistory] ([HistoryID], [UserAccountID], [ComplaintID], [ActionID], [LastModifiedDate], [Details]) VALUES (10, 2, 6, 7, CAST(0x0000A31E01076B80 AS DateTime), N'Changes complaints category')
INSERT [dbo].[ComplaintHistory] ([HistoryID], [UserAccountID], [ComplaintID], [ActionID], [LastModifiedDate], [Details]) VALUES (11, 2, 6, 4, CAST(0x0000A31E01076B84 AS DateTime), N'Updates complaint root cause')
INSERT [dbo].[ComplaintHistory] ([HistoryID], [UserAccountID], [ComplaintID], [ActionID], [LastModifiedDate], [Details]) VALUES (12, 1, 3, 6, CAST(0x0000A31E010786EB AS DateTime), N'Assigns technician')
INSERT [dbo].[ComplaintHistory] ([HistoryID], [UserAccountID], [ComplaintID], [ActionID], [LastModifiedDate], [Details]) VALUES (13, 1, 3, 2, CAST(0x0000A31E010786EF AS DateTime), N'Changes complaints status')
INSERT [dbo].[ComplaintImages] ([ComplaintImageID], [ComplaintID], [ImageLocation], [CreationDate]) VALUES (1, 1, N'upload/complaint/ram_error.jpg', CAST(0x0000A31E0102523D AS DateTime))
INSERT [dbo].[ComplaintImages] ([ComplaintImageID], [ComplaintID], [ImageLocation], [CreationDate]) VALUES (2, 2, N'upload/complaint/error_office.jpg', CAST(0x0000A31E0103630E AS DateTime))
INSERT [dbo].[ComplaintImages] ([ComplaintImageID], [ComplaintID], [ImageLocation], [CreationDate]) VALUES (3, 3, N'upload/complaint/cannot_open_excel.png', CAST(0x0000A31E010563D9 AS DateTime))
INSERT [dbo].[ComplaintImages] ([ComplaintImageID], [ComplaintID], [ImageLocation], [CreationDate]) VALUES (4, 4, N'upload/complaint/canot_open_pdf.jpg', CAST(0x0000A31E0105EB6A AS DateTime))
INSERT [dbo].[ComplaintImages] ([ComplaintImageID], [ComplaintID], [ImageLocation], [CreationDate]) VALUES (5, 5, N'upload/complaint/unable-to-install-printer-error.png', CAST(0x0000A31E010647D6 AS DateTime))
INSERT [dbo].[ComplaintImages] ([ComplaintImageID], [ComplaintID], [ImageLocation], [CreationDate]) VALUES (6, 6, N'upload/complaint/word_stop_working.jpg', CAST(0x0000A31E0106D1D2 AS DateTime))

