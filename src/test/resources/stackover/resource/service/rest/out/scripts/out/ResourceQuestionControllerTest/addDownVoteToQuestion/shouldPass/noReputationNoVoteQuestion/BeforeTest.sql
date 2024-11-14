INSERT INTO user_entity (id, dtype, account_id, full_name, persist_date, city, link_site, link_github, link_vk, about, image_link, last_redaction_date, nickname)
VALUES
    (101, 'association', 1001, 'Иван Иванов', '2022-02-24 00:00:00', 'Санкт-Петербург', 'https://ivivanov.ru', 'https://github.com/ivivanov', 'https://vk.com/ivivanov', 'Иван (Иванов)', 'https://ivivanov.ru/profile.jpg', '2022-02-24 12:00:00', 'IvIvanov'),
    (102, 'association', 1002, 'Алексей Алексеев', '2022-02-24 00:00:00', 'Санкт-Петербург', 'https://alalexeev.ru', 'https://github.com/alalexeev', 'https://vk.com/alalexeev', 'Алексей (Алексеев)', 'https://alalexeev.ru/profile.jpg', '2022-02-24 12:00:00', 'AlAlexeev');

INSERT INTO question (id, title, description, persist_date, account_id, last_redaction_date, is_deleted)
VALUES (101, 'Заголовок вопроса', 'Описание вопроса', '2024-02-24 00:00:00', 101, '2024-02-22 12:00:00', false);
