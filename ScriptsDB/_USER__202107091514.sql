-- public."USER" definition

-- Drop table

-- DROP TABLE "USER";

CREATE TABLE "USER" (
	"ID" int4 NOT NULL GENERATED ALWAYS AS IDENTITY,
	"EMAIL" varchar(125) NOT NULL,
	"USERNAME" varchar(50) NOT NULL,
	"NAME" varchar(50) NOT NULL,
	"SURNAME" varchar(50) NOT NULL,
	CONSTRAINT "USER_pkey" PRIMARY KEY ("ID")
);

INSERT INTO "USER" ("ID","EMAIL","USERNAME","NAME","SURNAME") OVERRIDING SYSTEM VALUE VALUES (1,'pippo@pippo.it','pippo','pippo','pluto');