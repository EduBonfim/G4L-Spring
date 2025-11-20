-- Create schema for the app (use in MySQL Workbench)
CREATE DATABASE IF NOT EXISTS `usuario` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
-- After running the script, ensure that your DB user has privileges on this database, e.g.
-- GRANT ALL PRIVILEGES ON `usuario`.* TO 'root'@'localhost';
-- If your MySQL uses a custom user, replace 'root' with the user you will use.

-- The JPA will create tables automatically when the app bootstraps (spring.jpa.hibernate.ddl-auto=update).
