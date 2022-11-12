package com.humanities.history.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
import java.util.logging.Logger;

@Getter @Setter @NoArgsConstructor
@Entity @Table( name = "history" )
public class History {

	private static final Logger LOGGER = Logger.getLogger(History.class.getName());

	// datebeg, dateend, eramain, locales, personname, eventmain, referenced, tags, mediaicopath
	@Id @GeneratedValue( strategy = GenerationType.IDENTITY ) // SEQUENCE?
	private Long id;
	private String datebeg;     // '0004-00-00-00.00.00'
	private String dateend;     // '0029-04-01-00.00.00'
	private String eramain;     // 'Roman Empire'
	private String locales;     // 'Israel , Jerusalem'
	private String personname;  // 'Jesus Christ'
	private String eventmain;   // 'Star of Bethlehem'
	private String referenced;  // ''
	private String tags;        // 'h0000'
	private String mediaicopath;// '_0000_H_Nazareth_JesusCross'
	private Date datecre;       // creation
	private Date datemod;       // modified
	private String user;        // user

	public History(String datebeg, String eventmain) {

		this.datebeg=datebeg;
		this.eventmain=eventmain;
	}

	public String showHistory( ) {

		String txtLine = this.id + " / "
			+ this.datebeg + " "
			+ this.dateend + " / "
			+ this.locales + " / "
			+ this.personname + " / "
			+ this.eventmain + "";
		return txtLine;
	}
}
