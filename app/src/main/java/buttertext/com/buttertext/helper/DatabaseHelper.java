package buttertext.com.buttertext.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import buttertext.com.buttertext.model.Contact;
import buttertext.com.buttertext.model.Message;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Table Names
    private static final String TABLE_MESSAGE = "messages";
    private static final String TABLE_CONTACT = "contacts";
    private static final String TABLE_CONTACT_MESSAGE = "contact_messages";

    // Common column names
    private static final String KEY_ID = "id";

    // MESSAGES Table - column names
    private static final String KEY_CONTENT = "content";
    private static final String KEY_TYPE = "type";
    private static final String KEY_SENT_AT = "sent_at";

    // CONTACTS Table - column names
    private static final String KEY_CONTACT_NAME = "contact_name";
    private static final String KEY_CONTACT_NUMBER = "contact_number";
    private static final String KEY_CONTACT_EMAIL = "contact_email";

    // MESSAGE_CONTACTS Table - column names
    private static final String KEY_MESSAGE_ID = "message_id";
    private static final String KEY_CONTACT_ID = "contact_id";

    // Table Create Statements
    // Message table create statement
    private static final String CREATE_TABLE_MESSAGE = "CREATE TABLE "
            + TABLE_MESSAGE + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_CONTENT
            + " TEXT," + KEY_TYPE + " INTEGER," + KEY_SENT_AT
            + " DATETIME" + ")";

    // Contact table create statement
    private static final String CREATE_TABLE_CONTACT = "CREATE TABLE " + TABLE_CONTACT
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_CONTACT_NAME + " TEXT,"
            + KEY_CONTACT_NUMBER + " TEXT," + KEY_CONTACT_EMAIL + " TEXT" + ")";

    // message_contact table create statement
    private static final String CREATE_TABLE_CONTACT_MESSAGE = "CREATE TABLE "
            + TABLE_CONTACT_MESSAGE + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_MESSAGE_ID + " INTEGER," + KEY_CONTACT_ID + " INTEGER,"
            + KEY_SENT_AT + " DATETIME" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_MESSAGE);
        db.execSQL(CREATE_TABLE_CONTACT);
        db.execSQL(CREATE_TABLE_CONTACT_MESSAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT_MESSAGE);

        // create new tables
        onCreate(db);
    }

    /*
    * Creating a message
    */
    public long createMessage(Message message, long[] contact_ids) {
        SQLiteDatabase db = this.getWritableDatabase();

        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String currentTime = sdf.format(dt);

        ContentValues values = new ContentValues();
        values.put(KEY_CONTENT, message.getContent());
        values.put(KEY_TYPE, message.getType());
        values.put(KEY_SENT_AT, currentTime);

        // insert row
        long message_id = db.insert(TABLE_MESSAGE, null, values);

        // assigning contacts to message
        for (long contact_id : contact_ids) {
            createMessageContact(message_id, contact_id);
        }

        return message_id;
    }

    /*
    * get single message
    */
    public Message getMessage(long message_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_MESSAGE + " WHERE "
                + KEY_ID + " = " + message_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Message td = new Message();
        td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        td.setType(c.getInt(c.getColumnIndex(KEY_TYPE)));
        td.setContent(c.getString(c.getColumnIndex(KEY_CONTENT)));
        td.setSent_at(c.getString(c.getColumnIndex(KEY_SENT_AT)));

        return td;
    }

    /*
    * getting all messages
    * */
    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<Message>();
        String selectQuery = "SELECT  * FROM " + TABLE_MESSAGE;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Message td = new Message();
                td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                td.setType(c.getInt(c.getColumnIndex(KEY_TYPE)));
                td.setContent(c.getString(c.getColumnIndex(KEY_CONTENT)));
                td.setSent_at(c.getString(c.getColumnIndex(KEY_SENT_AT)));

                // adding to message list
                messages.add(td);
            } while (c.moveToNext());
        }

        return messages;
    }

    /*
    * getting all messages under single contact
    * */
    public List<Message> getAllMessagesByContact(String contact_name) {
        List<Message> messages = new ArrayList<Message>();

        String selectQuery = "SELECT  * FROM " + TABLE_MESSAGE + " td, "
                + TABLE_CONTACT + " tg, " + TABLE_CONTACT_MESSAGE + " tt WHERE tg."
                + KEY_CONTACT_NAME + " = '" + contact_name + "'" + " AND tg." + KEY_ID
                + " = " + "tt." + KEY_CONTACT_ID + " AND td." + KEY_ID + " = "
                + "tt." + KEY_MESSAGE_ID;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Message td = new Message();
                td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                td.setType(c.getInt(c.getColumnIndex(KEY_TYPE)));
                td.setContent(c.getString(c.getColumnIndex(KEY_CONTENT)));
                td.setSent_at(c.getString(c.getColumnIndex(KEY_SENT_AT)));

                // adding to message list
                messages.add(td);
            } while (c.moveToNext());
        }

        return messages;
    }

    /*
    * Updating a message
    */
    public int updateMessage(Message message) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CONTENT, message.getContent());
        values.put(KEY_TYPE, message.getType());
        values.put(KEY_SENT_AT, message.getSent_at());

        // updating row
        return db.update(TABLE_MESSAGE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(message.getId()) });
    }

    /*
     * Deleting a message
     */
    public void deleteMessage(long message_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MESSAGE, KEY_ID + " = ?",
                new String[] { String.valueOf(message_id) });
    }

    /*
     * Creating contact
     */
    public long createContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CONTACT_NAME, contact.getName());
        values.put(KEY_CONTACT_NUMBER, contact.getNumber());
        values.put(KEY_CONTACT_EMAIL, contact.getEmail());

        // insert row
        long contact_id = db.insert(TABLE_CONTACT, null, values);

        return contact_id;
    }

    /**
     * getting all contacts
     * */
    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<Contact>();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACT;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Contact t = new Contact();
                t.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                t.setName(c.getString(c.getColumnIndex(KEY_CONTACT_NAME)));
                t.setNumber(c.getString(c.getColumnIndex(KEY_CONTACT_NUMBER)));
                t.setEmail(c.getString(c.getColumnIndex(KEY_CONTACT_EMAIL)));

                // adding to contacts list
                contacts.add(t);
            } while (c.moveToNext());
        }
        return contacts;
    }

    /*
     * Updating a contact
     */
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CONTACT_NAME, contact.getName());

        // updating row
        return db.update(TABLE_CONTACT, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getId()) });
    }

    /*
     * Deleting a contact
     */
    public void deleteContact(Contact contact, boolean should_delete_all_contact_messages) {
        SQLiteDatabase db = this.getWritableDatabase();

        // before deleting contact
        // check if messages under this contact should also be deleted
        if (should_delete_all_contact_messages) {
            // get all messages under this contact
            List<Message> allContactMessages = getAllMessagesByContact(contact.getName());

            // delete all messages
            for (Message message : allContactMessages) {
                // delete message
                deleteMessage(message.getId());
            }
        }

        // now delete the contact
        db.delete(TABLE_CONTACT, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getId()) });
    }

    /*
     * Creating message_contact
     */
    public long createMessageContact(long message_id, long contact_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String currentTime = sdf.format(dt);

        ContentValues values = new ContentValues();
        values.put(KEY_MESSAGE_ID, message_id);
        values.put(KEY_CONTACT_ID, contact_id);
        values.put(KEY_SENT_AT, currentTime);

        long id = db.insert(TABLE_CONTACT_MESSAGE, null, values);

        return id;
    }

    /*
     * Updating a message contact
     */
    public int updateNoteContact(long id, long contact_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CONTACT_ID, contact_id);

        // updating row
        return db.update(TABLE_MESSAGE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}