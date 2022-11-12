USE mydb; DESCRIBE history;
SHOW COLUMNS FROM history; SHOW FULL COLUMNS FROM history;

SELECT * FROM mydb.history;

ALTER TABLE mydb.history DROP COLUMN taglist; eralist localelist taglist
ALTER TABLE mydb.history RENAME COLUMN groupings TO tags;

UPDATE mydb.history SET referenced = "referenced" WHERE referenced = "references";
UPDATE mydb.history SET tags = "tags" WHERE tags = "grouping";

commit;