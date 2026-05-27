INSERT INTO tb_users
(id, name, email, password, status, role, created_at, updated_at)
VALUES
    (
        gen_random_uuid(),
        'Admin Demo',
        'admin@demo.com',
        '$2a$12$wqV1V7QpxCe0HsTOL4qfSuCHj1Jw8h55W93NccD88fRoGeYnXdq/m',
        'REGISTERED',
        'ADMIN',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    )
ON CONFLICT (email) DO NOTHING;