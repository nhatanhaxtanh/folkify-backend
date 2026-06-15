-- ==================== INSTRUMENT ====================
UPDATE instruments SET
    slug         = 'dan-ty-ba',
    name         = 'Đàn Tỳ Bà',
    english_name = 'Dan Ty Ba',
    region       = 'Toàn quốc',
    category     = 'Dây gảy',
    emoji        = '🪕',
    color        = '#92400E',
    image_url    = 'https://upload.wikimedia.org/wikipedia/commons/thumb/4/4e/Vietnamese_ty_ba.jpg/800px-Vietnamese_ty_ba.jpg',
    short_desc   = 'Đàn lute bốn dây hình quả lê, âm thanh trong sáng và trữ tình',
    description  = 'Đàn tỳ bà là nhạc cụ dây gảy bốn dây hình quả lê của Việt Nam, có nguồn gốc từ Trung Á và được Việt hóa qua nhiều thế kỷ. Tiếng đàn tỳ bà trong sáng, ấm áp và đầy biểu cảm, thường xuất hiện trong nhã nhạc cung đình và nhạc thính phòng.',
    origin       = 'Có nguồn gốc từ Trung Á, du nhập vào Việt Nam qua Con đường Tơ lụa',
    material     = 'Gỗ ngô đồng, gỗ trắc, dây nilon hoặc dây tơ',
    sound_range  = '3 quãng tám',
    difficulty   = 4,
    popularity   = 4
WHERE id = 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a05';

-- ==================== FACTS ====================
DELETE FROM instrument_facts WHERE instrument_id = 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a05';
INSERT INTO instrument_facts (instrument_id, fact, order_index) VALUES
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a05', 'Đàn tỳ bà từng là nhạc cụ chủ đạo trong nhã nhạc cung đình triều Nguyễn', 0),
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a05', 'Tên "tỳ bà" mô phỏng âm thanh gảy xuống (tỳ) và gảy lên (bà) của đàn', 1);

-- ==================== LESSON ====================
UPDATE lessons SET
    slug        = 'dtb-01',
    title       = 'Làm quen với đàn tỳ bà',
    duration    = '20 phút',
    level       = 'Beginner',
    description = 'Tìm hiểu cấu tạo, cách cầm đàn và tư thế ngồi đúng.',
    xp          = 50
WHERE id = 'b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b09';

-- ==================== LESSON STEPS ====================
DELETE FROM lesson_steps WHERE lesson_id = 'b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b09';
INSERT INTO lesson_steps (lesson_id, step, order_index) VALUES
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b09', 'Nhận biết các bộ phận: thùng đàn, cần đàn, phím và bốn dây', 0),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b09', 'Đặt đàn nghiêng trên đùi, thùng đàn tựa nhẹ vào ngực', 1),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b09', 'Tay phải gảy dây bằng móng tay hoặc miếng gảy (plectrum)', 2),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b09', 'Tay trái bấm phím, ngón tay đặt sát phím để âm rõ và không bị tắt', 3);

-- ==================== LESSON TIPS ====================
DELETE FROM lesson_tips WHERE lesson_id = 'b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b09';
INSERT INTO lesson_tips (lesson_id, tip, order_index) VALUES
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b09', 'Giữ lưng thẳng, vai thả lỏng khi ngồi để chơi lâu không mỏi', 0),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b09', 'Móng tay phải để dài vừa phải để gảy nhẹ nhàng và có kiểm soát', 1);

-- ==================== SONGS ====================
UPDATE instrument_songs SET title = 'Tứ đại cảnh', artist = 'Nhã nhạc cung đình Huế', duration = '4:20'
WHERE id = 'c0eebc99-9c0b-4ef8-bb6d-6bb9bd380c10';

UPDATE instrument_songs SET title = 'Lưu thủy', artist = 'Nhạc cổ truyền', duration = '3:50'
WHERE id = 'c0eebc99-9c0b-4ef8-bb6d-6bb9bd380c11';
