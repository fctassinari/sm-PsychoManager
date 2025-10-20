path=D:\Ferramentas\PostgreSQL\9.5\bin

PGDATABASE=teste
PGHOST=localhost
#PGOPTIONS
PGPORT=5432
PGUSER=tassinari
PGPASSWORD=12345678

pg_dump -v > bkpPsychoManager_`date "+%Y_%m_%d_%H_%M_%S"`.sql

