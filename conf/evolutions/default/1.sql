# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table tbbooks (
  book_id                   varchar(255) not null,
  book_date                 varchar(255),
  book_dates                varchar(255),
  book_time                 varchar(255),
  book_amount               varchar(255),
  book_status               varchar(255),
  status                    varchar(255),
  user_username             varchar(255),
  customer_cus_id           varchar(255),
  constraint pk_tbbooks primary key (book_id))
;

create table tbcenter (
  cen_id                    varchar(255) not null,
  cen_name                  varchar(255),
  cen_address               varchar(255),
  cen_tel                   varchar(255),
  cen_email                 varchar(255),
  cen_price                 varchar(255),
  cen_logo                  varchar(255),
  constraint pk_tbcenter primary key (cen_id))
;

create table tbcustomer (
  cus_id                    varchar(255) not null,
  cus_name                  varchar(255),
  cus_address               varchar(255),
  cus_tel                   varchar(255),
  cus_email                 varchar(255),
  constraint pk_tbcustomer primary key (cus_id))
;

create table tbKeep (
  keep_id                   varchar(255) not null,
  keep_date                 varchar(255),
  user_username             varchar(255),
  constraint pk_tbKeep primary key (keep_id))
;

create table tbKeepList (
  kl_id                     varchar(255) not null,
  amount                    varchar(255),
  keep_keep_id              varchar(255),
  product_pro_id            varchar(255),
  constraint pk_tbKeepList primary key (kl_id))
;

create table tbPayment (
  id                        varchar(255) not null,
  pay_total                 varchar(255),
  pay_date                  varchar(255),
  pay_month                 varchar(255),
  pay_year                  varchar(255),
  user_username             varchar(255),
  team_id                   varchar(255),
  center_cen_id             varchar(255),
  constraint pk_tbPayment primary key (id))
;

create table tbProduct (
  pro_id                    varchar(255) not null,
  pro_name                  varchar(255),
  pro_amount                varchar(255),
  pro_unit                  varchar(255),
  pro_total                 varchar(255),
  constraint pk_tbProduct primary key (pro_id))
;

create table tbSale (
  sale_id                   varchar(255) not null,
  sale_date                 varchar(255),
  total                     varchar(255),
  sale_month                varchar(255),
  sale_year                 varchar(255),
  user_username             varchar(255),
  constraint pk_tbSale primary key (sale_id))
;

create table tbSaleList (
  id                        varchar(255) not null,
  price                     varchar(255),
  amount                    varchar(255),
  total                     varchar(255),
  sale_sale_id              varchar(255),
  product_pro_id            varchar(255),
  constraint pk_tbSaleList primary key (id))
;

create table tbWork (
  work_id                   varchar(255) not null,
  work_date                 varchar(255),
  work_month                varchar(255),
  work_year                 varchar(255),
  work_timein               varchar(255),
  work_timeout              varchar(255),
  user_username             varchar(255),
  constraint pk_tbWork primary key (work_id))
;

create table tbScheduleL (
  Sch_id                    varchar(255) not null,
  list_sch_id               varchar(255),
  username                  varchar(255),
  constraint pk_tbScheduleL primary key (Sch_id))
;

create table tbScheduleList (
  ListSch_Id                varchar(255) not null,
  sch_date                  varchar(255),
  sch_dates                 varchar(255),
  sch_timein                varchar(255),
  sch_timeout               varchar(255),
  user_username             varchar(255),
  constraint pk_tbScheduleList primary key (ListSch_Id))
;

create table tbTeam (
  id                        varchar(255) not null,
  book_book_id              varchar(255),
  date                      varchar(255),
  time                      varchar(255),
  status                    varchar(255),
  constraint pk_tbTeam primary key (id))
;

create table tbuser (
  username                  varchar(255) not null,
  mem_name                  varchar(255),
  mem_add                   varchar(255),
  mem_tel                   varchar(255),
  mem_email                 varchar(255),
  mem_id                    varchar(255),
  password                  varchar(255),
  status                    varchar(255),
  id                        varchar(255),
  constraint pk_tbuser primary key (username))
;

create table tbDiv (
  div_id                    varchar(255) not null,
  div_date                  varchar(255),
  div_datediv               varchar(255),
  sale_sale_id              varchar(255),
  user_username             varchar(255),
  payment_id                varchar(255),
  schedule_list_ListSch_Id  varchar(255),
  save_work_work_id         varchar(255),
  constraint pk_tbDiv primary key (div_id))
;

create table team_list (
  team_list_id              varchar(255) not null,
  team_list_posit           varchar(255),
  username                  varchar(255),
  team_id                   varchar(255),
  constraint pk_team_list primary key (team_list_id))
;

alter table tbbooks add constraint fk_tbbooks_user_1 foreign key (user_username) references tbuser (username) on delete restrict on update restrict;
create index ix_tbbooks_user_1 on tbbooks (user_username);
alter table tbbooks add constraint fk_tbbooks_customer_2 foreign key (customer_cus_id) references tbcustomer (cus_id) on delete restrict on update restrict;
create index ix_tbbooks_customer_2 on tbbooks (customer_cus_id);
alter table tbKeep add constraint fk_tbKeep_user_3 foreign key (user_username) references tbuser (username) on delete restrict on update restrict;
create index ix_tbKeep_user_3 on tbKeep (user_username);
alter table tbKeepList add constraint fk_tbKeepList_keep_4 foreign key (keep_keep_id) references tbKeep (keep_id) on delete restrict on update restrict;
create index ix_tbKeepList_keep_4 on tbKeepList (keep_keep_id);
alter table tbKeepList add constraint fk_tbKeepList_product_5 foreign key (product_pro_id) references tbProduct (pro_id) on delete restrict on update restrict;
create index ix_tbKeepList_product_5 on tbKeepList (product_pro_id);
alter table tbPayment add constraint fk_tbPayment_user_6 foreign key (user_username) references tbuser (username) on delete restrict on update restrict;
create index ix_tbPayment_user_6 on tbPayment (user_username);
alter table tbPayment add constraint fk_tbPayment_Team_7 foreign key (team_id) references tbTeam (id) on delete restrict on update restrict;
create index ix_tbPayment_Team_7 on tbPayment (team_id);
alter table tbPayment add constraint fk_tbPayment_center_8 foreign key (center_cen_id) references tbcenter (cen_id) on delete restrict on update restrict;
create index ix_tbPayment_center_8 on tbPayment (center_cen_id);
alter table tbSale add constraint fk_tbSale_user_9 foreign key (user_username) references tbuser (username) on delete restrict on update restrict;
create index ix_tbSale_user_9 on tbSale (user_username);
alter table tbSaleList add constraint fk_tbSaleList_sale_10 foreign key (sale_sale_id) references tbSale (sale_id) on delete restrict on update restrict;
create index ix_tbSaleList_sale_10 on tbSaleList (sale_sale_id);
alter table tbSaleList add constraint fk_tbSaleList_product_11 foreign key (product_pro_id) references tbProduct (pro_id) on delete restrict on update restrict;
create index ix_tbSaleList_product_11 on tbSaleList (product_pro_id);
alter table tbWork add constraint fk_tbWork_user_12 foreign key (user_username) references tbuser (username) on delete restrict on update restrict;
create index ix_tbWork_user_12 on tbWork (user_username);
alter table tbScheduleL add constraint fk_tbScheduleL_scheduleList_13 foreign key (list_sch_id) references tbScheduleList (ListSch_Id) on delete restrict on update restrict;
create index ix_tbScheduleL_scheduleList_13 on tbScheduleL (list_sch_id);
alter table tbScheduleL add constraint fk_tbScheduleL_user_14 foreign key (username) references tbuser (username) on delete restrict on update restrict;
create index ix_tbScheduleL_user_14 on tbScheduleL (username);
alter table tbScheduleList add constraint fk_tbScheduleList_user_15 foreign key (user_username) references tbuser (username) on delete restrict on update restrict;
create index ix_tbScheduleList_user_15 on tbScheduleList (user_username);
alter table tbTeam add constraint fk_tbTeam_book_16 foreign key (book_book_id) references tbbooks (book_id) on delete restrict on update restrict;
create index ix_tbTeam_book_16 on tbTeam (book_book_id);
alter table tbDiv add constraint fk_tbDiv_sale_17 foreign key (sale_sale_id) references tbSale (sale_id) on delete restrict on update restrict;
create index ix_tbDiv_sale_17 on tbDiv (sale_sale_id);
alter table tbDiv add constraint fk_tbDiv_user_18 foreign key (user_username) references tbuser (username) on delete restrict on update restrict;
create index ix_tbDiv_user_18 on tbDiv (user_username);
alter table tbDiv add constraint fk_tbDiv_payment_19 foreign key (payment_id) references tbPayment (id) on delete restrict on update restrict;
create index ix_tbDiv_payment_19 on tbDiv (payment_id);
alter table tbDiv add constraint fk_tbDiv_scheduleList_20 foreign key (schedule_list_ListSch_Id) references tbScheduleList (ListSch_Id) on delete restrict on update restrict;
create index ix_tbDiv_scheduleList_20 on tbDiv (schedule_list_ListSch_Id);
alter table tbDiv add constraint fk_tbDiv_saveWork_21 foreign key (save_work_work_id) references tbWork (work_id) on delete restrict on update restrict;
create index ix_tbDiv_saveWork_21 on tbDiv (save_work_work_id);
alter table team_list add constraint fk_team_list_user_22 foreign key (username) references tbuser (username) on delete restrict on update restrict;
create index ix_team_list_user_22 on team_list (username);
alter table team_list add constraint fk_team_list_team_23 foreign key (team_id) references tbTeam (id) on delete restrict on update restrict;
create index ix_team_list_team_23 on team_list (team_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table tbbooks;

drop table tbcenter;

drop table tbcustomer;

drop table tbKeep;

drop table tbKeepList;

drop table tbPayment;

drop table tbProduct;

drop table tbSale;

drop table tbSaleList;

drop table tbWork;

drop table tbScheduleL;

drop table tbScheduleList;

drop table tbTeam;

drop table tbuser;

drop table tbDiv;

drop table team_list;

SET FOREIGN_KEY_CHECKS=1;

