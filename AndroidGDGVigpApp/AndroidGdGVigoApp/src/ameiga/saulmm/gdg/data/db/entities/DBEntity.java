package ameiga.saulmm.gdg.data.db.entities;

import java.io.Serializable;

public abstract class DBEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1341054414578082231L;
	public abstract DBEntity createDBEntity (String [] fields);
	abstract public String getTableName();
	abstract public String[] getProjection();
}
