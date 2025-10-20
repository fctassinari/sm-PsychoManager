#!/bin/sh


/opt/bitnami/mysql/bin/mysqldump  --defaults-extra-file=/sistemasIIDH/config.cnf --socket=/opt/bitnami/mysql/tmp/mysql.sock --host=localhost --databases psychomanager --add-drop-database > /sistemasIIDH/shared/backup/PsychoManager/bkpPsychoManager_`date "+%Y_%m_%d_%H_%M_%S"`.sql