INSERT INTO "PUBLIC"."purchuser"("id","name","phone","purchuser_type","regestration_date")VALUES(1,'Ahmed','019876988','CUSTOMER','2023-02-22 15:13:43');
INSERT INTO "PUBLIC"."purchuser"("id","name","phone","purchuser_type","regestration_date")VALUES(2,'Eslam Mohamed','0198769878','EMPLOYEE','2021-02-22 15:13:43');

INSERT INTO "PUBLIC"."purchuser"("id","name","phone","purchuser_type","regestration_date")VALUES(3,'Yassmen','0198712988','AFFILIATE','2023-02-22 15:13:43');

INSERT INTO "PUBLIC"."purchuser"("id","name","phone","purchuser_type","regestration_date")VALUES(4,'Ahmed','0196754488','CUSTOMER','2022-02-22 15:13:43');

-----Bills--------
INSERT INTO "PUBLIC"."bill" ("id","creation_date","market_name","purchuser_id")VALUES(1,1708694376000,'GO','1');
INSERT INTO "PUBLIC"."bill" ("id","creation_date","market_name","purchuser_id")VALUES(2,1708694376000,'GO','2');
INSERT INTO "PUBLIC"."bill" ("id","creation_date","market_name","purchuser_id")VALUES(3,1708694376000,'GO','3');
INSERT INTO "PUBLIC"."bill" ("id","creation_date","market_name","purchuser_id")VALUES(4,1708694376000,'GO','4');
INSERT INTO "PUBLIC"."bill" ("id","creation_date","market_name","purchuser_id")VALUES(5,1708694376000,'GO','1');
INSERT INTO "PUBLIC"."bill" ("id","creation_date","market_name","purchuser_id")VALUES(6,1708694376000,'GO','1');

---Items ----
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(1,20.0,'GROCERY','meet',1,'mee23');
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(2,10.0,'GROCERY','bean',1,'bea33');
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(3,30.0,'GROCERY','Tomato',1,'tom23');
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(4,15.5,'PERSONAL_HYGIENE','Deodorant',1,'Deo23');
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(5,30.0,'PERSONAL_HYGIENE','Shampoo',1,'sha23');
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(6,60.0,'PERSONAL_HYGIENE','Kleenex',1,'kle23');
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(7,30.0,'GROCERY','Tomato',1,'tom23');
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(8,30.0,'PERSONAL_HYGIENE','Shampoo',1,'sha23');


INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(11,20.0,'GROCERY','meet',2,'mee23');
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(12,10.0,'GROCERY','bean',2,'bea33');
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(13,30.0,'GROCERY','Tomato',2,'tom23');
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(14,15.5,'PERSONAL_HYGIENE','Deodorant',2,'Deo23');
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(15,30.0,'PERSONAL_HYGIENE','Shampoo',2,'sha23');
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(16,60.0,'PERSONAL_HYGIENE','Kleenex',2,'kle23');
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(17,30.0,'GROCERY','Tomato',2,'tom23');
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(18,80.0,'PERSONAL_HYGIENE','Facewash',2,'fac23');



INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(21,20.0,'GROCERY','meet',3,'mee23');
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(22,10.0,'GROCERY','bean',3,'bea33');
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(23,30.0,'GROCERY','Tomato',3,'tom23');
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(24,15.5,'PERSONAL_HYGIENE','Deodorant',3,'Deo23');
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(25,30.0,'PERSONAL_HYGIENE','Shampoo',3,'sha23');
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(26,60.0,'PERSONAL_HYGIENE','Kleenex',3,'kle23');
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(29,60.0,'PERSONAL_HYGIENE','Kleenex',3,'kle23');
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(27,30.0,'GROCERY','Tomato',3,'tom23');
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(28,50.0,'PAPER_PRODUCTS','Paper Towels',3,'fac23');


INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(31,20.0,'GROCERY','meet',4,'mee23');
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(32,10.0,'GROCERY','bean',4,'bea33');
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(33,30.0,'GROCERY','Tomato',4,'tom23');
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(34,15.5,'PERSONAL_HYGIENE','Deodorant',4,'Deo23');
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(35,30.0,'PERSONAL_HYGIENE','Shampoo',4,'sha23');
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(36,60.0,'PERSONAL_HYGIENE','Kleenex',4,'kle23');
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(39,60.0,'PERSONAL_HYGIENE','Kleenex',4,'kle23');
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(37,30.0,'GROCERY','Tomato',4,'tom23');
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(38,50.0,'PAPER_PRODUCTS','Paper Towels',4,'fac23');


INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(46,5.0,'PERSONAL_HYGIENE','Kleenex',5,'kle23');
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(49,5.0,'PERSONAL_HYGIENE','Kleenex',5,'kle23');
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(47,30.0,'GROCERY','Tomato',5,'tom23');
INSERT INTO "PUBLIC"."item" ("id","price","type","name","bill_id","code")VALUES(48,50.0,'PAPER_PRODUCTS','Paper Towels',5,'fac23');





