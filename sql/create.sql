create table Image (
       id            bigserial          primary key,
       content_type  varchar(32)        not null,
       created_at    timestamp          not null default current_timestamp,
       status        smallint           not null,
       bin           bytea              default null
);

create index image_created_at on image (created_at);
