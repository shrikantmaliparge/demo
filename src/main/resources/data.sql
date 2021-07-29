insert into Login_Database
values(10001,'Shrikant.456@gmail.com','Shrikant', 'jtLsPQ', 'Shrikant.456@gmail.com_Shrikant');

insert into Login_Database
values(10002,'Vaishali.rokade@gmail.com','Vaishali', 'akejdi', 'Vaishali.rokade@gmail.com_Vaishali');

insert into Available_Services
values('Shrikant','India Films',10,4.0);

insert into Available_Services
values('Shrikant','Netherlandse Series',20,6.0);

insert into Available_Services
values('Vaishali','Netherlandse Films',8,5.0);

insert into Available_Services
values('Vaisahli','Netherlandse Series',25,8.0);

insert into Subscribed_Services
values(1001, 'Shrikant','Netherlandse Films',1,4.0,TO_DATE('25-07-2021', 'DD-MM-YYYY'), TO_DATE('25-07-2021', 'DD-MM-YYYY'));

insert into Subscribed_Services
values(2001, 'Shrikant','Netherlandse Series',1,6.0,TO_DATE('27-07-2021', 'DD-MM-YYYY'), TO_DATE('25-07-2021', 'DD-MM-YYYY'));

insert into Subscribed_Services
values(3001, 'Vaishu','Netherlandse Films',1,5.0,TO_DATE('27-07-2021', 'DD-MM-YYYY'), TO_DATE('25-07-2021', 'DD-MM-YYYY'));

insert into Subscribed_Services
values(4001, 'Vaishu','Netherlandse Series',1,8.0,TO_DATE('27-07-2021', 'DD-MM-YYYY'), TO_DATE('25-07-2021', 'DD-MM-YYYY'));