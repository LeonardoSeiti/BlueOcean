create sequence cadastro_seq start with 1 increment by 50;
create sequence coral_seq start with 1 increment by 50;
create table cadastro (id number(19,0) not null, email varchar2(255 char) not null, nome varchar2(255 char) not null, primary key (id));
create table coral (tipo varchar2(4 char) not null, id number(19,0) not null, cor varchar2(255 char) not null, primary key (id));
