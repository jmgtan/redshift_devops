create or replace procedure merge_staged_products()
as $$
BEGIN
    update products set status='CLOSED', close_date=CURRENT_DATE where product_name in (select product_name from products_staging) and status='ACTIVE';
    insert into products(product_name,price) select product_name, price from products_staging;
    truncate products_staging;
END;
$$ LANGUAGE plpgsql;