-- public."WALLET" definition

-- Drop table

-- DROP TABLE "WALLET";

CREATE TABLE "WALLET" (
	"ID" int4 NOT NULL GENERATED ALWAYS AS IDENTITY,
	"USER_ID" int4 NOT NULL,
	"DISPOSABILITY" float4 NOT NULL DEFAULT 0,
	"CURRENCY_ID" int4 NOT NULL,
	"ACTUAL_STAKED" float4 NULL DEFAULT 0,
	CONSTRAINT "WALLET_pkey" PRIMARY KEY ("ID")
);

-- public."WALLET" foreign keys

ALTER TABLE public."WALLET" ADD CONSTRAINT "WALLET_USER_ID" FOREIGN KEY ("USER_ID") REFERENCES "USER"("ID");
ALTER TABLE public."WALLET" ADD CONSTRAINT "WALLET_CURRENCY_ID" FOREIGN KEY ("CURRENCY_ID") REFERENCES "CURRENCY"("ID");

INSERT INTO "WALLET" ("ID","USER_ID","DISPOSABILITY","CURRENCY_ID","ACTUAL_STAKED") VALUES
	 (2,1,123.213,1,0.0);