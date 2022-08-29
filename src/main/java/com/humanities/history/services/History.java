package com.humanities.history.services;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter @Setter @EqualsAndHashCode @NoArgsConstructor
@Entity @Table( name = "history" )
public class History {

	@Id @GeneratedValue( strategy = GenerationType.IDENTITY ) // SEQUENCE?
	private Long id;
	private String datebegpre;  // '-'
	private String datebeg;     // '0004-00-00-00.00.00'
	private String dateendpre;  // '+'
	private String dateend;     // '0029-04-01-00.00.00'
	private String eramain;     // 'Roman Empire'
	private String locations;   // 'Israel , Jerusalem'
	private String personname;  // 'Jesus Christ'
	private String eventmain;   // 'birth , ministry , death , resurrection'
	private String referenced;  // 'Josephus, MaraBarSerapion, Phlegon, Thallus'
	private String groupings;   // 'h0000'
	private String mediaicopath;// '_0000_H_Nazareth_JesusCross'

	public static History getSample( ) {
		//
		History history = new History();
		history.id = 0L;
		//
		history.datebegpre = "-";
		history.datebeg = "begins";
		history.dateendpre = "-";
		history.dateend = "ending";
		//
		history.eramain = "eramain";
		history.locations = "locations";
		history.personname = "personname";
		history.eventmain = "eventmain 历史";
		//
		history.referenced = "referenced";
		history.groupings = "groupings";
		history.mediaicopath = "mediaicopath";
		//
		return history;
	}

	public String showHistory( ) {
		//
		String newDateBeg = "", newDateEnd = "";
		if ( this.datebeg == null || this.datebeg.length() > 4 ) { }
		{ newDateBeg = datebeg.substring(0, 4); }
		if ( this.dateend == null || this.dateend.length() > 4 ) { }
		{ newDateEnd = dateend.substring(0, 4); }
		//
		String txtLine = this.id + " / "
			+ this.datebegpre + newDateBeg + " "
			+ this.dateendpre + newDateEnd + " / "
			+ this.locations + " / "
			+ this.personname + " / "
			+ this.eventmain + "";
		return txtLine;
	}
}
