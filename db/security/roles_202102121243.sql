INSERT INTO public.roles (icon,"name",parent_id,link,created_by,created_date,last_modified_by,last_modified_date,"index") VALUES 
('','OPERATION',NULL,NULL,NULL,NULL,'admin','2020-03-21 10:25:01.435',1)
,('','FINANCE',NULL,NULL,NULL,NULL,NULL,NULL,2)
,('','ADMIN',NULL,NULL,NULL,NULL,'admin','2020-03-24 17:19:27.846',3)
,(NULL,'REPORT',NULL,NULL,NULL,NULL,NULL,NULL,4)
,(NULL,'MASTER DATA',NULL,NULL,NULL,NULL,NULL,NULL,5)
,('fa fa-user-plus','LIST USER',5,'/admin/user-list',NULL,NULL,'admin','2020-08-06 12:13:20.700',6)
,('fa fa-reorder','MENU',5,'/admin/menu-list',NULL,NULL,'admin','2020-08-06 12:12:43.240',7)
,('fa fa-reorder','SUB MENU',5,'/admin/submenu-list',NULL,NULL,'admin','2020-08-06 12:12:54.603',8)
,('fa fa-user','OTORITAS',5,'/admin/userrole-list',NULL,NULL,NULL,NULL,9)
,('fa fa-table','REF',5,'/admin/ref-list',NULL,NULL,NULL,NULL,10)
;