package com.humanities.history.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter @Setter @NoArgsConstructor
public class HistoryView {

	private String datebeg;     // '-0004'
	private String dateend;     // '+0029'
	private String eramain;     // 'Roman Empire'
	private String locales;     // 'Israel , Jerusalem'
	private String personname;  // 'Jesus Christ'
	private String eventmain;   // 'birth , ministry , death , resurrection'
	private String referenced;  // 'Josephus, MaraBarSerapion, Phlegon, Thallus'
	private String tags;        // 'h0000'
	private String mediaicopath;// '_0000_H_Nazareth_JesusCross'

	private Map<String, String> eralist; // lombok requires @Getter if using statics
	private Map<String, String> localelist;
	private List<String> taglist;
}
