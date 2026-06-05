-- ==================== INSTRUMENTS ====================
INSERT INTO instruments (id, slug, name, english_name, region, category, emoji, color, image_url, short_desc, description, origin, material, sound_range, difficulty, popularity) VALUES
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a01', 'dan-tranh', 'Đàn Tranh', 'Dan Tranh', 'Toàn quốc', 'Dây gảy', '🎵', '#B45309',
 'https://upload.wikimedia.org/wikipedia/commons/thumb/3/33/%C4%90%C3%A0n_Tranh.jpg/800px-%C4%90%C3%A0n_Tranh.jpg',
 'Đàn tranh 16 dây, âm thanh trong trẻo, thanh thoát',
 'Đàn tranh là nhạc cụ dây gảy truyền thống của Việt Nam, có 16 dây kim loại căng trên thân đàn hình thang dài. Tiếng đàn tranh trong trẻo, thanh thoát như tiếng suối chảy, thường được dùng trong nhạc thính phòng và biểu diễn dân tộc.',
 'Trung Quốc, du nhập vào Việt Nam khoảng thế kỷ 17', 'Gỗ, dây kim loại, nhựa tổng hợp', '3.5 quãng tám', 3, 5),

('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a02', 'dan-bau', 'Đàn Bầu', 'Dan Bau', 'Toàn quốc', 'Dây', '🎶', '#7C3AED',
 'https://upload.wikimedia.org/wikipedia/commons/thumb/6/69/Dan_Bau.jpg/800px-Dan_Bau.jpg',
 'Đàn một dây độc đáo với âm thanh huyền bí',
 'Đàn bầu là nhạc cụ độc đáo của Việt Nam, chỉ có một dây nhưng có thể tạo ra vô số cao độ khác nhau. Tiếng đàn bầu mang âm hưởng huyền bí, sâu lắng, được ví như tiếng khóc của linh hồn.',
 'Việt Nam, có lịch sử hơn 1000 năm', 'Tre, dây kim loại, vỏ bầu khô', '3 quãng tám', 4, 4),

('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a03', 'dan-nguyet', 'Đàn Nguyệt', 'Dan Nguyet', 'Toàn quốc', 'Dây gảy', '🌙', '#0369A1',
 'https://upload.wikimedia.org/wikipedia/commons/thumb/4/4f/Vietnamese_moon_lute.jpg/800px-Vietnamese_moon_lute.jpg',
 'Đàn hình trăng, âm thanh tươi sáng và linh hoạt',
 'Đàn nguyệt (đàn kìm) là nhạc cụ dây gảy có thân đàn hình tròn như mặt trăng. Với hai dây tơ hoặc nilon, đàn nguyệt tạo ra âm thanh tươi sáng, vui tươi.',
 'Trung Quốc, du nhập vào Việt Nam', 'Gỗ quý, dây tơ hoặc nilon', '2.5 quãng tám', 3, 4),

('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a04', 'sao-truc', 'Sáo Trúc', 'Sao Truc', 'Toàn quốc', 'Hơi', '🎋', '#15803D',
 'https://upload.wikimedia.org/wikipedia/commons/thumb/e/e6/Vietnamese_bamboo_flute.jpg/800px-Vietnamese_bamboo_flute.jpg',
 'Sáo tre với âm thanh trong sáng, du dương',
 'Sáo trúc là nhạc cụ thổi làm từ tre/trúc, phổ biến nhất trong các nhạc cụ dân gian Việt Nam. Tiếng sáo trúc trong sáng, du dương, gợi nhớ đến làng quê yên bình.',
 'Việt Nam bản địa, có từ thời tiền sử', 'Tre hoặc trúc già', '2 quãng tám', 2, 5),

('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a05', 'trong-com', 'Trống Cơm', 'Trong Com', 'Toàn quốc', 'Gõ', '🥁', '#B91C1C',
 'https://upload.wikimedia.org/wikipedia/commons/thumb/9/9d/Vietnamese_barrel_drum.jpg/800px-Vietnamese_barrel_drum.jpg',
 'Trống hai mặt, tiết tấu phong phú và sôi động',
 'Trống cơm là loại trống hai mặt của Việt Nam, được làm từ thân gỗ và da trâu. Âm thanh trống cơm mạnh mẽ, sôi động, thường được dùng trong lễ hội và biểu diễn sân khấu.',
 'Việt Nam, có từ thời đại Đông Sơn', 'Gỗ mít, da trâu', '1 quãng tám', 2, 4),

('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a06', 'dan-nhi', 'Đàn Nhị', 'Dan Nhi', 'Toàn quốc', 'Dây kéo', '🎻', '#C2410C',
 'https://upload.wikimedia.org/wikipedia/commons/thumb/8/87/Dan_Nhi.jpg/800px-Dan_Nhi.jpg',
 'Đàn hai dây kéo, âm thanh ấm áp và diễn cảm',
 'Đàn nhị là nhạc cụ dây kéo hai dây của Việt Nam, tương tự đàn erhu của Trung Quốc. Tiếng đàn nhị ấm áp, có thể biểu đạt nhiều cung bậc cảm xúc từ vui tươi đến u buồn.',
 'Du nhập từ Trung Quốc, được Việt hóa qua nhiều thế kỷ', 'Gỗ, da trăn hoặc da rắn, dây nilon', '3 quãng tám', 4, 3);

-- ==================== FACTS ====================
INSERT INTO instrument_facts (instrument_id, fact, order_index) VALUES
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a01', 'Đàn tranh có nguồn gốc từ nhạc cụ Trung Quốc tên là Guzheng', 0),
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a01', 'Trong âm nhạc cổ truyền Việt Nam, đàn tranh thường được dùng để diễn tấu nhạc thính phòng', 1),
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a01', 'Có thể tạo ra âm thanh rung bằng kỹ thuật nhấn dây bằng tay trái', 2),
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a01', 'Đàn tranh hiện đại có 16 dây, nhưng các phiên bản cổ xưa chỉ có 15 dây', 3),

('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a02', 'Đàn bầu là nhạc cụ duy nhất trên thế giới chỉ có một dây nhưng tạo ra âm thanh đa dạng', 0),
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a02', 'Âm thanh của đàn bầu được tạo ra từ nguyên lý cộng hưởng harmonics', 1),
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a02', 'Đàn bầu từng bị cấm biểu diễn ở một số triều đại vì cho rằng tiếng đàn quá thê lương', 2),
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a02', 'UNESCO đã công nhận đàn bầu là Di sản văn hóa phi vật thể của Việt Nam', 3),

('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a03', 'Đàn nguyệt được dùng phổ biến trong hát chầu văn và cải lương', 0),
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a03', 'Thân đàn hình tròn tượng trưng cho mặt trăng - biểu tượng của sự thanh cao', 1),
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a03', 'Đàn nguyệt có mặt trong các dàn nhạc cung đình từ thời nhà Nguyễn', 2),

('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a04', 'Sáo trúc là nhạc cụ phổ biến nhất ở nông thôn Việt Nam', 0),
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a04', 'Mỗi vùng miền có kiểu sáo và điệu thổi khác nhau', 1),
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a04', 'Tiếng sáo trúc xuất hiện trong hầu hết các thể loại âm nhạc dân gian', 2),

('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a05', 'Trống cơm xuất hiện trên trống đồng Đông Sơn cách đây hơn 2000 năm', 0),
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a05', 'Tiếng trống cơm là biểu tượng của lễ hội và sự may mắn trong văn hóa Việt', 1),

('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a06', 'Đàn nhị thường được dùng để dẫn giai điệu trong dàn nhạc dân tộc', 0),
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a06', 'Tiếng đàn nhị gần với giọng người nhất trong các nhạc cụ dân tộc', 1);

-- ==================== LESSONS ====================
INSERT INTO lessons (id, instrument_id, slug, title, duration, level, description, xp, youtube_url, order_index) VALUES
-- Đàn Tranh
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b01', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a01', 'dt-01', 'Tư thế ngồi và cách cầm đàn', '15 phút', 'Beginner', 'Học cách ngồi đúng tư thế và cầm đàn tranh chuẩn xác để tránh chấn thương và tạo âm thanh đẹp.', 50, 'https://www.youtube.com/embed/example1', 0),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b02', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a01', 'dt-02', 'Làm quen với các dây đàn', '20 phút', 'Beginner', 'Tìm hiểu về hệ thống dây đàn và cách định âm của đàn tranh.', 75, NULL, 1),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b03', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a01', 'dt-03', 'Kỹ thuật gảy cơ bản', '30 phút', 'Beginner', 'Học các kỹ thuật gảy dây cơ bản: gảy đơn, gảy kép và vê.', 100, NULL, 2),
-- Đàn Bầu
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b04', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a02', 'db-01', 'Giới thiệu về đàn bầu', '15 phút', 'Beginner', 'Tìm hiểu cấu tạo và nguyên lý hoạt động của đàn bầu.', 60, NULL, 0),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b05', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a02', 'db-02', 'Kỹ thuật rung cần', '25 phút', 'Beginner', 'Học kỹ thuật rung cần để tạo ra các cao độ khác nhau.', 90, NULL, 1),
-- Đàn Nguyệt
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b06', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a03', 'dn-01', 'Làm quen với đàn nguyệt', '20 phút', 'Beginner', 'Tìm hiểu cấu tạo và cách cầm đàn nguyệt đúng cách.', 55, NULL, 0),
-- Sáo Trúc
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b07', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a04', 'st-01', 'Cách thổi sáo đúng kỹ thuật', '20 phút', 'Beginner', 'Học cách đặt miệng và thổi hơi đúng để tạo ra âm thanh sáo đẹp.', 40, NULL, 0),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b08', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a04', 'st-02', 'Các nốt cơ bản Đồ, Rê, Mi', '25 phút', 'Beginner', 'Học cách bấm ngón để tạo ra các nốt nhạc cơ bản.', 70, NULL, 1),
-- Trống Cơm
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b09', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a05', 'tc-01', 'Kỹ thuật gõ trống cơ bản', '20 phút', 'Beginner', 'Học cách cầm dùi và gõ trống đúng kỹ thuật.', 45, NULL, 0),
-- Đàn Nhị
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b10', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a06', 'dnhi-01', 'Làm quen với đàn nhị', '20 phút', 'Beginner', 'Tìm hiểu cấu tạo và cách cầm cung đàn nhị.', 65, NULL, 0);

-- ==================== LESSON STEPS ====================
INSERT INTO lesson_steps (lesson_id, step, order_index) VALUES
-- dt-01
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b01', 'Ngồi thẳng lưng trên ghế, hai chân chạm sàn', 0),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b01', 'Đặt đàn nghiêng 45 độ so với cơ thể', 1),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b01', 'Đeo móng gảy vào ngón cái, trỏ và giữa của tay phải', 2),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b01', 'Tay trái đặt nhẹ lên các dây phía đầu trụ', 3),
-- dt-02
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b02', 'Xác định dây số 1 (dây trầm nhất) và dây số 16 (cao nhất)', 0),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b02', 'Luyện gảy từng dây một theo thứ tự', 1),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b02', 'Nghe và cảm nhận sự khác biệt về cao độ', 2),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b02', 'Thực hành gảy liên tiếp các dây từ trầm đến cao', 3),
-- dt-03
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b03', 'Gảy đơn: dùng một ngón gảy một dây', 0),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b03', 'Gảy kép: gảy đồng thời hai dây cách nhau một quãng', 1),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b03', 'Vê: gảy liên tiếp nhanh trên một dây', 2),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b03', 'Kết hợp ba kỹ thuật trong bài tập nhỏ', 3),
-- db-01
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b04', 'Nhận biết các bộ phận: thân đàn, cần đàn, dây đàn, bầu cộng hưởng', 0),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b04', 'Hiểu nguyên lý tạo âm thanh qua sóng harmonics', 1),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b04', 'Học cách cầm que gảy đúng cách', 2),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b04', 'Thực hành gảy nhẹ để nghe âm thanh cơ bản', 3),
-- db-02
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b05', 'Giữ cần đàn nhẹ nhàng giữa ngón cái và ngón trỏ trái', 0),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b05', 'Thực hành rung cần nhẹ để tạo ra âm thanh rung', 1),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b05', 'Luyện di chuyển cần để thay đổi cao độ', 2),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b05', 'Kết hợp gảy dây và rung cần đồng thời', 3),
-- dn-01
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b06', 'Nhận biết các bộ phận: thùng đàn, cần đàn, trục lên dây', 0),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b06', 'Học cách cầm đàn và tư thế ngồi', 1),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b06', 'Làm quen với phím đàn và vị trí các nốt cơ bản', 2),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b06', 'Thực hành gảy dây mở', 3),
-- st-01
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b07', 'Đặt lỗ thổi vào môi dưới, hướng lỗ thổi ra ngoài 45 độ', 0),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b07', 'Thổi hơi nhẹ và liên tục qua kẽ môi', 1),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b07', 'Điều chỉnh góc thổi để tìm âm thanh đẹp nhất', 2),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b07', 'Thực hành thổi một âm duy nhất trong 10 giây', 3),
-- st-02
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b08', 'Bịt kín tất cả 6 lỗ để tạo nốt Đồ thấp', 0),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b08', 'Mở lỗ cuối cùng để tạo nốt Rê', 1),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b08', 'Tiếp tục mở dần các lỗ để tạo các nốt tiếp theo', 2),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b08', 'Luyện chuyển đổi giữa các nốt một cách mượt mà', 3),
-- tc-01
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b09', 'Cầm dùi giữa ngón cái và ngón trỏ, tay thả lỏng', 0),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b09', 'Gõ vào giữa mặt trống để tạo âm đầy', 1),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b09', 'Gõ vào cạnh mặt trống để tạo âm khác', 2),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b09', 'Luyện nhịp 4/4 cơ bản', 3),
-- dnhi-01
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b10', 'Nhận biết các bộ phận: thùng đàn, cần đàn, cung kéo', 0),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b10', 'Học cách cầm cung kéo bằng tay phải', 1),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b10', 'Đặt đàn lên đùi hoặc dùng giá đỡ', 2),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b10', 'Thực hành kéo cung tạo âm thanh đầu tiên', 3);

-- ==================== LESSON TIPS ====================
INSERT INTO lesson_tips (lesson_id, tip, order_index) VALUES
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b01', 'Giữ lưng thẳng trong suốt buổi tập', 0),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b01', 'Thư giãn vai và cánh tay', 1),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b01', 'Không ép buộc - hãy để cơ thể quen dần', 2),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b02', 'Dùng tai để nghe và điều chỉnh lực gảy', 0),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b02', 'Gảy nhẹ nhàng, đừng dùng quá nhiều lực', 1),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b03', 'Luyện chậm trước, tốc độ sẽ đến sau', 0),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b03', 'Đảm bảo âm thanh đều và rõ ràng', 1),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b04', 'Đàn bầu cần không gian yên tĩnh để luyện tập', 0),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b04', 'Điều chỉnh căng dây phù hợp với độ tuổi và sức mạnh tay', 1),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b05', 'Rung cần nhẹ nhàng, đừng dùng quá nhiều lực', 0),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b05', 'Nghe kỹ để cảm nhận sự thay đổi cao độ', 1),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b06', 'Đàn nguyệt dễ học hơn nhiều loại đàn khác', 0),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b06', 'Hãy kiên nhẫn với giai đoạn đầu', 1),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b07', 'Hít thở bằng bụng, không phải bằng ngực', 0),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b07', 'Kiên trì - cần vài tuần để tạo ra âm thanh ổn định', 1),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b08', 'Đảm bảo ngón tay bịt kín lỗ để không bị rò hơi', 0),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b08', 'Luyện từng nốt riêng lẻ trước khi kết hợp', 1),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b09', 'Thả lỏng cổ tay khi gõ', 0),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b09', 'Lắng nghe nhịp và cảm nhận tiết tấu', 1),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b10', 'Đàn nhị khó hơn đàn tranh, cần nhiều kiên nhẫn', 0),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b10', 'Đặt nhựa thông đủ lên cung trước khi kéo', 1);

-- ==================== SONGS ====================
INSERT INTO songs (id, instrument_id, title, artist, duration, order_index) VALUES
('c0eebc99-9c0b-4ef8-bb6d-6bb9bd380c01', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a01', 'Lý con sáo', 'Dân ca Nam Bộ', '3:45', 0),
('c0eebc99-9c0b-4ef8-bb6d-6bb9bd380c02', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a01', 'Bèo dạt mây trôi', 'Dân ca Quan họ', '4:20', 1),
('c0eebc99-9c0b-4ef8-bb6d-6bb9bd380c03', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a01', 'Xuân về trên bản', 'Nhạc dân tộc', '5:10', 2),
('c0eebc99-9c0b-4ef8-bb6d-6bb9bd380c04', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a02', 'Tiếng đàn bầu', 'Nhạc cổ truyền', '4:30', 0),
('c0eebc99-9c0b-4ef8-bb6d-6bb9bd380c05', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a02', 'Lý ngựa ô', 'Dân ca miền Trung', '3:55', 1),
('c0eebc99-9c0b-4ef8-bb6d-6bb9bd380c06', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a03', 'Đà cổ hoài lang', 'Cải lương', '5:20', 0),
('c0eebc99-9c0b-4ef8-bb6d-6bb9bd380c07', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a03', 'Lưu thủy hành vân', 'Nhạc tế lễ', '6:00', 1),
('c0eebc99-9c0b-4ef8-bb6d-6bb9bd380c08', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a04', 'Lý con sáo', 'Dân ca Nam Bộ', '3:30', 0),
('c0eebc99-9c0b-4ef8-bb6d-6bb9bd380c09', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a04', 'Xuân về trên bản', 'Nhạc dân tộc', '4:15', 1),
('c0eebc99-9c0b-4ef8-bb6d-6bb9bd380c10', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a05', 'Trống hội', 'Nhạc lễ hội', '3:00', 0),
('c0eebc99-9c0b-4ef8-bb6d-6bb9bd380c11', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a05', 'Múa lân', 'Nhạc tết', '2:45', 1),
('c0eebc99-9c0b-4ef8-bb6d-6bb9bd380c12', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a06', 'Bèo dạt mây trôi', 'Dân ca Quan họ', '4:00', 0),
('c0eebc99-9c0b-4ef8-bb6d-6bb9bd380c13', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a06', 'Lý ngựa ô', 'Dân ca miền Trung', '3:40', 1);
