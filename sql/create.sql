create table picture (
       id            bigserial          primary key,
       file_name     varchar(255)       not null,
       content_type  varchar(32)        not null,
       created_at    timestamp          not null default current_timestamp
);

create index picture_created_at on picture (created_at);

create table Image (
       id            bigserial          primary key,
       content_type  varchar(32)        not null,
       created_at    timestamp          not null default current_timestamp,
       status        smallint           not null
);

create index image_created_at on image (created_at);

-- create view picture_selectall as
-- select * from picture order by created_at desc;
