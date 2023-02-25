DELETE FROM booksCover;
DELETE FROM booksContent;
DELETE FROM feedbacks;
DELETE FROM orders;
DELETE FROM bookmarks;
DELETE FROM books;
DELETE FROM publishers;
DELETE FROM users;

INSERT INTO publishers (id, title) VALUES
(1, 'Pearson'),
(2, 'RELX'),
(3, 'Penguin Random House'),
(4, 'Bloomsbury'),
(5, 'Hachette Livre'),
(6, 'HarperCollins'),
(7, 'Macmillan Publishers'),
(8, 'Bertelsmann'),
(9, 'Scholastic Corporation'),
(10, 'McGraw Hill Education'),
(11, 'Saga Press'),
(12, 'Drawn and Quarterly'),
(13, 'Europa Editions'),
(14, 'Autumn House Press'),
(15, 'Akashic Books'),
(16, 'Crown Publishing'),
(17, 'Entangled Publishing'),
(18, 'Severn House'),
(19, 'Grove Atlantic'),
(20, 'Mighty Media');


INSERT INTO books (id, isbn, publisherId, title, author, genre, language, publicationDate, totalCopiesNumber, freeCopiesNumber, gradesSum, gradesNumber, hasElectronicFormat, isValuable, isDeleted, description) VALUES
(1, 3333, 6, 'The Hitchhiker’s Guide to the Galaxy', 'Douglas Adams', 0, 0, DATE '2020-11-23', 12, 12, 24, 6, true, false, false, 'Go on a galactic adventure with the last human on Earth, his alien best friend, and a depressed android. Introducing younger readers to The Hitchhiker’s Guide to the Galaxy, this YA edition of the funny sci-fi classic includes an introduction from Artemis Fowl author Eoin Colfer.'),
(2, 2222, 5, 'Anna Karenina', 'Leo Tolstoy', 3, 1, DATE '2002-12-11', 25, 25, 18, 7, false, false, false, 'In 1872 the mistress of a neighbouring landowner threw herself under a train at a station near Tolstoy’s home. This gave Tolstoy the starting point he needed for composing what many believe to be the greatest novel ever written. In writing Anna Karenina he moved away from the vast historical sweep of War and Peace to tell, with extraordinary understanding, the story of an aristocratic woman who brings ruin on herself.'),
(3, 1234, 5, 'Interview With The Vampire', 'Anne Rice', 1, 0, DATE '2019-09-22', 40, 40, 19, 6, true, false, false, 'In a darkened room a young man sits telling the macabre and eerie story of his life - the story of a vampire, gifted with eternal life, cursed with an exquisite craving for human blood. When Interview with the Vampire was published the Washington Post said it was a ’thrilling, strikingly original work of the imagination, sometimes horrible, sometimes beautiful, always unforgettable’. Now, more than forty years since its release, Anne Rice’s masterpiece is more beloved than ever.'),
(4, 2345, 5, 'Dracula', 'Bram Stoker', 1, 0, DATE '2021-02-27', 12, 12, 14, 7, false, false, false, 'This classic of horror writing is composed of diary entries, letters and newspaper clippings that piece together the depraved story of the ultimate predator. A young lawyer on an assignment finds himself imprisoned in a Transylvanian castle by his mysterious host. Back at home his fiancee and friends are menaced by a malevolent force which seems intent on imposing suffering and destruction.'),
(5, 9876, 2, 'The Hobbit', 'J. R. R. Tolkien', 0, 0, DATE '2009-12-01', 55, 55, 23, 7, false, false, false, 'The Hobbit is a tale of high adventure, undertaken by a company of dwarves in search of dragon-guarded gold. A reluctant partner in this perilous quest is Bilbo Baggins, a comfort-loving unambitious hobbit, who surprises even himself by his resourcefulness and skill as a burglar.'),
(6, 1357, 2, 'Rich Dad Poor Dad', 'Robert T. Kiyosaki', 6, 2, DATE '2021-11-07', 20, 20, 16, 6, false, false, false, 'April of 2022 marks a 25-year milestone for the personal finance classic Rich Dad Poor Dad that still ranks as the 1 Personal Finance book of all time. And although 25 years have passed since Rich Dad Poor Dad was first published, readers will find that very little in the book itself has changed - and for good reason.'),
(7, 1122, 3, 'Spy x Family 3', 'Tatsuya Endo', 2, 2, DATE '2015-04-21', 15, 15, 16, 6, false, false, false, 'An action-packed comedy about a fake family that includes a spy, an assassin and a telepath! Master spy Twilight is unparalleled when it comes to going undercover on dangerous missions for the betterment of the world. But when he receives the ultimate assignment-to get married and have a kid-he may finally be in over his head!'),
(8, 2244, 3, 'Spy x Family 4', 'Tatsuya Endo', 2, 2, DATE '2016-07-24', 20, 20, 18, 6, true, false, false, 'An action-packed comedy about a fake family that includes a spy, an assassin and a telepath! Master spy Twilight is unparalleled when it comes to going undercover on dangerous missions for the betterment of the world. But when he receives the ultimate assignment-to get married and have a kid-he may finally be in over his head!'),
(9, 5544, 3, 'Spy x Family 6', 'Tatsuya Endo', 2, 2, DATE '2018-01-22', 30, 30, 26, 7, true, false, false, 'An action-packed comedy about a fake family that includes a spy, an assassin and a telepath! Master spy Twilight is unparalleled when it comes to going undercover on dangerous missions for the betterment of the world. But when he receives the ultimate assignment-to get married and have a kid-he may finally be in over his head!'),
(10, 1221, 1, 'Neuromancer', 'William Gibson', 4, 1, DATE '2009-04-13', 54, 54, 24, 7, false, false, false, 'The book that defined the cyberpunk movement, inspiring everything from The Matrix to Cyberpunk 2077.The sky above the port was the colour of television, tuned to a dead channel.William Gibson revolutionised science fiction in his 1984 debut Neuromancer. The writer who gave us the matrix and coined the term ’cyberspace’ produced a first novel that won the Hugo, Nebula and Philip K. Dick Awards, and lit the fuse on the Cyberpunk movement.'),
(11, 2334, 1, 'Giraffes Can’t Dance', 'Giles Andreae', 5, 1, DATE '2013-09-08', 18, 18, 18, 5, false, false, false, 'Number One bestseller Giraffes Can’t Dance from author Giles Andreae has been delighting children for over 20 years. Gerald the tall giraffe would love to join in with the other animals at the Jungle Dance, but everyone knows that giraffes can’t dance . . . or can they?'),
(12, 3445, 4, 'Harry Potter and the Cursed Child', 'J. K. Rowling', 0, 0, DATE '2018-07-02', 24, 23, 15, 6, true, false, false, 'Based on an original new story by J.K. Rowling, Jack Thorne and John Tiffany, a new play by Jack Thorne, Harry Potter and the Cursed Child is the eighth story in the Harry Potter series and the first official Harry Potter story to be presented on stage. The play will receive its world premiere in London’s West End on 30th July 2016.'),
(13, 9080, 4, 'Harry Potter and the Philosopher’s Stone', 'J. K. Rowling', 0, 0, DATE '2021-09-22', 5, 4, 19, 6, false, false, false, 'Prepare to be spellbound by Jim Kay’s dazzling depiction of the wizarding world and much loved characters in this full-colour illustrated hardback edition of the nation’s favourite children’s book - Harry Potter and the Philosopher’s Stone. Brimming with rich detail and humour that perfectly complements J.K. Rowling’s timeless classic, Jim Kay’s glorious illustrations will captivate fans and new readers alike.'),
(14, 2121, 4, 'Harry Potter and the Chamber of Secrets', 'J. K. Rowling', 0, 0, DATE '2022-02-15', 5, 4, 14, 6, false, false, false, 'Prepare to be spellbound by Jim Kay’s dazzling full-colour illustrations in this stunning new edition of J.K. Rowling’s Harry Potter and the Chamber of Secrets. Breathtaking scenes, dark themes and unforgettable characters - including Dobby and Gilderoy Lockhart - await inside this fully illustrated edition.'),
(15, 8987, 4, 'Harry Potter and the Prisoner of Azkaban', 'J. K. Rowling', 0, 0, DATE '2022-01-09', 50, 49, 25, 7, false, false, false, 'When the Knight Bus crashes through the darkness and screeches to a halt in front of him, it’s the start of another far from ordinary year at Hogwarts for Harry Potter. Sirius Black, escaped mass-murderer and follower of Lord Voldemort, is on the run - and they say he is coming after Harry.'),
(16, 1999, 4, 'Harry Potter and the Order of the Phoenix', 'J. K. Rowling', 0, 0, DATE '2021-02-23', 30, 30, 32, 7, false, false, false, 'Dark times have come to Hogwarts. After the Dementors’ attack on his cousin Dudley, Harry Potter knows that Voldemort will stop at nothing to find him. There are many who deny the Dark Lord’s return, but Harry is not alone: a secret order gathers at Grimmauld Place to fight against the Dark forces.'),
(17, 4365, 4, 'Harry Potter and the Deathly Hallows', 'J. K. Rowling', 0, 0, DATE '2011-01-12', 20, 20, 27, 7, false, false, false, 'As he climbs into the sidecar of Hagrid’s motorbike and takes to the skies, leaving Privet Drive for the last time, Harry Potter knows that Lord Voldemort and the Death Eaters are not far behind. The protective charm that has kept Harry safe until now is now broken, but he cannot keep hiding.'),
(18, 1289, 4, 'Harry Potter and the Half-Blood Prince', 'J. K. Rowling', 0, 0, DATE '2017-09-25', 60, 60, 17, 7, false, false, false, 'When Dumbledore arrives at Privet Drive one summer night to collect Harry Potter, his wand hand is blackened and shrivelled, but he does not reveal why. Secrets and suspicion are spreading through the wizarding world, and Hogwarts itself is not safe.'),
(19, 5555, 1, 'Look Inside Our World', 'Emily Bone', 6, 0, DATE '2005-07-22', 60, 60, 19, 7, false, true, false, 'Take a trip around the world in this fascinating lift-the-flap book. With over 80 flaps to lift, intrepid explorers can discover our world, from the layers that make up planet Earth to the tiniest insects in the rainforest and the creatures who live at the very bottom of the sea. Includes pages about the hottest and coldest parts of the world, and a map with lift-the-flap details about each continent. A colourful and fun introduction to geography with internet links to find out more.'),
(20, 8888, 1, 'Look Inside Space', 'Rob Lloyd Jones', 6, 0, DATE '2007-02-01', 20, 20, 18, 7, false, true, false, 'Why do stars shine? And how was the moon made? Children can blast off into space with this fantastic interactive flap book revealing the secrets of the Universe. Vibrant illustrations and simple explanations combined with over 70 flaps to lift. Each double page covers a different topic, including ’Glittering galaxies’, ’On the Moon’ and ’Space Station’.');

INSERT INTO users (id, email, firstname, lastname, isBlocked, fine, role, lastFineRecalculationDate, passwordHash) VALUES
(1, 'admin@gmail.com', 'Mark', 'Brown', false, 0, 0, DATE '2023-01-01', 'mvFbM25qlhmShTffMLLmojdlafz51+dz7M7eZWBlKaA='), /*0000*/
(2, 'mary@gmail.com', 'Mary', 'Smith', false, 0, 1, DATE '2023-01-01', 'A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ='), /*1234*/
(3, 'john@gmail.com', 'John', 'Twist', false, 0, 1, DATE '2023-01-01', '2ixXXkjudlG56ZpREha4J+9t6nygjnfHBVsYudkYqEI='), /*1248*/
(4, 'elon@gmail.com', 'Elon', 'Musk', false, 0, 1, DATE '2023-01-01', 'iNQmb9TmM40TuEX88olXnSCciXgjuSF9o+Fhk28DFYk='), /*abcd*/
(5, 'matt@gmail.com', 'Matt', 'Smith', true, 1000, 2, DATE '2023-01-01', '/iWStCpyfpd/BVlHOFtwnMgrFrmof4jGq/OQDWXQzcM='), /*4321*/
(6, 'mark@gmail.com', 'Mark', 'Brown', false, 0, 2, DATE '2023-01-01', 'RwZIRYLKAZgV14JEcJVPoDvu80DhM1xmNnkjurDy+cU='), /*iiii*/
(7, 'ann@gmail.com', 'Ann', 'Smith', false, 0, 2, DATE '2023-01-01', 'M3k/jdfPbCP++WpIW8pDeYQJkuVftKag2RN6SDdqCh0='), /*11aa*/
(8, 'harry@gmail.com', 'Harry', 'Smith', false, 100, 2, DATE '2023-01-01', 'KSaicx9LMSwImCys+AYesUv2XBqHzF1w6GTgecYiBzE='), /*8888*/
(9, 'adam@gmail.com', 'Adam', 'Black', false, 0, 2, DATE '2023-01-01', 'iNQmb9TmM40TuEX88olXnSCciXgjuSF9o+Fhk28DFYk='), /*abcd*/
(10, 'mrsm@gmail.com', 'Mark', 'Smith', false, 0, 2, DATE '2023-01-01', 'NHKtu8uWd9G0U2XTfZbRwzIX10VWdXf9m9XCdmolgyA='), /*0202*/
(11, 'mary01@gmail.com', 'Mary', 'Stone', false, 10, 2, DATE '2023-01-01', 'alUXdu6t2r5dVS6kA/q6qoGCX28F19lFQ5zNTn3AkCk='); /*2288*/

INSERT INTO bookmarks (userId, bookId) VALUES
(6, 12),
(6, 13),
(6, 14),
(6, 15),
(6, 16),
(6, 17),
(6, 18);

INSERT INTO orders (code, userId, bookId, state, creationDate, deadlineDate) VALUES
(1, 6, 12, 1, DATE '2023-01-01', DATE '2023-04-01'),
(2, 6, 13, 1, DATE '2023-01-01', DATE '2023-04-01'),
(3, 6, 14, 1, DATE '2023-01-01', DATE '2023-04-01'),
(4, 6, 15, 1, DATE '2023-01-01', DATE '2023-04-01'),
(5, 6, 16, 1, DATE '2023-01-01', DATE '2023-04-01'),
(6, 6, 17, 1, DATE '2023-01-01', DATE '2023-04-01'),
(7, 6, 18, 1, DATE '2023-01-01', DATE '2023-04-01'),

(8, 7, 12, 2, NULL, NULL),
(9, 7, 13, 2, NULL, NULL),
(10, 7, 14, 2, NULL, NULL),
(11, 7, 15, 2, NULL, NULL),
(12, 7, 16, 2, NULL, NULL),

(13, 8, 7, 3, NULL, NULL),
(14, 8, 8, 3, NULL, NULL),
(15, 8, 9, 3, NULL, NULL),
(16, 8, 14, 0, NULL, NULL),
(17, 8, 15, 0, NULL, NULL),
(18, 8, 16, 0, NULL, NULL),
(19, 8, 17, 0, NULL, NULL),
(20, 8, 18, 0, NULL, NULL),

(21, 9, 13, 0, NULL, NULL),
(22, 9, 14, 0, NULL, NULL),
(23, 9, 15, 0, NULL, NULL),
(24, 9, 16, 0, NULL, NULL),
(25, 9, 17, 0, NULL, NULL),

(26, 10, 1, 0, NULL, NULL),
(27, 10, 2, 0, NULL, NULL),
(28, 10, 3, 0, NULL, NULL),
(29, 10, 4, 0, NULL, NULL),
(30, 10, 5, 0, NULL, NULL),

(31, 10, 10, 0, NULL, NULL),
(32, 10, 11, 0, NULL, NULL),
(33, 10, 7, 0, NULL, NULL),
(34, 10, 8, 0, NULL, NULL),
(35, 10, 9, 0, NULL, NULL);





INSERT INTO feedbacks (userId, bookId, creationDate, grade, text) VALUES
(5, 1, DATE '2022-03-08', 3, 'I know things are stressful right now, but thanks to you, I think everything’s going to slow down soon. When it does, I’m taking you out to happy hour to thank you for everything you’ve done to get us out of this situation.'),
(6, 1, DATE '2022-03-14', 4, 'I admired how you admitted you lacked deep knowledge and experience in Python. Your honesty helped me help you succeed, and that’s what I always want to do.'),
(7, 1, DATE '2021-08-21', 3, 'I realize it wasn’t easy to admit you were running behind on this project, but I’m so glad you were honest. We can fix this together. If you had kept quiet and failed to meet our deadline, we might be in hot water with the big boss.'),
(8, 1, DATE '2022-07-06', 4, 'I’m so impressed by your dedication to learning. I know it wasn’t easy when that technology solution you presented didn’t work out. I’m amazed that you managed to distill feedback from all those stakeholders and find a new, viable solution that everyone loves.'),
(9, 1, DATE '2022-08-11', 5, 'The way you gave that presentation today really shows me you listened to what I said about the snafu last month. I appreciate your mindful application of feedback.'),
(10, 1, DATE '2020-11-22', 5, 'I’m so thrilled that you managed to secure ten new partners. This achievement surpassed my expectations, and it will make a significant impact on the kind of business we can do in the next quarter.'),

(5, 2, DATE '2021-03-23', 5, 'I know things are stressful right now, but thanks to you, I think everything’s going to slow down soon. When it does, I’m taking you out to happy hour to thank you for everything you’ve done to get us out of this situation.'),
(6, 2, DATE '2020-04-20', 5, 'I admired how you admitted you lacked deep knowledge and experience in Python. Your honesty helped me help you succeed, and that’s what I always want to do.'),
(7, 2, DATE '2020-11-10', 1, 'I realize it wasn’t easy to admit you were running behind on this project, but I’m so glad you were honest. We can fix this together. If you had kept quiet and failed to meet our deadline, we might be in hot water with the big boss.'),
(8, 2, DATE '2020-07-12', 1, 'I’m so impressed by your dedication to learning. I know it wasn’t easy when that technology solution you presented didn’t work out. I’m amazed that you managed to distill feedback from all those stakeholders and find a new, viable solution that everyone loves.'),
(9, 2, DATE '2022-05-22', 1, 'The way you gave that presentation today really shows me you listened to what I said about the snafu last month. I appreciate your mindful application of feedback.'),
(10, 2, DATE '2023-01-03', 3, 'I’m so thrilled that you managed to secure ten new partners. This achievement surpassed my expectations, and it will make a significant impact on the kind of business we can do in the next quarter.'),
(11, 2, DATE '2022-11-03', 2, 'I know this goal wasn’t easy. How you managed to set it and systematically work towards it until you achieved it truly speaks to your intelligence, tenacity, and perseverance. I’m lucky to have you on my team'),

(5, 3, DATE '2023-01-14', 2, 'I know things are stressful right now, but thanks to you, I think everything’s going to slow down soon. When it does, I’m taking you out to happy hour to thank you for everything you’ve done to get us out of this situation.'),
(6, 3, DATE '2021-05-22', 4, 'I admired how you admitted you lacked deep knowledge and experience in Python. Your honesty helped me help you succeed, and that’s what I always want to do.'),
(7, 3, DATE '2022-01-21', 2, 'I realize it wasn’t easy to admit you were running behind on this project, but I’m so glad you were honest. We can fix this together. If you had kept quiet and failed to meet our deadline, we might be in hot water with the big boss.'),
(8, 3, DATE '2020-05-02', 4, 'I’m so impressed by your dedication to learning. I know it wasn’t easy when that technology solution you presented didn’t work out. I’m amazed that you managed to distill feedback from all those stakeholders and find a new, viable solution that everyone loves.'),
(9, 3, DATE '2020-06-08', 2, 'The way you gave that presentation today really shows me you listened to what I said about the snafu last month. I appreciate your mindful application of feedback.'),
(11, 3, DATE '2021-04-02', 5, 'I know this goal wasn’t easy. How you managed to set it and systematically work towards it until you achieved it truly speaks to your intelligence, tenacity, and perseverance. I’m lucky to have you on my team'),

(5, 4, DATE '2021-03-23', 3, 'I know things are stressful right now, but thanks to you, I think everything’s going to slow down soon. When it does, I’m taking you out to happy hour to thank you for everything you’ve done to get us out of this situation.'),
(6, 4, DATE '2022-11-06', 1, 'I admired how you admitted you lacked deep knowledge and experience in Python. Your honesty helped me help you succeed, and that’s what I always want to do.'),
(7, 4, DATE '2020-06-17', 2, 'I realize it wasn’t easy to admit you were running behind on this project, but I’m so glad you were honest. We can fix this together. If you had kept quiet and failed to meet our deadline, we might be in hot water with the big boss.'),
(8, 4, DATE '2022-10-26', 1, 'I’m so impressed by your dedication to learning. I know it wasn’t easy when that technology solution you presented didn’t work out. I’m amazed that you managed to distill feedback from all those stakeholders and find a new, viable solution that everyone loves.'),
(9, 4, DATE '2020-03-18', 1, 'The way you gave that presentation today really shows me you listened to what I said about the snafu last month. I appreciate your mindful application of feedback.'),
(10, 4, DATE '2020-07-09', 1, 'I’m so thrilled that you managed to secure ten new partners. This achievement surpassed my expectations, and it will make a significant impact on the kind of business we can do in the next quarter.'),
(11, 4, DATE '2021-01-06', 5, 'I know this goal wasn’t easy. How you managed to set it and systematically work towards it until you achieved it truly speaks to your intelligence, tenacity, and perseverance. I’m lucky to have you on my team'),

(5, 5, DATE '2020-12-07', 1, 'I know things are stressful right now, but thanks to you, I think everything’s going to slow down soon. When it does, I’m taking you out to happy hour to thank you for everything you’ve done to get us out of this situation.'),
(6, 5, DATE '2021-07-02', 4, 'I admired how you admitted you lacked deep knowledge and experience in Python. Your honesty helped me help you succeed, and that’s what I always want to do.'),
(7, 5, DATE '2020-12-06', 4, 'I realize it wasn’t easy to admit you were running behind on this project, but I’m so glad you were honest. We can fix this together. If you had kept quiet and failed to meet our deadline, we might be in hot water with the big boss.'),
(8, 5, DATE '2022-11-25', 4, 'I’m so impressed by your dedication to learning. I know it wasn’t easy when that technology solution you presented didn’t work out. I’m amazed that you managed to distill feedback from all those stakeholders and find a new, viable solution that everyone loves.'),
(9, 5, DATE '2022-08-15', 4, 'The way you gave that presentation today really shows me you listened to what I said about the snafu last month. I appreciate your mindful application of feedback.'),
(10, 5, DATE '2022-01-27', 4, 'I’m so thrilled that you managed to secure ten new partners. This achievement surpassed my expectations, and it will make a significant impact on the kind of business we can do in the next quarter.'),
(11, 5, DATE '2020-11-24', 2, 'I know this goal wasn’t easy. How you managed to set it and systematically work towards it until you achieved it truly speaks to your intelligence, tenacity, and perseverance. I’m lucky to have you on my team'),

(5, 6, DATE '2020-02-07', 4, 'I know things are stressful right now, but thanks to you, I think everything’s going to slow down soon. When it does, I’m taking you out to happy hour to thank you for everything you’ve done to get us out of this situation.'),
(7, 6, DATE '2021-02-24', 2, 'I realize it wasn’t easy to admit you were running behind on this project, but I’m so glad you were honest. We can fix this together. If you had kept quiet and failed to meet our deadline, we might be in hot water with the big boss.'),
(8, 6, DATE '2020-03-14', 1, 'I’m so impressed by your dedication to learning. I know it wasn’t easy when that technology solution you presented didn’t work out. I’m amazed that you managed to distill feedback from all those stakeholders and find a new, viable solution that everyone loves.'),
(9, 6, DATE '2021-09-14', 3, 'The way you gave that presentation today really shows me you listened to what I said about the snafu last month. I appreciate your mindful application of feedback.'),
(10, 6, DATE '2021-01-07', 5, 'I’m so thrilled that you managed to secure ten new partners. This achievement surpassed my expectations, and it will make a significant impact on the kind of business we can do in the next quarter.'),
(11, 6, DATE '2021-04-13', 1, 'I know this goal wasn’t easy. How you managed to set it and systematically work towards it until you achieved it truly speaks to your intelligence, tenacity, and perseverance. I’m lucky to have you on my team'),

(5, 7, DATE '2021-01-03', 3, 'I know things are stressful right now, but thanks to you, I think everything’s going to slow down soon. When it does, I’m taking you out to happy hour to thank you for everything you’ve done to get us out of this situation.'),
(7, 7, DATE '2022-06-12', 3, 'I realize it wasn’t easy to admit you were running behind on this project, but I’m so glad you were honest. We can fix this together. If you had kept quiet and failed to meet our deadline, we might be in hot water with the big boss.'),
(8, 7, DATE '2022-02-11', 2, 'I’m so impressed by your dedication to learning. I know it wasn’t easy when that technology solution you presented didn’t work out. I’m amazed that you managed to distill feedback from all those stakeholders and find a new, viable solution that everyone loves.'),
(9, 7, DATE '2020-05-10', 3, 'The way you gave that presentation today really shows me you listened to what I said about the snafu last month. I appreciate your mindful application of feedback.'),
(10, 7, DATE '2022-04-12', 4, 'I’m so thrilled that you managed to secure ten new partners. This achievement surpassed my expectations, and it will make a significant impact on the kind of business we can do in the next quarter.'),
(11, 7, DATE '2023-01-08', 1, 'I know this goal wasn’t easy. How you managed to set it and systematically work towards it until you achieved it truly speaks to your intelligence, tenacity, and perseverance. I’m lucky to have you on my team'),

(5, 8, DATE '2022-05-04', 3, 'I know things are stressful right now, but thanks to you, I think everything’s going to slow down soon. When it does, I’m taking you out to happy hour to thank you for everything you’ve done to get us out of this situation.'),
(7, 8, DATE '2021-09-10', 2, 'I realize it wasn’t easy to admit you were running behind on this project, but I’m so glad you were honest. We can fix this together. If you had kept quiet and failed to meet our deadline, we might be in hot water with the big boss.'),
(8, 8, DATE '2021-11-19', 2, 'I’m so impressed by your dedication to learning. I know it wasn’t easy when that technology solution you presented didn’t work out. I’m amazed that you managed to distill feedback from all those stakeholders and find a new, viable solution that everyone loves.'),
(9, 8, DATE '2020-06-25', 3, 'The way you gave that presentation today really shows me you listened to what I said about the snafu last month. I appreciate your mindful application of feedback.'),
(10, 8, DATE '2020-05-19', 5, 'I’m so thrilled that you managed to secure ten new partners. This achievement surpassed my expectations, and it will make a significant impact on the kind of business we can do in the next quarter.'),
(11, 8, DATE '2022-01-17', 3, 'I know this goal wasn’t easy. How you managed to set it and systematically work towards it until you achieved it truly speaks to your intelligence, tenacity, and perseverance. I’m lucky to have you on my team'),

(5, 9, DATE '2021-05-15', 5, 'I know things are stressful right now, but thanks to you, I think everything’s going to slow down soon. When it does, I’m taking you out to happy hour to thank you for everything you’ve done to get us out of this situation.'),
(6, 9, DATE '2022-05-16', 5, 'I admired how you admitted you lacked deep knowledge and experience in Python. Your honesty helped me help you succeed, and that’s what I always want to do.'),
(7, 9, DATE '2021-08-14', 2, 'I realize it wasn’t easy to admit you were running behind on this project, but I’m so glad you were honest. We can fix this together. If you had kept quiet and failed to meet our deadline, we might be in hot water with the big boss.'),
(8, 9, DATE '2022-04-02', 2, 'I’m so impressed by your dedication to learning. I know it wasn’t easy when that technology solution you presented didn’t work out. I’m amazed that you managed to distill feedback from all those stakeholders and find a new, viable solution that everyone loves.'),
(9, 9, DATE '2020-05-03', 2, 'The way you gave that presentation today really shows me you listened to what I said about the snafu last month. I appreciate your mindful application of feedback.'),
(10, 9, DATE '2020-10-13', 5, 'I’m so thrilled that you managed to secure ten new partners. This achievement surpassed my expectations, and it will make a significant impact on the kind of business we can do in the next quarter.'),
(11, 9, DATE '2022-01-22', 5, 'I know this goal wasn’t easy. How you managed to set it and systematically work towards it until you achieved it truly speaks to your intelligence, tenacity, and perseverance. I’m lucky to have you on my team'),

(5, 10, DATE '2022-09-25', 5, 'I know things are stressful right now, but thanks to you, I think everything’s going to slow down soon. When it does, I’m taking you out to happy hour to thank you for everything you’ve done to get us out of this situation.'),
(6, 10, DATE '2020-03-25', 2, 'I admired how you admitted you lacked deep knowledge and experience in Python. Your honesty helped me help you succeed, and that’s what I always want to do.'),
(7, 10, DATE '2020-06-26', 5, 'I realize it wasn’t easy to admit you were running behind on this project, but I’m so glad you were honest. We can fix this together. If you had kept quiet and failed to meet our deadline, we might be in hot water with the big boss.'),
(8, 10, DATE '2021-07-03', 2, 'I’m so impressed by your dedication to learning. I know it wasn’t easy when that technology solution you presented didn’t work out. I’m amazed that you managed to distill feedback from all those stakeholders and find a new, viable solution that everyone loves.'),
(9, 10, DATE '2020-03-15', 4, 'The way you gave that presentation today really shows me you listened to what I said about the snafu last month. I appreciate your mindful application of feedback.'),
(10, 10, DATE '2022-11-07', 3, 'I’m so thrilled that you managed to secure ten new partners. This achievement surpassed my expectations, and it will make a significant impact on the kind of business we can do in the next quarter.'),
(11, 10, DATE '2020-12-17', 3, 'I know this goal wasn’t easy. How you managed to set it and systematically work towards it until you achieved it truly speaks to your intelligence, tenacity, and perseverance. I’m lucky to have you on my team'),

(5, 11, DATE '2020-07-26', 4, 'I know things are stressful right now, but thanks to you, I think everything’s going to slow down soon. When it does, I’m taking you out to happy hour to thank you for everything you’ve done to get us out of this situation.'),
(6, 11, DATE '2021-12-16', 4, 'I admired how you admitted you lacked deep knowledge and experience in Python. Your honesty helped me help you succeed, and that’s what I always want to do.'),
(7, 11, DATE '2020-04-24', 4, 'I realize it wasn’t easy to admit you were running behind on this project, but I’m so glad you were honest. We can fix this together. If you had kept quiet and failed to meet our deadline, we might be in hot water with the big boss.'),
(8, 11, DATE '2020-08-18', 2, 'I’m so impressed by your dedication to learning. I know it wasn’t easy when that technology solution you presented didn’t work out. I’m amazed that you managed to distill feedback from all those stakeholders and find a new, viable solution that everyone loves.'),
(9, 11, DATE '2021-10-23', 4, 'The way you gave that presentation today really shows me you listened to what I said about the snafu last month. I appreciate your mindful application of feedback.'),

(5, 12, DATE '2022-10-16', 1, 'I know things are stressful right now, but thanks to you, I think everything’s going to slow down soon. When it does, I’m taking you out to happy hour to thank you for everything you’ve done to get us out of this situation.'),
(6, 12, DATE '2022-06-01', 1, 'I admired how you admitted you lacked deep knowledge and experience in Python. Your honesty helped me help you succeed, and that’s what I always want to do.'),
(8, 12, DATE '2020-09-20', 2, 'I’m so impressed by your dedication to learning. I know it wasn’t easy when that technology solution you presented didn’t work out. I’m amazed that you managed to distill feedback from all those stakeholders and find a new, viable solution that everyone loves.'),
(9, 12, DATE '2020-02-27', 3, 'The way you gave that presentation today really shows me you listened to what I said about the snafu last month. I appreciate your mindful application of feedback.'),
(10, 12, DATE '2020-06-14', 5, 'I’m so thrilled that you managed to secure ten new partners. This achievement surpassed my expectations, and it will make a significant impact on the kind of business we can do in the next quarter.'),
(11, 12, DATE '2022-03-21', 3, 'I know this goal wasn’t easy. How you managed to set it and systematically work towards it until you achieved it truly speaks to your intelligence, tenacity, and perseverance. I’m lucky to have you on my team'),

(5, 13, DATE '2020-12-05', 3, 'I know things are stressful right now, but thanks to you, I think everything’s going to slow down soon. When it does, I’m taking you out to happy hour to thank you for everything you’ve done to get us out of this situation.'),
(7, 13, DATE '2022-01-13', 4, 'I realize it wasn’t easy to admit you were running behind on this project, but I’m so glad you were honest. We can fix this together. If you had kept quiet and failed to meet our deadline, we might be in hot water with the big boss.'),
(8, 13, DATE '2020-02-11', 4, 'I’m so impressed by your dedication to learning. I know it wasn’t easy when that technology solution you presented didn’t work out. I’m amazed that you managed to distill feedback from all those stakeholders and find a new, viable solution that everyone loves.'),
(9, 13, DATE '2021-04-17', 1, 'The way you gave that presentation today really shows me you listened to what I said about the snafu last month. I appreciate your mindful application of feedback.'),
(10, 13, DATE '2021-11-02', 3, 'I’m so thrilled that you managed to secure ten new partners. This achievement surpassed my expectations, and it will make a significant impact on the kind of business we can do in the next quarter.'),
(11, 13, DATE '2020-10-22', 4, 'I know this goal wasn’t easy. How you managed to set it and systematically work towards it until you achieved it truly speaks to your intelligence, tenacity, and perseverance. I’m lucky to have you on my team'),

(5, 14, DATE '2021-09-21', 3, 'I know things are stressful right now, but thanks to you, I think everything’s going to slow down soon. When it does, I’m taking you out to happy hour to thank you for everything you’ve done to get us out of this situation.'),
(7, 14, DATE '2022-11-16', 1, 'I realize it wasn’t easy to admit you were running behind on this project, but I’m so glad you were honest. We can fix this together. If you had kept quiet and failed to meet our deadline, we might be in hot water with the big boss.'),
(8, 14, DATE '2021-08-05', 5, 'I’m so impressed by your dedication to learning. I know it wasn’t easy when that technology solution you presented didn’t work out. I’m amazed that you managed to distill feedback from all those stakeholders and find a new, viable solution that everyone loves.'),
(9, 14, DATE '2021-12-14', 1, 'The way you gave that presentation today really shows me you listened to what I said about the snafu last month. I appreciate your mindful application of feedback.'),
(10, 14, DATE '2022-11-18', 3, 'I’m so thrilled that you managed to secure ten new partners. This achievement surpassed my expectations, and it will make a significant impact on the kind of business we can do in the next quarter.'),
(11, 14, DATE '2021-01-05', 1, 'I know this goal wasn’t easy. How you managed to set it and systematically work towards it until you achieved it truly speaks to your intelligence, tenacity, and perseverance. I’m lucky to have you on my team'),

(5, 15, DATE '2021-06-11', 5, 'I know things are stressful right now, but thanks to you, I think everything’s going to slow down soon. When it does, I’m taking you out to happy hour to thank you for everything you’ve done to get us out of this situation.'),
(6, 15, DATE '2020-02-19', 3, 'I admired how you admitted you lacked deep knowledge and experience in Python. Your honesty helped me help you succeed, and that’s what I always want to do.'),
(7, 15, DATE '2022-01-12', 3, 'I realize it wasn’t easy to admit you were running behind on this project, but I’m so glad you were honest. We can fix this together. If you had kept quiet and failed to meet our deadline, we might be in hot water with the big boss.'),
(8, 15, DATE '2022-03-14', 5, 'I’m so impressed by your dedication to learning. I know it wasn’t easy when that technology solution you presented didn’t work out. I’m amazed that you managed to distill feedback from all those stakeholders and find a new, viable solution that everyone loves.'),
(9, 15, DATE '2021-12-16', 1, 'The way you gave that presentation today really shows me you listened to what I said about the snafu last month. I appreciate your mindful application of feedback.'),
(10, 15, DATE '2022-05-24', 4, 'I’m so thrilled that you managed to secure ten new partners. This achievement surpassed my expectations, and it will make a significant impact on the kind of business we can do in the next quarter.'),
(11, 15, DATE '2021-03-24', 4, 'I know this goal wasn’t easy. How you managed to set it and systematically work towards it until you achieved it truly speaks to your intelligence, tenacity, and perseverance. I’m lucky to have you on my team'),

(5, 16, DATE '2020-05-22', 5, 'I know things are stressful right now, but thanks to you, I think everything’s going to slow down soon. When it does, I’m taking you out to happy hour to thank you for everything you’ve done to get us out of this situation.'),
(6, 16, DATE '2020-08-26', 4, 'I admired how you admitted you lacked deep knowledge and experience in Python. Your honesty helped me help you succeed, and that’s what I always want to do.'),
(7, 16, DATE '2021-03-20', 5, 'I realize it wasn’t easy to admit you were running behind on this project, but I’m so glad you were honest. We can fix this together. If you had kept quiet and failed to meet our deadline, we might be in hot water with the big boss.'),
(8, 16, DATE '2022-03-12', 5, 'I’m so impressed by your dedication to learning. I know it wasn’t easy when that technology solution you presented didn’t work out. I’m amazed that you managed to distill feedback from all those stakeholders and find a new, viable solution that everyone loves.'),
(9, 16, DATE '2020-02-27', 5, 'The way you gave that presentation today really shows me you listened to what I said about the snafu last month. I appreciate your mindful application of feedback.'),
(10, 16, DATE '2022-07-03', 4, 'I’m so thrilled that you managed to secure ten new partners. This achievement surpassed my expectations, and it will make a significant impact on the kind of business we can do in the next quarter.'),
(11, 16, DATE '2021-01-06', 4, 'I know this goal wasn’t easy. How you managed to set it and systematically work towards it until you achieved it truly speaks to your intelligence, tenacity, and perseverance. I’m lucky to have you on my team'),

(5, 17, DATE '2021-05-08', 4, 'I know things are stressful right now, but thanks to you, I think everything’s going to slow down soon. When it does, I’m taking you out to happy hour to thank you for everything you’ve done to get us out of this situation.'),
(6, 17, DATE '2020-04-17', 4, 'I admired how you admitted you lacked deep knowledge and experience in Python. Your honesty helped me help you succeed, and that’s what I always want to do.'),
(7, 17, DATE '2022-04-26', 2, 'I realize it wasn’t easy to admit you were running behind on this project, but I’m so glad you were honest. We can fix this together. If you had kept quiet and failed to meet our deadline, we might be in hot water with the big boss.'),
(8, 17, DATE '2020-06-16', 4, 'I’m so impressed by your dedication to learning. I know it wasn’t easy when that technology solution you presented didn’t work out. I’m amazed that you managed to distill feedback from all those stakeholders and find a new, viable solution that everyone loves.'),
(9, 17, DATE '2023-01-06', 5, 'The way you gave that presentation today really shows me you listened to what I said about the snafu last month. I appreciate your mindful application of feedback.'),
(10, 17, DATE '2022-06-17', 3, 'I’m so thrilled that you managed to secure ten new partners. This achievement surpassed my expectations, and it will make a significant impact on the kind of business we can do in the next quarter.'),
(11, 17, DATE '2020-04-07', 5, 'I know this goal wasn’t easy. How you managed to set it and systematically work towards it until you achieved it truly speaks to your intelligence, tenacity, and perseverance. I’m lucky to have you on my team'),

(5, 18, DATE '2022-10-07', 3, 'I know things are stressful right now, but thanks to you, I think everything’s going to slow down soon. When it does, I’m taking you out to happy hour to thank you for everything you’ve done to get us out of this situation.'),
(6, 18, DATE '2021-12-18', 4, 'I admired how you admitted you lacked deep knowledge and experience in Python. Your honesty helped me help you succeed, and that’s what I always want to do.'),
(7, 18, DATE '2022-01-06', 1, 'I realize it wasn’t easy to admit you were running behind on this project, but I’m so glad you were honest. We can fix this together. If you had kept quiet and failed to meet our deadline, we might be in hot water with the big boss.'),
(8, 18, DATE '2022-08-18', 4, 'I’m so impressed by your dedication to learning. I know it wasn’t easy when that technology solution you presented didn’t work out. I’m amazed that you managed to distill feedback from all those stakeholders and find a new, viable solution that everyone loves.'),
(9, 18, DATE '2022-08-08', 2, 'The way you gave that presentation today really shows me you listened to what I said about the snafu last month. I appreciate your mindful application of feedback.'),
(10, 18, DATE '2021-03-17', 1, 'I’m so thrilled that you managed to secure ten new partners. This achievement surpassed my expectations, and it will make a significant impact on the kind of business we can do in the next quarter.'),
(11, 18, DATE '2020-09-10', 2, 'I know this goal wasn’t easy. How you managed to set it and systematically work towards it until you achieved it truly speaks to your intelligence, tenacity, and perseverance. I’m lucky to have you on my team'),

(5, 19, DATE '2021-01-10', 4, 'I know things are stressful right now, but thanks to you, I think everything’s going to slow down soon. When it does, I’m taking you out to happy hour to thank you for everything you’ve done to get us out of this situation.'),
(6, 19, DATE '2022-11-06', 1, 'I admired how you admitted you lacked deep knowledge and experience in Python. Your honesty helped me help you succeed, and that’s what I always want to do.'),
(7, 19, DATE '2021-11-07', 4, 'I realize it wasn’t easy to admit you were running behind on this project, but I’m so glad you were honest. We can fix this together. If you had kept quiet and failed to meet our deadline, we might be in hot water with the big boss.'),
(8, 19, DATE '2021-11-06', 2, 'I’m so impressed by your dedication to learning. I know it wasn’t easy when that technology solution you presented didn’t work out. I’m amazed that you managed to distill feedback from all those stakeholders and find a new, viable solution that everyone loves.'),
(9, 19, DATE '2021-07-21', 1, 'The way you gave that presentation today really shows me you listened to what I said about the snafu last month. I appreciate your mindful application of feedback.'),
(10, 19, DATE '2021-03-04', 2, 'I’m so thrilled that you managed to secure ten new partners. This achievement surpassed my expectations, and it will make a significant impact on the kind of business we can do in the next quarter.'),
(11, 19, DATE '2021-03-20', 5, 'I know this goal wasn’t easy. How you managed to set it and systematically work towards it until you achieved it truly speaks to your intelligence, tenacity, and perseverance. I’m lucky to have you on my team'),

(5, 20, DATE '2020-09-18', 5, 'I know things are stressful right now, but thanks to you, I think everything’s going to slow down soon. When it does, I’m taking you out to happy hour to thank you for everything you’ve done to get us out of this situation.'),
(6, 20, DATE '2021-11-23', 1, 'I admired how you admitted you lacked deep knowledge and experience in Python. Your honesty helped me help you succeed, and that’s what I always want to do.'),
(7, 20, DATE '2020-08-05', 2, 'I realize it wasn’t easy to admit you were running behind on this project, but I’m so glad you were honest. We can fix this together. If you had kept quiet and failed to meet our deadline, we might be in hot water with the big boss.'),
(8, 20, DATE '2021-05-20', 1, 'I’m so impressed by your dedication to learning. I know it wasn’t easy when that technology solution you presented didn’t work out. I’m amazed that you managed to distill feedback from all those stakeholders and find a new, viable solution that everyone loves.'),
(9, 20, DATE '2022-02-11', 2, 'The way you gave that presentation today really shows me you listened to what I said about the snafu last month. I appreciate your mindful application of feedback.'),
(10, 20, DATE '2021-05-10', 5, 'I’m so thrilled that you managed to secure ten new partners. This achievement surpassed my expectations, and it will make a significant impact on the kind of business we can do in the next quarter.'),
(11, 20, DATE '2020-03-09', 2, 'I know this goal wasn’t easy. How you managed to set it and systematically work towards it until you achieved it truly speaks to your intelligence, tenacity, and perseverance. I’m lucky to have you on my team');