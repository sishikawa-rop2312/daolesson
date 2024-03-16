package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import model.User;

public class UsersDAO {
	Connection con;
	PreparedStatement stmt;
	ResultSet rs;

	// DB接続
	private void connect() throws NamingException, SQLException {
		Context context = new InitialContext();
		DataSource ds = (DataSource) context.lookup("java:comp/env/mariadb_daolesson");
		this.con = ds.getConnection();
	}

	// DB切断
	private void disconnect() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 全件取得
	public List<User> findAll() {
		List<User> userList = new ArrayList<>();

		try {
			this.connect();

			// select文準備
			String sql = "select * from users";
			this.stmt = this.con.prepareStatement(sql);

			// select文を実行
			this.rs = stmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int age = rs.getInt("age");

				User user = new User(id, name, age);
				userList.add(user);
			}
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			this.disconnect();
		}

		return userList;
	}

	// 1件登録
	public boolean insertOne(User user) {
		try {
			this.connect();

			// insert文準備
			String sql = "insert into users(name, age) value (?, ?)";
			this.stmt = this.con.prepareStatement(sql);
			
			// INSERT文中の「？」に使用する値を設定してSQL文w完成
			stmt.setString(1, user.getName());
			stmt.setInt(2, user.getAge());

			// INSERTを実行（resultには追加された行数が代入される）
			int result = stmt.executeUpdate();
			
			if (result != 1) {
				return false;
			}
			
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			this.disconnect();
		}

		return true;
	}
}
