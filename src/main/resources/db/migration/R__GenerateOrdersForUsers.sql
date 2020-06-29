create or replace procedure gen_orders(in_email varchar, num_items integer)
as $$
DECLARE
    i INTEGER;
BEGIN
    FOR i in 1..num_items LOOP
        insert into orders(email, date_created, product_id, quantity) values(in_email, CURRENT_TIMESTAMP, (select id from products order by random() limit 1), 1);
    END LOOP;
END;
$$ LANGUAGE plpgsql;