package com.humanities.history.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter @Setter @NoArgsConstructor
public class HistoryView {

	private History history;

	private Map<String, String> eralist; // lombok requires @Getter if using statics
	private Map<String, String> localelist;
	private List<String> taglist;
}
