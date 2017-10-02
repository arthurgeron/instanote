package com.instanote.infra;

/**
 * Created by hellguy on 29/09/17.
 */
public class Credentials_Example {
    private static final String user = "yourUser", password = "yourPassword", address = "yourDBAddress", databaseName = "schemaName", salt = "saltForPasswords";

    public static String getUser() { return user; }

    public static String getPassword() { return password; }

    public static String getAddress() { return  address; }

    public static String getDatabaseName() { return databaseName; }

    public static String getSalt() { return salt; }
}
