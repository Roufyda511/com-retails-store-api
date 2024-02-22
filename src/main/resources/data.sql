INSERT INTO "PUBLIC"."purchuser"("id","name","phone","purchuser_type","regestration_date")VALUES(1,'Ahmed','019876988','CUSTOMER','2023-02-22 15:13:43');
INSERT INTO "PUBLIC"."purchuser"("id","name","phone","purchuser_type","regestration_date")VALUES(2,'Eslam Mohamed','0198769878','EMPLOYEE','2021-02-22 15:13:43');

INSERT INTO "PUBLIC"."purchuser"("id","name","phone","purchuser_type","regestration_date")VALUES(3,'Yassmen','0198712988','AFFILIATE','2023-02-22 15:13:43');

INSERT INTO "PUBLIC"."purchuser"("id","name","phone","purchuser_type","regestration_date")VALUES(4,'Ahmed','0196754488','CUSTOMER','2022-02-22 15:13:43');

-----Bills--------
INSERT INTO "PUBLIC"."bill" ("id","creation_date","market_name","purchuser_id")VALUES(1,'2024-02-22 15:13:43','GO','1');
INSERT INTO "PUBLIC"."bill" ("id","creation_date","market_name","purchuser_id")VALUES(2,'2024-02-22 15:13:43','GO','2');
INSERT INTO "PUBLIC"."bill" ("id","creation_date","market_name","purchuser_id")VALUES(3,'2024-02-22 15:13:43','GO','4');

---Items ----
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id")VALUES(1,20,'GROCERY','meet',1);
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id")VALUES(2,10,'GROCERY','bean',1);
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id")VALUES(3,30,'GROCERY','Tomato',1);
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id")VALUES(4,15.5,'PERSONAL_HYGIENE','Deodorant',1);
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id")VALUES(5,30,'PERSONAL_HYGIENE','Shampoo',1);
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id")VALUES(6,60,'PERSONAL_HYGIENE','Kleenex',1);