delete from tasks;

insert into tasks(id, create_time, payload, status, length, type) values
(1, '2020-11-26 00:30:00.839', 'h8guqcuvqmlkIC8', "COMPLETED", 15, "string"),
(2, '2020-11-26 00:58:38.585', 'hO6rmPDIzffvsdZ3M3e7JJlY1uMlsteKyYnQqez7', "COMPLETED", 40, "string"),
(3, '2020-11-26 00:58:51.606', 'ie1ayTbngH7jhWufNTg1FRQ0sRoqXPDNp39NSm1PzrPFB375YvZrGDXqEEvWWJ21XTYRRCHPSaIxMXsUja4U43mqqHv1JsDSD8FkNEkRLlxori7mG6kb1rEoTQWLUgKsrtr1hqiiZyI7dumyADTonj', "COMPLETED", 150, "string"),


alter sequence hibernate_sequence restart with 10;
