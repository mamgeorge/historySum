--// datebeg, dateend, eramain, locales, personname, eventmain, referenced, tags, mediaicopath

USE mydb; DESCRIBE history;
SHOW COLUMNS FROM history; SHOW FULL COLUMNS FROM history;
SELECT * FROM mydb.history;
SELECT id, eventmain, referenced, datecre, datemod FROM mydb.history;
SELECT NOW(), CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP, UTC_TIMESTAMP();
SELECT UNIX_TIMESTAMP('2023-05-27 12:00:00');

ALTER TABLE mydb.history DROP COLUMN dateendpre; eralist localelist taglist
ALTER TABLE mydb.history RENAME COLUMN groupings TO tags;
ALTER TABLE mydb.history MODIFY COLUMN datecre TIMESTAMP;
ALTER TABLE mydb.history MODIFY COLUMN datemod TIMESTAMP;

UPDATE mydb.history SET referenced = 'referenced' WHERE referenced = 'references';
UPDATE mydb.history SET tags = 'tags' WHERE tags = 'grouping';
UPDATE mydb.history SET datebeg = '-0004', dateend = '+0029' WHERE id = 2;
UPDATE mydb.history SET datecre = TIMESTAMP('2023-05-27 00:00:00') WHERE id = 2;
UPDATE mydb.history SET datecre = TIMESTAMP( STR_TO_DATE( '2023/05/27 12:00:00 AM', '%Y/%m/%d %h:%i:%s %p')) WHERE id = 2;

UPDATE mydb.history SET
	datebeg = '-0004',
	dateend = '-0004',
	eramain = 'Roman Empire',
	locales = 'Canaan (Israel, Philistia)',
	personname = 'Jesus Christ',
	eventmain = 'Star of Bethlehem',
	referenced = 'QHS.TreatiseAstronomy.par156: Cap BroomStar, QHS.AnnalsAi.par26: Aqu StarComet',
	tags = 'h0000',
	mediaicopath = 'b0004_H_Jerusalem_Star',
	datecre = CURRENT_TIMESTAMP,
	datemod = CURRENT_TIMESTAMP,
	user = 'mamge'
	WHERE id = 1;

COMMIT;
