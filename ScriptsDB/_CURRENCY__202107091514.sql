-- public."CURRENCY" definition

-- Drop table

-- DROP TABLE "CURRENCY";

CREATE TABLE "CURRENCY" (
	"ID" int4 NOT NULL GENERATED ALWAYS AS IDENTITY,
	"CODE" varchar(3) NOT NULL,
	"SYMBOL" varchar(5) NOT NULL,
	"DESCRIPTION" varchar(256) NOT NULL,
	CONSTRAINT "CURRENCY_pkey" PRIMARY KEY ("ID")
);

INSERT INTO "CURRENCY" ("ID","CODE","SYMBOL","DESCRIPTION") OVERRIDING SYSTEM VALUE VALUES VALUES
	 (1,'USD','$','US Dollars'),
	 (2,'EUR','€','Euro');