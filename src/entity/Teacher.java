package entity;

import java.lang.Thread.State;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.JDBCTools;

// ��ʦ
public class Teacher extends User {
	// ��ʦ�칫�ҵ绰
	private String office_phone;
	// ��ʦ˽�˵绰
	private String mobile;
	// ÿλ��ʦ���ɴ�ѧ����
	private int max_number;

	public String getOffice_phone() {
		return office_phone;
	}

	public void setOffice_phone(String office_phone) {
		this.office_phone = office_phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getMax_number() {
		return max_number;
	}

	public void setMax_number(int max_number) {
		this.max_number = max_number;
	}

	@Override
	public String toString() {
		return "Teacher [id=" + id + ", name=" + name + ", password=" + password + ", sex=" + sex + ", office_phone="
				+ office_phone + ", mobile=" + mobile + ", max_number=" + max_number + "]";
	}

	// ����gogogo
	// �޸���Ϣ
	@SuppressWarnings("finally")
	public boolean changeInfo(String id, String password, String sex, String office_phone, String mobile,
			String max_number) {
		Statement statement = null;
		int count = 0;
		try {
			statement = JDBCTools.getConnection().createStatement();
			String sql = "update teacher set password='" + password + "',sex='" + sex + "',office_phone='"
					+ office_phone + "',mobile='" + mobile + "',max_number='" + max_number + "'" + " where id='" + id
					+ "'";
			count = statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTools.release(null, statement, null);
			return count > 0 ? true : false;
		}
	}

	// �ϴ�����
	@SuppressWarnings("finally")
	public boolean sendData(String sid, String sname, String content, int permission) {
		Statement statement = null;
		int count = 0;
		try {
			statement = JDBCTools.getConnection().createStatement();
			String sql = "insert into data(sender_id,sender_name,title,permission) values('" + sid + "','" + sname
					+ "','" + content + "'," + permission + ")";
			count = statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTools.release(null, statement, null);
			return count > 0 ? true : false;
		}
	}

	// ��Ȩ
	@SuppressWarnings("finally")
	public boolean grantData(int dataID) {
		Statement statement = null;
		int count = 0;
		try {
			statement = JDBCTools.getConnection().createStatement();
			String sql = "update data set permission=" + 0 + " where id=" + dataID;
			count = statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTools.release(null, statement, null);
			return count > 0 ? true : false;
		}
	}

	// ����
	@SuppressWarnings("finally")
	public boolean revokeData(int dataID) {
		Statement statement = null;
		int count = 0;
		try {
			statement = JDBCTools.getConnection().createStatement();
			String sql = "update data set permission=" + 1 + " where id=" + dataID;
			count = statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTools.release(null, statement, null);
			return count > 0 ? true : false;
		}
	}

	// ��������
	// student��teacher_id��id����sid��
	// teacher��name��creater����id��
	// topic��name��paper_title����creater��
	// student��name��sname����major��major����sid��
	// �ı�����sid��guider_command
	// comment��sid��sname��major��paper_title��guider_command
	// �ܹ�������student��teacher��topic
	// String teacID = select teacher_id from student where id = sid;
	// String teacName = select name from teacher where id = teacID;
	// String paper_title = select name from topic where creater = teacName;
	// String sname = select name from student where id = sid;
	// insert into comment(sid,sname,major,paper_title,guider_command) values();
	@SuppressWarnings("finally")
	public boolean sendComment(String sid, String guider_command) {
		Statement statement = null;
		ResultSet rSet = null;
		int count = 0;
		String msg = "";
		String sql = "";
		try {
			statement = JDBCTools.getConnection().createStatement();
			sql = "select teacher_id from student where id='" + sid + "'";
			rSet = statement.executeQuery(sql);
			String teacID = "";
			while (rSet.next()) {
				teacID = rSet.getString("teacher_id");
			}
			sql = "select name from teacher where id='" + teacID + "'";
			String teacName = "";
			rSet = statement.executeQuery(sql);
			while (rSet.next()) {
				teacName = rSet.getString("name");
			}
			sql = "select name from topic where creater='" + teacName + "'";
			String paper_title = "";
			rSet = statement.executeQuery(sql);
			while (rSet.next()) {
				paper_title = rSet.getString("name");
			}
			sql = "select name,major from student where id='" + sid + "'";
			String sname = "";
			String major = "";
			rSet = statement.executeQuery(sql);
			while (rSet.next()) {
				sname = rSet.getString("name");
				major = rSet.getString("major");
			}
			sql = "insert into comment(sid,sname,major,paper_title,guider_command) values('" + sid + "','" + sname
					+ "','" + major + "','" + paper_title + "','" + guider_command + "')";
			count = statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTools.release(null, statement, rSet);
			return count > 0 ? true : false;
		}
	}

	@SuppressWarnings("finally")
	public String readMyComment(String teacID) {
		Statement statement = null;
		ResultSet rSet = null;
		String returnMsg = "";
		try {
			statement = JDBCTools.getConnection().createStatement();
			String sql = "select sid,guider_command from comment where sid in(select id from student where teacher_id='"
					+ teacID + "')";
			rSet = statement.executeQuery(sql);
			while (rSet.next()) {
				String tmp = "TO" + rSet.getString("sid") + "��" + rSet.getString("guider_command") + "|";
				returnMsg = returnMsg + tmp;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTools.release(null, statement, rSet);
			return returnMsg;
		}
	}
}
