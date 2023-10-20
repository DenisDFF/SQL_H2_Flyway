package org.example;

public class DatabaseInitService {
    public static void main(String[] args) {
        try {

            SQLFileReader sqlFileReader = new SQLFileReader();

            String file = "C://Users//Denis//IdeaProjects//DataBase_test//src//main//resources//db//migration//V1__init_db.sql";
            String file2 = "C://Users//Denis//IdeaProjects//DataBase_test//src//main//resources//db//migration//V2__populate_db.sql";

            String createTable = sqlFileReader.readSQLFile(file);
            String createTable2 = sqlFileReader.readSQLFile(file2);

            Database instance = Database.getInstance();
            int resultCreate = instance.executeUpdate(createTable);
            int resultCreate2 = instance.executeUpdate(createTable2);

            System.out.println("res 1 =" + resultCreate);
            System.out.println("res 1 =" + resultCreate2);

            instance.executeClose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
