set path=D:\Ferramentas\PostgreSQL\9.5\bin

set PGDATABASE=teste
set PGHOST=localhost
rem set PGOPTIONS
set PGPORT=5432
set PGUSER=tassinari
set PGPASSWORD=12345678

rem pg_dump -v > bkpPsychoManager_`date "+%Y_%m_%d_%H_%M_%S"`.sql

pg_dump -v > bkpPsychoManager.sql
