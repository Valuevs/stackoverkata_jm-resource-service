INSERT INTO user_entity (id, dtype, account_id, full_name, persist_date, city, link_site, link_github, link_vk, about, image_link, last_redaction_date, nickname)
VALUES
    (101, 'association', 1001, 'Алексей Иванов', '2023-09-10 14:25:00', 'Москва', 'https://alexey-ivanov.ru', 'https://github.com/alexeyivanov', 'https://vk.com/alexey', 'Frontend разработчик', 'https://alexey-ivanov.ru/profile.jpg', '2023-09-18 11:00:00', 'AlexIvanov'),
    (102, 'association', 1002, 'Мария Петрова', '2023-09-11 09:15:00', 'Санкт-Петербург', 'https://maria-petrova.ru', 'https://github.com/mariapetrova', 'https://vk.com/maria', 'Fullstack разработчик', 'https://maria-petrova.ru/profile.jpg', '2023-09-18 11:20:00', 'MariaPetrova'),
    (103, 'association', 1003, 'Иван Сидоров', '2023-09-12 12:45:00', 'Новосибирск', 'https://ivan-sidorov.ru', 'https://github.com/ivansidorov', 'https://vk.com/ivan', 'Backend разработчик', 'https://ivan-sidorov.ru/profile.jpg', '2023-09-18 12:15:00', 'IvanSid'),
    (104, 'association', 1004, 'Ольга Смирнова', '2023-09-13 16:30:00', 'Екатеринбург', 'https://olga-smirnova.ru', 'https://github.com/olgasmirnova', 'https://vk.com/olga', 'Дизайнер', 'https://olga-smirnova.ru/profile.jpg', '2023-09-18 12:40:00', 'OlgaSmi'),
    (105, 'association', 1005, 'Дмитрий Кузнецов', '2023-09-14 08:50:00', 'Казань', 'https://dmitry-kuznetsov.ru', 'https://github.com/dmitrykuznetsov', 'https://vk.com/dmitry', 'DevOps инженер', 'https://dmitry-kuznetsov.ru/profile.jpg', '2023-09-18 13:20:00', 'DmitryKuz');

INSERT INTO question (id, title, description, persist_date, account_id, last_redaction_date, is_deleted)
VALUES
    (101, 'Как улучшить производительность сайта?', 'Какие техники и инструменты можно использовать для оптимизации загрузки веб-страниц и снижения времени отклика?', '2023-09-15 10:30:00', 101, '2023-09-15 11:00:00', false),
    (102, 'Какие есть подходы к CI/CD в DevOps?', 'Какие инструменты чаще всего используются для организации процессов CI/CD в современных командах DevOps?', '2023-09-16 14:15:00', 102, '2023-09-16 14:45:00', false);

INSERT INTO answer (id, persist_date, update_date, question_id, account_id, html_body, is_helpful, is_deleted, is_deleted_by_moderator, date_accept_time)
VALUES
    -- Ответы на вопрос 101
    (101, '2023-09-20 11-30', '2023-09-20 11:30:00', 101, 102, '<p>Используйте кэширование и минимизацию ресурсов для ускорения загрузки сайта.</p>', false, false, false, '2023-09-20 12:00:00'),
    (102, '2023-09-21 12-30', '2023-09-21 12:30:00', 101, 103, '<p>Попробуйте оптимизировать изображения, используя современные форматы, такие как WebP.</p>', false, false, false, '2023-09-21 14:30:00'),
    (103, '2023-09-21 13-30', '2023-09-21 13:30:00', 101, 104, '<p>Асинхронная загрузка ресурсов и ленивое их выполнение может помочь в оптимизации.</p>', false, false, false, '2023-09-21 16:45:00'),
    (104, '2023-09-22 14-30', '2023-09-22 14:30:00', 101, 105, '<p>CDN-сеть и сжатие файлов помогут ускорить работу сайта.</p>', true, false, false, '2023-09-22 11:15:00'),
    -- Ответы на вопрос 102
    (105, '2023-09-23 11-30', '2023-09-23 13:00:00', 102, 103, '<p>Jenkins — один из самых популярных инструментов для CI/CD.</p>', false, false, false, '2023-09-23 13:30:00'),
    (106, '2023-09-24 12-30', '2023-09-24 09:45:00', 102, 104, '<p>Попробуйте CircleCI — он позволяет гибко настроить процессы CI/CD.</p>', false, false, false, '2023-09-24 10:15:00'),
    (107, '2023-09-24 13-30', '2023-09-24 11:15:00', 102, 105, '<p>GitHub Actions — это удобный способ автоматизации через GitHub.</p>', true, false, false, '2023-09-24 11:45:00'),
    (108, '2023-09-24 14-30', '2023-09-24 12:30:00', 102, 101, '<p>Buddy — ещё один инструмент для простого CI/CD.</p>', false, false, false, '2023-09-24 13:00:00');

-- INSERT INTO votes_on_answers (id, user_id, answer_id, persist_date, vote_type_answer)
-- VALUES
--     (1, 101, 1, '2023-09-21 10:00:00', 'UP'),
--     (2, 103, 1, '2023-09-21 10:00:00', 'UP'),
--     (3, 104, 1, '2023-09-21 10:00:00', 'UP'),
--     (4, 105, 1, '2023-09-21 10:00:00', 'UP'),
--     (5, 101, 2, '2023-09-21 10:00:00', 'UP'),
--     (6, 102, 3, '2023-09-21 10:00:00', 'UP'),
--     (7, 103, 3, '2023-09-21 10:00:00', 'DOWN'),
--     (8, 104, 4, '2023-09-21 10:00:00', 'UP'),
--
--     (9, 101, 6, '2023-09-21 10:30:00', 'UP'),
--     (10, 102, 6, '2023-09-21 10:30:00', 'UP'),
--     (11, 103, 6, '2023-09-21 10:30:00', 'UP'),
--     (12, 102, 5, '2023-09-21 10:30:00', 'DOWN'),
--     (13, 102, 7, '2023-09-21 10:30:00', 'DOWN'),
--     (14, 103, 8, '2023-09-21 10:30:00', 'UP'),
--     (15, 104, 8, '2023-09-21 10:30:00', 'UP'),
--     (16, 105, 8, '2023-09-21 10:30:00', 'DOWN');

-- insert into reputation (id, persist_date, author_id, sender_id, count, type, question_id, answer_id)
-- values
--     (1, '2023-09-21 10:30:00', 101, 103, 1, 1, 102, 8),
--     (2, '2023-09-21 10:30:00', 101, 104, 1, 1, 102, 8),
--     (3, '2023-09-21 10:30:00', 101, 105, -1, 1, 102, 8),
--
--     (4, '2023-09-21 10:30:00', 102, 101, 1, 1, 101, 1),
--     (5, '2023-09-21 10:30:00', 102, 103, 1, 1, 101, 1),
--     (6, '2023-09-21 10:30:00', 102, 104, 1, 1, 101, 1),
--     (7, '2023-09-21 10:30:00', 102, 105, 1, 1, 101, 1),
--
--     (8, '2023-09-21 10:30:00', 103, 101, 1, 1, 101, 2),
--     (9, '2023-09-21 10:30:00', 103, 102, -1, 1, 102, 5),
--
--     (10, '2023-09-21 10:30:00', 104, 102, 1, 1, 101, 3),
--     (11, '2023-09-21 10:30:00', 104, 103, -1, 1, 101, 3),
--     (12, '2023-09-21 10:30:00', 104, 101, 1, 1, 102, 6),
--     (13, '2023-09-21 10:30:00', 104, 102, 1, 1, 102, 6),
--     (14, '2023-09-21 10:30:00', 104, 103, 1, 1, 102, 6),
--
--     (15, '2023-09-21 10:30:00', 105, 104, 1, 1, 101, 4),
--     (16, '2023-09-21 10:30:00', 105, 102, -1, 1, 102, 7);
