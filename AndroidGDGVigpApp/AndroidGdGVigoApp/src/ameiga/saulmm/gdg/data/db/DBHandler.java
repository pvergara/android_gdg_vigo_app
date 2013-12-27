package ameiga.saulmm.gdg.data.db;

import static ameiga.saulmm.gdg.data.api.entities.Post.DELETE_TABLE_ACTIVITIES;

import java.util.LinkedList;
import java.util.List;

import ameiga.saulmm.gdg.Configuration;
import ameiga.saulmm.gdg.data.api.ApiHandler;
import ameiga.saulmm.gdg.data.api.entities.Post;
import ameiga.saulmm.gdg.data.api.entities.Url;
import ameiga.saulmm.gdg.data.db.entities.DBEntity;
import ameiga.saulmm.gdg.data.db.entities.Event;
import ameiga.saulmm.gdg.data.db.entities.GroupInfo;
import ameiga.saulmm.gdg.data.db.entities.Member;
import ameiga.saulmm.gdg.utils.DbUtils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHandler extends SQLiteOpenHelper {
	private final Context context;


	public DBHandler (Context context) {
		super(context, Configuration.DATABASE_NAME,
				null, Configuration.DATABASE_VERSION);

		this.context = context;
	}


	@Override
	public void onCreate (SQLiteDatabase db) {
		db.execSQL(Post.CREATE_TABLE_ACTIVITIES);
		db.execSQL(Event.CREATE_TABLE_EVENTS);
		db.execSQL(Member.CREATE_TABLE_MEMBERS);
		db.execSQL(GroupInfo.CREATE_GROUP_INFO);
		db.execSQL(Url.CREATE_TABLE_URL);

		ApiHandler apiHandler = new ApiHandler(context);
		String [] membersInsertsStatments = apiHandler.getMemberInsertStatments(); // Used to insert all members in the db
		for (String membersInsertsStatment : membersInsertsStatments) {
			db.execSQL(membersInsertsStatment);
		}
	}


	@Override
	public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DELETE_TABLE_ACTIVITIES);
		db.execSQL(Event.DELETE_TABLE_EVENTS);
//		db.execSQL(Member.DELETE_TABLE_EVENTS);
	}


	@Override
	public void onDowngrade (SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}


	/**
	 * Inserts a row in the database, the fields are the table projetion
	 *
	 * @param type: used to switch the database table to insert the row
	 * @param fields: the column values to insert in the db.
	 * @param <G>: generic type to selec the table.
	 */
	public <G extends DBEntity> void insertElement (Class<G> type, String[] fields) {
		@SuppressWarnings("unused")
		long rowID = 0;
		SQLiteDatabase db = getWritableDatabase();

		ContentValues insertValues = new ContentValues();
		DBTableInfo dbTableInfo = DbUtils.getDBTableInfo(type);
		String[] tableProjection = dbTableInfo.getProjection();

		for (int i = 0; i < tableProjection.length; i++)
			insertValues.put(tableProjection[i], fields[i]);

		try {
			rowID = db.insertOrThrow(dbTableInfo
					.getTableName(), null, insertValues);

		} catch (SQLException e) {
			Log.e("[ERROR] fucverg.saulmm.gdg.data.db.DBHandler.insertElement ",
					"SQL Exception inserting: " + e.getCause());
		}
	}


	/**
	 * Makes a query to the database to retrieve a member by its id
	 *
	 * @param id: the id to the requested member.
	 * @return the member or null if has not been found.
	 */
	public Member getMemberById (String id) {
		final String selection = Member.COLUMN_NAME_ENTRY_ID + " LIKE ?";
		final String[] selectionArgs = {id};

		Member foundMember = null;

		try {
			foundMember = getAllElements(Member.class,
					selection, selectionArgs, false).get(0);

		} catch (IndexOutOfBoundsException ignored) {};

		return foundMember;
	}


	/**
	 * Makes a request to the database to retrieve a member by its name
	 *
	 * @param name: the name of the member.
	 * @return the found member or null is has not been found.
	 */
	public Member getMemberByName (String name) {
		final String selection = Member.COLUMN_NAME_NAME + " LIKE ?";
		final String[] selectionArgs = {name};

		Member foundMember = null;

		try {
			foundMember = getAllElements(Member.class,
					selection, selectionArgs, false).get(0);

		} catch (IndexOutOfBoundsException ignored) {}

		return foundMember;
	}


	/**
	 * Method used to retrieve all de database, selecting the table with the type, chosing the
	 * table columns by the table projection.
	 *
	 * @param type: the db entity.
	 * @param selection: may be null, the 'where' clause.
	 * @param args: may be null, the args of the, 'where' clause.
	 * @param reverse: a boolean ordering if the returning list has to be inverted.
	 * @return a list with all elements of the table.
	 */
	@SuppressWarnings("unchecked")
	public <G extends DBEntity> List<G> getAllElements (Class<G> type, String selection, String[] args, boolean reverse) {

		LinkedList<G> elementList = new LinkedList<G>();
		SQLiteDatabase db = getReadableDatabase();

		DBTableInfo dbTableInfo = DbUtils.getDBTableInfo(type);
		String[] tableProjection = dbTableInfo.getProjection();
		String tableName = dbTableInfo.getTableName();

		Cursor cursor = db.query(tableName,
				tableProjection, selection, args,
				null, null, null);

		if (cursor != null) {
			cursor.moveToFirst();

			if (cursor.getCount() > 0) {
				String[] fields = new String[cursor.getColumnCount()];

				do {
					for (int i = 0; i < fields.length; i++)
						fields[i] = cursor.getString(cursor.getColumnIndexOrThrow(
								tableProjection[i]));

					G resulted = null;
					try {
						resulted = (G) type.newInstance().createDBEntity(fields);

					} catch (InstantiationException e) {
						e.printStackTrace();

					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}

					if (reverse)
						elementList.addFirst(resulted);
					else
						elementList.add(resulted);

				} while ((cursor.moveToNext()));
			}
		}

		return elementList;
	}

}
