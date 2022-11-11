package com.humanities.history.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.logging.Logger;

@Getter @Setter @NoArgsConstructor
@Entity @Table( name = "history" )
public class History {

	private static final Logger LOGGER = Logger.getLogger(History.class.getName());

	@Id @GeneratedValue( strategy = GenerationType.IDENTITY ) // SEQUENCE?
	private Long id;
	private String datebegpre;  // '-'
	private String datebeg;     // '0004-00-00-00.00.00'
	private String dateendpre;  // '+'
	private String dateend;     // '0029-04-01-00.00.00'
	private String eramain;     // 'Roman Empire'
	private String locales;     // 'Israel , Jerusalem'
	private String personname;  // 'Jesus Christ'
	private String eventmain;   // 'birth , ministry , death , resurrection'
	private String referenced;  // 'Josephus, MaraBarSerapion, Phlegon, Thallus'
	private String tags;        // 'h0000'
	private String mediaicopath;// '_0000_H_Nazareth_JesusCross'

	public String showHistory( ) {
		//
		String newDateBeg = "";
		String  newDateEnd = "";
		String  txtLine;
		if ( this.datebeg == null || this.datebeg.length() > 4 ) { LOGGER.info(""); } else
		{ newDateBeg = datebeg.substring(0, 4); }
		if ( this.dateend == null || this.dateend.length() > 4 ) { LOGGER.info(""); } else
		{ newDateEnd = dateend.substring(0, 4); }
		//
		txtLine = this.id + " / "
			+ this.datebegpre + newDateBeg + " "
			+ this.dateendpre + newDateEnd + " / "
			+ this.locales + " / "
			+ this.personname + " / "
			+ this.eventmain + "";
		return txtLine;
	}
}