package instilled.playground;

import static instilled.playground.ds.tables.Author.AUTHOR;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.stream.Stream;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

public class JOOQ {

	public void query() {
		String userName = "root";
		String password = null;
		String url = "jdbc:mysql://localhost:3306/sandbox";

		// Connection is the only JDBC resource that we need
		// PreparedStatement and ResultSet are handled by jOOQ, internally
		try (Connection conn = DriverManager.getConnection(url, userName, password)) {
			DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
			try (Stream<Record> s = ctx.select().from(AUTHOR).stream()) {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
