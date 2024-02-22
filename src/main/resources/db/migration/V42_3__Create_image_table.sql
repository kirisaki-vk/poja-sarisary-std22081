create table if not exists image (
    id varchar primary key,
    original varchar not null ,
    modified varchar not null ,
    time varchar default current_timestamp
)