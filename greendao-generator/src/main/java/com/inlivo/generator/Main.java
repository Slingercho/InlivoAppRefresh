package com.inlivo.generator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class Main {

    public static void main(String[] args) throws Exception {

        Schema schema = new Schema(1, "com.ss.inlivo.database");

        addUsersAndMessages(schema);

        new DaoGenerator().generateAll(schema, "./app/src/main/java");
    }

    private static void addUsersAndMessages(Schema schema){

        Entity user = schema.addEntity("User");
        user.setTableName("Users");
        user.addIdProperty().autoincrement();
        user.addStringProperty("userName");
        user.addStringProperty("userProfilePicture");

        Entity message = schema.addEntity("Message");
        message.setTableName("Messages");
        message.addIdProperty().autoincrement();
        message.addStringProperty("message");

        Property userId = message.addLongProperty("userId").notNull().getProperty();
        message.addToOne(user, userId);
    }
}
