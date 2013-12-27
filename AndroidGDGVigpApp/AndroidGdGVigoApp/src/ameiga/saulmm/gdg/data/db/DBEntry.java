package ameiga.saulmm.gdg.data.db;

import ameiga.saulmm.gdg.data.db.entities.DBEntity;

public abstract class DBEntry extends DBEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 329076823672534333L;
	abstract public String getTableName();
	abstract public String[] getProjection();
}
