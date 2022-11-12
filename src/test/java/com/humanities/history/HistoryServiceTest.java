package com.humanities.history;

import com.humanities.history.model.History;
import com.humanities.history.services.IHistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HistoryServiceTest {

	private static final String EOL = "\n";
	private static final String DLM = "\t";
	private static final String FRMT = "\t%-5s %s\n";
	@Autowired private IHistoryService historyService;

	@Test void test_history_showHistory( ) {
		//
		History history = new History();
		String txtLines = String.format(FRMT, "history", history.showHistory());
		//
		System.out.println(txtLines);
		assertTrue(txtLines.contains("history"));
	}

	@Test void test_historyService_showHistory( ) {

		History history = new History();
		// history = historyService.findById(2L);
		System.out.println(history.showHistory());
		assertNotNull(history);
	}

	@Test void test_JDBC( ) {
		//
		String txtLines = "";
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/mydb";
		String user = System.getenv("MYSQL_USER"); // compare getProperty
		String pass = System.getenv("MYSQL_PASS");
		//
		// datebeg, dateend, eramain, locales, personname, eventmain, referenced, tags, mediaicopath
		String dateBeg = "0000";
		String dateEnd = "1000";
		String query = "SELECT * FROM history;";
		String queryRange = "SELECT * FROM history WHERE "
			+ "datebeg >= ? AND dateEnd < ? "
			+ "ORDER BY datebeg;";
		try {
			Connection connection = DriverManager.getConnection(url, user, pass);
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			// preparedStatement.setString(1, dateBeg);
			// preparedStatement.setString(2, dateEnd);
			ResultSet resultSet = preparedStatement.executeQuery();
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int intCols = resultSetMetaData.getColumnCount();
			while ( resultSet.next() ) {
				String txtLine = "";
				for ( int ictr = 1; ictr < intCols; ictr++ ) { txtLine += resultSet.getString(ictr) + DLM; }
				txtLines += txtLine + EOL;
			}
		}
		catch (SQLException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		System.out.println(txtLines);
		assertNotNull(txtLines);
	}

	@Test void test_Dates( ) {

		String txtLines = "";
		Instant instant = Instant.now();
		txtLines += "instant: " + instant + EOL;

		String dateFull = "2022-11-12T02:57:05.596291700Z";
		Instant instantFull = Instant.parse(dateFull);
		Instant instantZero = Instant.parse("2022-11-12T00:00:00.00Z");
		txtLines += "instantFull: " + instantFull + EOL;
		txtLines += "instantZero: " + instantZero + EOL;

		String[] dates = { "04", "2022", "-1500", "-5" };
		DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy");
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
		LocalDate localDate;
		Date date;
		for ( String year : dates ) {
			try {
				date = dateFormat.parse(year);
				localDate = Instant.ofEpochMilli(date.getTime())
					.atZone(ZoneId.systemDefault())
					.toLocalDate();
				txtLines += date + DLM + localDate + EOL;
			}
			catch (ParseException ex) { System.out.println(ex.getMessage()); }
			//	txtLines += LocalDate.parse(year, DTF) + EOL;
		}

		System.out.println(txtLines);
		assertNotNull(txtLines);
	}
}
