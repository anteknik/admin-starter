-- Drop table

-- DROP TABLE public.user_logins

CREATE TABLE public.user_logins (
	id bigserial NOT NULL,
	email varchar(255) NULL,
	"password" varchar(255) NOT NULL,
	username varchar(255) NOT NULL,
	account_non_expired int4 NOT NULL DEFAULT 1,
	account_non_locked int4 NOT NULL DEFAULT 1,
	credentials_non_expired int4 NOT NULL DEFAULT 1,
	enabled int4 NOT NULL DEFAULT 1,
	created_by varchar(255) NULL,
	created_date timestamp NULL,
	last_modified_by varchar(255) NULL,
	last_modified_date timestamp NULL,
	department_id varchar(50) NULL,
	CONSTRAINT user_logins_pkey PRIMARY KEY (id)
);

-- Drop table

-- DROP TABLE public.roles

CREATE TABLE public.roles (
	id bigserial NOT NULL,
	icon varchar(255) NULL,
	"name" varchar(255) NOT NULL,
	parent_id int8 NULL,
	link varchar(255) NULL,
	created_by varchar(255) NULL,
	created_date timestamp NULL,
	last_modified_by varchar(255) NULL,
	last_modified_date timestamp NULL,
	"index" int4 NULL,
	CONSTRAINT roles_pkey PRIMARY KEY (id),
	CONSTRAINT fkc8p0cleevyq7r83sa85bn13gm FOREIGN KEY (parent_id) REFERENCES roles(id)
);

-- Drop table

-- DROP TABLE public.user_roles

CREATE TABLE public.user_roles (
	id bigserial NOT NULL,
	role_id int8 NULL,
	user_id int8 NULL,
	created_by varchar(255) NULL,
	created_date timestamp NULL,
	last_modified_by varchar(255) NULL,
	last_modified_date timestamp NULL,
	CONSTRAINT user_roles_pkey PRIMARY KEY (id),
	CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6 FOREIGN KEY (role_id) REFERENCES roles(id),
	CONSTRAINT fkkl3gjcoh6m7b0s29w1knfegr7 FOREIGN KEY (id) REFERENCES user_roles(id),
	CONSTRAINT fknxv7d3c0go3dx3cu8keu3qw2p FOREIGN KEY (user_id) REFERENCES user_logins(id)
);




INSERT INTO public.user_logins (email,"password",username,account_non_expired,account_non_locked,credentials_non_expired,enabled,created_by,created_date,last_modified_by,last_modified_date,department_id) VALUES 
('admin@admin.net','$2a$10$5BZ5CXIbB/2vIAI11zlw1e51QVBhE/nWBvFTCPQ51e7YvoozNdU4m','admin',1,1,1,1,NULL,NULL,'admin','2021-02-03 07:31:17.147',NULL)
,('user@user.net','$2a$10$dQyANI0W1RArirvM5iq66u5qFC1A/vJ6z3EO8o1q77IormPmHj0.i','user',0,0,0,1,'admin','2021-02-03 07:30:53.832','admin','2021-02-03 07:30:53.832',NULL)
;


INSERT INTO public.roles (icon,"name",parent_id,link,created_by,created_date,last_modified_by,last_modified_date,"index") VALUES 
(NULL,'MASTER DATA',NULL,NULL,NULL,NULL,NULL,NULL,1)
,('fa fa-user-plus','LIST USER',1,'/admin/user-list',NULL,NULL,'admin','2020-08-06 12:13:20.700',2)
,('fa fa-reorder','MENU',1,'/admin/menu-list',NULL,NULL,'admin','2020-08-06 12:12:43.240',2)
,('fa fa-reorder','SUB MENU',1,'/admin/submenu-list',NULL,NULL,'admin','2020-08-06 12:12:54.603',4)
,('fa fa-user','OTORITAS',1,'/admin/userrole-list',NULL,NULL,NULL,NULL,5)
;

INSERT INTO public.user_roles (role_id,user_id,created_by,created_date,last_modified_by,last_modified_date) VALUES 
(1,1,'admin','2020-03-21 10:25:01.000','admin','2021-02-03 10:25:01.000')
,(2,1,'admin','2020-03-21 10:25:01.000','admin','2021-02-03 10:25:01.000')
,(3,1,'admin','2020-03-21 10:25:01.000','admin','2021-02-03 10:25:01.000')
,(4,1,'admin','2020-03-21 10:25:01.000','admin','2021-02-03 10:25:01.000')
,(5,1,'admin','2020-03-21 10:25:01.000','admin','2021-02-03 10:25:01.000')
;

