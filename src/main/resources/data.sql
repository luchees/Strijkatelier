/*
Item init
*/
INSERT INTO item (id, item_name, minutes, price) VALUES (1, 'Hemd', 12, 6);
INSERT INTO item (id, item_name, minutes, price) VALUES (2, 'T-shirt', 8, 3);
INSERT INTO item (id, item_name, minutes, price) VALUES (3, 'Broek', 10, 3);
INSERT INTO item (id, item_name, minutes, price) VALUES (4, 'Zakdoek', 5, 2);
INSERT INTO item (id, item_name, minutes, price) VALUES (5, 'Hemd', 12, 6);

/*
Roles init
*/

INSERT INTO role (id, name) VALUES (1, 'BASIC_USER');
INSERT INTO role (id, name) VALUES (2, 'ADMIN');

/*
Customers init
*/

INSERT INTO customer (id, emailaddress, first_name, last_name, minutes_left, note, phone_number) VALUES (1, 'lucasvandenabbeele@hotmail.com', 'Lucas', 'Van den Abbeele', 12, 'First customer in the database', '0477449635');


/*
Basket init
*/

INSERT INTO public.basket (id, active, cash, done_date_time, price, start_date_time, customer_id) VALUES (1, true, false, null, 0, '2020-07-02 12:13:20.000000', 1);

/*
Add items to bucket
 */
INSERT INTO public.item_baskets (items_id, baskets_id) VALUES (1, 1);
INSERT INTO public.item_baskets (items_id, baskets_id) VALUES (2, 1);
INSERT INTO public.item_baskets (items_id, baskets_id) VALUES (3, 1);