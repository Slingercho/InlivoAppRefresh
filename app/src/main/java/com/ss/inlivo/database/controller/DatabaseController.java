package com.ss.inlivo.database.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.ss.inlivo.database.DaoMaster;
import com.ss.inlivo.database.DaoSession;
import com.ss.inlivo.database.Message;
import com.ss.inlivo.database.MessageDao;
import com.ss.inlivo.database.User;
import com.ss.inlivo.database.UserDao;

import java.util.List;

/**
 * Created by user on 29.5.2016 г..
 */
public class DatabaseController {

    private static DatabaseController instance;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase mDatabase;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;


    public static DatabaseController init(Context context) {
        if (instance == null) {
            synchronized (DatabaseController.class) {
                if (instance == null) {
                    instance = new DatabaseController(context);
                }
            }
        }
        return instance;
    }

    public static DatabaseController getInstance() {

        if (instance == null) {
            throw new RuntimeException("Shouldn't be null. Init it on Activity creation");
        }

        return instance;
    }

    private DatabaseController(Context context) {

        mHelper = new DaoMaster.DevOpenHelper(context.getApplicationContext(), "inlivo-database", null);

        openWritableDb();

        addUsers();
    }

    public void openWritableDb() throws SQLiteException {
        mDatabase = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(mDatabase);
        mDaoSession = mDaoMaster.newSession();
    }

    private void addUsers() {

        User user1 = new User();
        user1.setUserName("Stanislav");
        user1.setUserProfilePicture("user_1");

        User user2 = new User();
        user2.setUserName("Stela");
        user2.setUserProfilePicture("user_2");

        User user3 = new User();
        user3.setUserName("Georgi");
        user3.setUserProfilePicture("user_3");

        UserDao userDao = mDaoSession.getUserDao();

        if (userDao.getKey(user1) == null) {
            userDao.insert(user1);
        }
        if (userDao.getKey(user2) == null) {
            userDao.insert(user2);
        }
        if (userDao.getKey(user3) == null) {
            userDao.insert(user3);
        }
    }

    public Message addMessage(String messageText, long userId){

        Message message = new Message();
        message.setMessage(messageText);
        message.setUserId(userId);

        mDaoSession.getMessageDao().insert(message);

        return message;
    }

    public List<Message> loadAllMessages() {

        MessageDao messageDao = mDaoSession.getMessageDao();

        return messageDao.loadAll();
    }

    public void clearDB(){

        mDaoSession.getUserDao().deleteAll();
        mDaoSession.getMessageDao().deleteAll();
    }
}
