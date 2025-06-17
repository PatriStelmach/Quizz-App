-- Insert 5 users
INSERT INTO _user (username, password, email, av_name, av_type, bio, is_active, account_locked, enabled, created_at, updated_at, birth_date, user_level, streak, score)
VALUES
    ('user1', '$2a$10$xJwL5v5Jz5Jz5Jz5Jz5Jz.5Jz5Jz5Jz5Jz5Jz5Jz5Jz5Jz5Jz5J', 'user1@example.com', 'Avatar1', 'png', 'Bio for user1', true, false, true, NOW(), NULL, '1990-01-01', 'NEWBIE', 0, 0),
    ('user2', '$2a$10$xJwL5v5Jz5Jz5Jz5Jz5Jz.5Jz5Jz5Jz5Jz5Jz5Jz5Jz5Jz5Jz5J', 'user2@example.com', 'Avatar2', 'png', 'Bio for user2', true, false, true, NOW(), NULL, '1991-02-02', 'NOOB', 1, 100),
    ('user3', '$2a$10$xJwL5v5Jz5Jz5Jz5Jz5Jz.5Jz5Jz5Jz5Jz5Jz5Jz5Jz5Jz5Jz5J', 'user3@example.com', 'Avatar3', 'png', 'Bio for user3', true, false, true, NOW(), NULL, '1992-03-03', 'CASUAL', 3, 300),
    ('user4', '$2a$10$xJwL5v5Jz5Jz5Jz5Jz5Jz.5Jz5Jz5Jz5Jz5Jz5Jz5Jz5Jz5Jz5J', 'user4@example.com', 'Avatar4', 'png', 'Bio for user4', true, false, true, NOW(), NULL, '1993-04-04', 'PRO', 7, 700),
    ('user5', '$2a$10$xJwL5v5Jz5Jz5Jz5Jz5Jz.5Jz5Jz5Jz5Jz5Jz5Jz5Jz5Jz5Jz5J', 'user5@example.com', 'Avatar5', 'png', 'Bio for user5', true, false, true, NOW(), NULL, '1994-05-05', 'MASTER', 15, 1500);

-- Insert roles for users (assuming ROLE_USER exists)
INSERT INTO _user_roles (users_id, roles_id)
VALUES
    (1, 1),
    (2, 1),
    (3, 1),
    (4, 1),
    (5, 1);

-- Insert 5 quizzes (each with different category and author)
INSERT INTO quiz (title, description, category, diff, author_id, created_at, time_limit, max_points)
VALUES
    ('Math Basics', 'Basic mathematics quiz for beginners', 'MATHEMATICS', 'EASY', 1, NOW(), 150, 100),
    ('World History', 'Test your knowledge of world history', 'HISTORY', 'MEDIUM', 2, NOW(), 300, 150),
    ('Science Facts', 'Interesting science questions', 'SCIENCE', 'HARD', 3, NOW(), 700, 200),
    ('Geography Challenge', 'How well do you know the world?', 'GEOGRAPHY', 'EXPERT', 4, NOW(), 150, 180),
    ('Tech Quiz', 'Test your technology knowledge', 'TECHNOLOGY', 'MEDIUM', 5, NOW(), 800, 120);

-- Insert questions for Math Basics quiz (Quiz ID 1)
INSERT INTO question (question_type, question, quiz_id)
VALUES
    ('SINGLE_CHOICE', 'What is 2 + 2?', 1),
    ('SINGLE_CHOICE', 'What is the square root of 16?', 1),
    ('MULTIPLE_CHOICE', 'Which of these are prime numbers?', 1),
    ('SINGLE_CHOICE', 'What is 5 × 7?', 1),
    ('OPEN', 'Solve for x: 2x + 3 = 11', 1);

-- Insert answers for Math Basics questions
INSERT INTO answer (correct, answer, question_id)
VALUES
    (true, '4', 1),
    (false, '3', 1),
    (false, '5', 1),
    (false, '6', 1),
    (true, '4', 2),
    (false, '2', 2),
    (false, '8', 2),
    (true, '2', 3),
    (true, '3', 3),
    (false, '4', 3),
    (false, '6', 3),
    (true, '35', 4),
    (false, '30', 4),
    (false, '25', 4),
    (false, '40', 4),
    (true, 'x = 4', 5);

-- Insert questions for World History quiz (Quiz ID 2)
INSERT INTO question (question_type, question, quiz_id)
VALUES
    ('SINGLE_CHOICE', 'In which year did World War II end?', 2),
    ('MULTIPLE_CHOICE', 'Which civilizations were ancient?', 2),
    ('SINGLE_CHOICE', 'Who was the first president of the USA?', 2),
    ('OPEN', 'Name one major consequence of the Industrial Revolution', 2),
    ('SINGLE_CHOICE', 'When did the Berlin Wall fall?', 2);

-- Insert answers for World History questions
INSERT INTO answer (correct, answer, question_id)
VALUES
    (true, '1945', 6),
    (false, '1939', 6),
    (false, '1941', 6),
    (false, '1950', 6),
    (true, 'Egyptian', 7),
    (true, 'Roman', 7),
    (false, 'American', 7),
    (true, 'George Washington', 8),
    (false, 'Abraham Lincoln', 8),
    (false, 'Thomas Jefferson', 8),
    (false, 'John Adams', 8),
    (true, 'Urbanization', 9),
    (true, '1989', 10),
    (false, '1991', 10),
    (false, '1985', 10),
    (false, '1979', 10);

-- Insert questions for Science Facts quiz (Quiz ID 3)
INSERT INTO question (question_type, question, quiz_id)
VALUES
    ('SINGLE_CHOICE', 'What is the chemical symbol for gold?', 3),
    ('MULTIPLE_CHOICE', 'Which of these are noble gases?', 3),
    ('SINGLE_CHOICE', 'How many bones are in the human body?', 3),
    ('OPEN', 'What is Newton''s first law of motion?', 3),
    ('SINGLE_CHOICE', 'What is the largest planet in our solar system?', 3);

-- Insert answers for Science Facts questions
INSERT INTO answer (correct, answer, question_id)
VALUES
    (true, 'Au', 11),
    (false, 'Ag', 11),
    (false, 'Fe', 11),
    (false, 'Pb', 11),
    (true, 'Helium', 12),
    (true, 'Neon', 12),
    (false, 'Oxygen', 12),
    (true, '206', 13),
    (false, '300', 13),
    (false, '150', 13),
    (false, '250', 13),
    (true, 'An object in motion stays in motion unless acted upon by an external force', 14),
    (true, 'Jupiter', 15),
    (false, 'Saturn', 15),
    (false, 'Earth', 15),
    (false, 'Mars', 15);

-- Insert questions for Geography Challenge quiz (Quiz ID 4)
INSERT INTO question (question_type, question, quiz_id)
VALUES
    ('SINGLE_CHOICE', 'What is the capital of France?', 4),
    ('MULTIPLE_CHOICE', 'Which countries are in South America?', 4),
    ('SINGLE_CHOICE', 'What is the longest river in the world?', 4),
    ('OPEN', 'Name one country that borders the Mediterranean Sea', 4),
    ('SINGLE_CHOICE', 'Which continent is the least populated?', 4);

-- Insert answers for Geography Challenge questions
INSERT INTO answer (correct, answer, question_id)
VALUES
    (true, 'Paris', 16),
    (false, 'London', 16),
    (false, 'Berlin', 16),
    (false, 'Madrid', 16),
    (true, 'Brazil', 17),
    (true, 'Argentina', 17),
    (false, 'Mexico', 17),
    (true, 'Nile', 18),
    (false, 'Amazon', 18),
    (false, 'Yangtze', 18),
    (false, 'Mississippi', 18),
    (true, 'Spain', 19),
    (true, 'Antarctica', 20),
    (false, 'Australia', 20),
    (false, 'Africa', 20),
    (false, 'South America', 20);

-- Insert questions for Tech Quiz (Quiz ID 5)
INSERT INTO question (question_type, question, quiz_id)
VALUES
    ('SINGLE_CHOICE', 'What does "HTML" stand for?', 5),
    ('MULTIPLE_CHOICE', 'Which of these are programming languages?', 5),
    ('SINGLE_CHOICE', 'Who is considered the father of computer science?', 5),
    ('OPEN', 'Name one popular JavaScript framework', 5),
    ('SINGLE_CHOICE', 'What year was the first iPhone released?', 5);

-- Insert answers for Tech Quiz questions
INSERT INTO answer (correct, answer, question_id)
VALUES
    (true, 'HyperText Markup Language', 21),
    (false, 'Hyperlinks and Text Markup Language', 21),
    (false, 'Home Tool Markup Language', 21),
    (false, 'High Tech Markup Language', 21),
    (true, 'Java', 22),
    (true, 'Python', 22),
    (false, 'HTML', 22),
    (true, 'Alan Turing', 23),
    (false, 'Bill Gates', 23),
    (false, 'Steve Jobs', 23),
    (false, 'Tim Berners-Lee', 23),
    (true, 'React', 24),
    (true, '2007', 25),
    (false, '2005', 25),
    (false, '2010', 25),
    (false, '2000', 25);