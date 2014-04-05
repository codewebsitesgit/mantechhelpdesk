create database bie40d12_MantechHelpdesk;

use bie40d12_MantechHelpdesk;

/* UserAccount and UserRole 
* n:1
* one user has only one role
* one role can be assigned to multiple users
*/
create table UserAccount
(
	AccountID int primary key identity,
	Username varchar(50) unique not null,
	Password varchar(150) not null,
	DepartmentID int not null,
	 
);

/*create table UserRole 
(
	
);*/

