INSERT INTO cities (name, country_id) VALUES ('Smila', (select id from countries where countries.name = 'Ukraine'))