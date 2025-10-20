PATH = %PATH%;"C:\Program Files\MySQL\MySQL Server 5.7\bin"


rem mysql --user=root --password=12345678 --host=localhost 
rem mysql SHOW VARIABLES LIKE "secure_file_priv"

mysqldump --user=root --password=12345678 --host=localhost --databases psychomanager --add-drop-database > dump.sql
rem mysqldump --user=root --password=12345678 --host=localhost --tab="C:\ProgramData\MySQL\MySQL Server 5.7\Uploads\tst" psychomanager



pause