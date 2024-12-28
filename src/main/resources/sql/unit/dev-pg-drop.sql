alter table if exists hu_measured_values drop constraint if exists hu_measured_values_fk;
alter table if exists pu_measured_values drop constraint if exists pu_measured_values_fk;
alter table if exists uom_hu_link drop constraint if exists conversion_from_uom_fk;
alter table if exists uom_hu_link drop constraint if exists conversion_to_hu_fk;
alter table if exists uom_measured_values drop constraint if exists uom_measured_values_fk;
alter table if exists uom_pu_link drop constraint if exists conversion_from_uom_fk;
alter table if exists uom_pu_link drop constraint if exists conversion_to_pu_fk;
alter table if exists uom_self_link drop constraint if exists conversion_from_uom_fk;
alter table if exists uom_self_link drop constraint if exists conversion_to_uom_fk;
drop table if exists hu cascade;
drop table if exists hu_measured_values cascade;
drop table if exists pu cascade;
drop table if exists pu_measured_values cascade;
drop table if exists uom cascade;
drop table if exists uom_hu_link cascade;
drop table if exists uom_measured_values cascade;
drop table if exists uom_pu_link cascade;
drop table if exists uom_self_link cascade;