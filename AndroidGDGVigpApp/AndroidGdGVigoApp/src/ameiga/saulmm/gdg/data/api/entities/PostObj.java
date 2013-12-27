package ameiga.saulmm.gdg.data.api.entities;

import java.io.Serializable;

public class PostObj implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5554217947590655193L;
	public String objectType;
	public String content;
	public String url;
	public Repliers replies;
	public Plusoners plusoners;
	public Reshares resharers;
	public Attachments[] attachments;


	public String getObjectType () {
		return objectType;
	}


	public String getContent () {
		return content;
	}


	public String getUrl () {
		return url;
	}


	public Repliers getReplies () {
		return replies;
	}


	public Plusoners getPlusoners () {
		return plusoners;
	}


	public Reshares getResharers () {
		return resharers;
	}


	public Attachments[] getAttachments () {
		return attachments;
	}
}
