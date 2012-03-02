# --- First database schema

# --- !Ups

create table user (
  id                        bigint not null primary key,
  name                       varchar(255),
  introduce                 text
);


create sequence user_seq start with 1;

# --- !Downs

drop table if exists user;
drop sequence if exists user_seq;