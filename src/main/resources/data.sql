INSERT INTO `method` (name) VALUES ('Transfer Rekening');

INSERT INTO `merchant` (`id`, `name`, `image`, `is_active`) VALUES (1, 'Tokopedia', 'https://toppng.com/uploads/preview/tokopedia-logo-icon-tokopedia-115638033660bhcr3nbcq.png', b'1');
INSERT INTO `merchant` (`id`, `name`, `image`, `is_active`) VALUES (2, 'Shopee', 'https://w7.pngwing.com/pngs/650/828/png-transparent-shopee-logo-thumbnail.png', b'1');
INSERT INTO `merchant` (`id`, `name`, `image`, `is_active`) VALUES (3, 'Bukalapak', 'https://assets.bukalapak.com/sigil/bukalapak-logo-primary.svg', b'1');
INSERT INTO `merchant` (`id`, `name`, `image`, `is_active`) VALUES (4, 'Lazada', 'https://w7.pngwing.com/pngs/922/628/png-transparent-lazada-logo.png', b'1');
INSERT INTO `merchant` (`id`, `name`, `image`, `is_active`) VALUES (5, 'Blibli', 'https://seeklogo.com/images/B/blibli-logo-BBC7270E6F-seeklogo.com.png', b'1');



INSERT INTO `user` (`id`, `created_at`, `email`, `is_active`, `password`, `updated_at`) VALUES (1, '2023-01-10 10:30:59.263000', 'ilhamferdian88@gmail.com', b'1', '$2a$10$ahb4yc8JO6uve0sMZErQTuzn10mQSlBDYpjNEswcO36Uv8KSLANQ6', '2023-01-10 10:30:59.263000');
INSERT INTO `user` (`id`, `created_at`, `email`, `is_active`, `password`, `updated_at`) VALUES (2, '2023-01-10 10:31:11.644000', 'ilhamferdian881@gmail.com', b'1', '$2a$10$zGyVJQqquVouH.rXtX03pO.pJ5fq0i7VZANUH0Hcjc7j.oxYg/DfS', '2023-01-10 10:31:11.644000');
INSERT INTO `user` (`id`, `created_at`, `email`, `is_active`, `password`, `updated_at`) VALUES (3, '2023-01-14 23:58:07.644000', 'michael@gmail.com', b'1', '$2a$10$LcqqJZyQ34JuF14T.LJKrely7IPMl12/br/ZCFLN9qcMFmMaVSPym', '2023-01-14 23:58:07.644000');
INSERT INTO `user` (`id`, `created_at`, `email`, `is_active`, `password`, `updated_at`) VALUES (4, '2023-01-15 09:34:13.644000', 'michaeljacob@gmail.com', b'1', '$2a$10$HOzRXdKzwfWU8GjFN9dUrOtftiqGyUxC.kapFQ7F/vM.4qAh8YWee', '2023-01-15 09:34:13.644000');



INSERT INTO `wallet` (`user_id`, `balance`, `pin`, `updated_at`) VALUES (1, 1000000, '123456', '2023-01-10 10:30:59.269000');
INSERT INTO `wallet` (`user_id`, `balance`, `pin`, `updated_at`) VALUES (2, 0, '123456', '2023-01-10 10:31:11.647000');
INSERT INTO `wallet` (`user_id`, `balance`, `pin`, `updated_at`) VALUES (3, 1000000, '123456', '2023-01-14 23:58:07.644000');
INSERT INTO `wallet` (`user_id`, `balance`, `pin`, `updated_at`) VALUES (4, 1000000, '123456', '2023-01-15 09:34:13.644000');


INSERT INTO `account` (`user_id`, `image`, `name`, `updated_at`) VALUES (1, NULL, 'Ilham Ferdian', '2023-01-10 10:44:14.762000');
INSERT INTO `account` (`user_id`, `image`, `name`, `updated_at`) VALUES (2, NULL, 'Ilham Ferdian 2', '2023-01-10 10:44:16.682000');
INSERT INTO `account` (`user_id`, `image`, `name`, `updated_at`) VALUES (3, 'https://randomuser.me/api/portraits/men/18.jpg', 'michael', '2023-01-14 23:59:07.644000');
INSERT INTO `account` (`user_id`, `image`, `name`, `updated_at`) VALUES (4, 'https://randomuser.me/api/portraits/men/78.jpg', 'Michael Jacob', '2023-01-15 09:35:13.644000');


INSERT INTO `transaction` (`id`, `amount`, `created_at`, `notes`, `status`, `updated_at`, `from_user_id`, `to_user_id`) VALUES (1, 100000,'2023-01-10 09:34:13.644000' ,NULL,b'1' ,'2023-01-10 09:34:13.644000',4,3);
INSERT INTO `transaction` (`id`, `amount`, `created_at`, `notes`, `status`, `updated_at`, `from_user_id`, `to_user_id`) VALUES (2, 100000,'2023-01-11 09:34:13.644000' ,NULL,b'1' ,'2023-01-11 09:34:13.644000',4,3);
INSERT INTO `transaction` (`id`, `amount`, `created_at`, `notes`, `status`, `updated_at`, `from_user_id`, `to_user_id`) VALUES (3, 100000,'2023-01-12 09:34:13.644000' ,NULL,b'1' ,'2023-01-12 09:34:13.644000',4,3);
INSERT INTO `transaction` (`id`, `amount`, `created_at`, `notes`, `status`, `updated_at`, `from_user_id`, `to_user_id`) VALUES (4, 100000,'2023-01-13 09:34:13.644000' ,NULL,b'1' ,'2023-01-13 09:34:13.644000',4,3);
INSERT INTO `transaction` (`id`, `amount`, `created_at`, `notes`, `status`, `updated_at`, `from_user_id`, `to_user_id`) VALUES (5, 100000,'2023-01-14 09:34:13.644000' ,NULL,b'1' ,'2023-01-14 09:34:13.644000',4,3);

INSERT INTO `transaction` (`id`, `amount`, `created_at`, `notes`, `status`, `updated_at`, `from_user_id`, `to_user_id`) VALUES (6, 200000,'2023-01-10 09:35:13.644000' ,NULL,b'1' ,'2023-01-10 09:34:13.644000',3,4);
INSERT INTO `transaction` (`id`, `amount`, `created_at`, `notes`, `status`, `updated_at`, `from_user_id`, `to_user_id`) VALUES (7, 200000,'2023-01-11 09:35:13.644000' ,NULL,b'1' ,'2023-01-11 09:34:13.644000',3,4);
INSERT INTO `transaction` (`id`, `amount`, `created_at`, `notes`, `status`, `updated_at`, `from_user_id`, `to_user_id`) VALUES (8, 100000,'2023-01-12 09:35:13.644000' ,NULL,b'1' ,'2023-01-12 09:34:13.644000',3,4);
