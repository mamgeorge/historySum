--// datebeg, dateend, eramain, locales, personname, eventmain, referenced, tags, mediaicopath

USE mydb; DESCRIBE history;
SHOW COLUMNS FROM history; SHOW FULL COLUMNS FROM history;
SELECT * FROM mydb.history;

ALTER TABLE mydb.history DROP COLUMN dateendpre; eralist localelist taglist
ALTER TABLE mydb.history RENAME COLUMN groupings TO tags;

UPDATE mydb.history SET referenced = "referenced" WHERE referenced = "references";
UPDATE mydb.history SET tags = "tags" WHERE tags = "grouping";
UPDATE mydb.history SET datebeg = "-0004", dateend = "+0029" WHERE id = 2;

UPDATE mydb.history SET
	datebeg = "-0004",
	dateend = "-0004",
	eramain = "Roman Empire",
	locales = "Canaan (Israel, Philistia)",
	personname = "Jesus Christ",
	eventmain = "Star of Bethlehem",
	referenced = "QHS.TreatiseAstronomy.par156: Cap BroomStar, QHS.AnnalsAi.par26: Aqu StarComet",
	tags = "h0000",
	mediaicopath = "b0004_H_Jerusalem_Star"
	WHERE id = 1;

commit;
