package entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.JDBCTools;

// ֪ͨ
public class Notice {
	// ֪ͨ��ţ�����
	private static int id = 0;
	// ����
	private String title;
	// ������ --> nullΪ������
	private String receiver;
	// ����Ա
	private String sender;
	// ֪ͨ����
	private String content;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Notice [id=" + id + ", title=" + title + ", receiver=" + receiver + ", sender=" + sender + ", content="
				+ content + "]";
	}

	@SuppressWarnings("finally")
	public static String showAllNotice() {
		Statement statement = null;
		ResultSet rSet = null;
		String returnMsg = "";
		try {
			statement = JDBCTools.getConnection().createStatement();
			String sql = "select id,sender,content from notice order by id desc";
			rSet = statement.executeQuery(sql);
			while (rSet.next()) {
				String tmpMsg = "Notice" + rSet.getString("id") + "��(Sender��" + rSet.getString("sender") + ")<br>"
						+ rSet.getString("content") + "<br>";
				returnMsg = returnMsg + tmpMsg;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTools.release(null, statement, rSet);
			return returnMsg;
		}
	}

}
