/*
Item init
*/
INSERT INTO item (id, item_name, minutes, price) VALUES (10, 'Hemd', 12, 6);
INSERT INTO item (id, item_name, minutes, price) VALUES (20, 'T-shirt', 8, 3);
INSERT INTO item (id, item_name, minutes, price) VALUES (30, 'Broek', 10, 3);
INSERT INTO item (id, item_name, minutes, price) VALUES (40, 'Zakdoek', 5, 2);
INSERT INTO item (id, item_name, minutes, price) VALUES (50, 'Hemd', 20, 10);

/*
Roles init
*/

INSERT INTO role (id, name) VALUES (1, 'BASIC_USER');
INSERT INTO role (id, name) VALUES (2, 'ADMIN');

/*
Customers init
*/

INSERT INTO customer (id, email_address, first_name, last_name, minutes_left, note,account_number, phone_number) VALUES (10, 'lucasvandenabbeele@hotmail.com', 'Lucas', 'Van den Abbeele', 12, 'First customer in the database', 15485646531, '0477449635');


/*
Basket init
*/

INSERT INTO public.basket (id, active, cash, done_date_time, price, start_date_time, customer_id) VALUES (10, true, false, null, 0, '2020-07-02 12:13:20.000000', 10);

/*
Add items to bucket
 */
INSERT INTO public.item_baskets (items_id, baskets_id) VALUES (10, 10);
INSERT INTO public.item_baskets (items_id, baskets_id) VALUES (20, 10);
INSERT INTO public.item_baskets (items_id, baskets_id) VALUES (30, 10);