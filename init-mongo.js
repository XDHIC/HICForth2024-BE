db = db.getSiblingDB('hic');
db.createUser(
  {
    user: "kites-db",
    pwd: "kites262",
    roles: [{ role: "readWrite", db: "hic" }]
  }
);
