#up
ALTER TABLE users ADD COLUMN lastModified TIMESTAMP ;

# down
ALTER TABLE users DROP COLUMN lastModified;