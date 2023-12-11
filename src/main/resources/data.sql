INSERT INTO users (id, email, password, username)
VALUES
    (1, 'admin@gmail.com', '67cfb2916d5b11d93bf295d8c8d383245e652ad0b11a254a4439c9b904b1a73c0285107f1211f8d5e4c13102ee524228', 'admin'),
    (2, 'nati.hristova@abv.bg', '67cfb2916d5b11d93bf295d8c8d383245e652ad0b11a254a4439c9b904b1a73c0285107f1211f8d5e4c13102ee524228', 'nati'),
    (3, 'natihristova2003@gmail.com', '67cfb2916d5b11d93bf295d8c8d383245e652ad0b11a254a4439c9b904b1a73c0285107f1211f8d5e4c13102ee524228', 'natali');

INSERT INTO roles (`id`, `role`)
VALUES
    (1, 'ADMIN'),
    (2, 'SHOP_OWNER'),
    (3, 'USER');

INSERT INTO users_roles(`user_id`, `role_id`)
VALUES
    (1, 1),
    (2, 3),
    (3, 2);