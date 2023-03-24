create table if not exists type_room (
	id serial4 not null,
	created_at timestamp(6) not null,
	description text null,
	"name" varchar(255) null,
	updated_at timestamp(6) null,
	constraint type_room_pkey primary key (id)
);

insert into type_room
(created_at, description, "name", updated_at)
values
('2023-03-20 12:38:14.255',
'A webinar is a meeting that takes place over the internet. This type of meeting is perfect for when you need to have a conversation with a group of people but don’t have the time or resources to meet in person.',
'Webinar',
'2023-03-20 12:38:14.256'),
('2023-03-21 04:11:24.696',
'Conferences are virtual meetings where multiple people from different locations participate in the same discussion. This type of meeting is great for when you need to get a large number of opinions and viewpoints on an issue. With this method, you can easily see what everyone thinks without having them all together at one physical location.',
'Conference',
'2023-03-21 04:11:24.696'),
('2023-03-21 04:11:33.674',
'A session is a meeting in which one person shares their expertise on a particular topic with everyone else. This format of meeting works well when you need to get information from someone quickly and informally when there isn’t time for more extensive training or education.',
'Session / Q&A session',
'2023-03-21 04:11:33.675');

INSERT INTO users
(created_at, email, first_name, last_name, "password", "role", updated_at, username)
VALUES('2023-03-20 15:29:26.662', 'admin@gmail.com', 'Admin', 'Satu', '$2a$10$kqJ0YDHHFm00XkbduRFe0upJvHMD0v7byoQvQfCOHDULoYSPA.7Em', 'admin', '2023-03-20 15:29:26.662', 'admin');