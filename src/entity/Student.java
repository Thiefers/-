package entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import util.JDBCTools;

// ѧ��
public class Student extends User {
	// ѧԺ
	private String school;
	// �༶
	private String class_name;
	// רҵ
	private String major;
	// ��ʦ
	private String teacher_id;
	// ��ϵ�绰
	private String mobile;
	// ���⸨����
	private String select_topic = "null";
	// ����
	private Topic topic;
	// ���ͳ�ȥ������ID
	private ArrayList<Integer> dataIDList;

	public ArrayList<Integer> getDataIDList() {
		return dataIDList;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(String teacher_id) {
		this.teacher_id = teacher_id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSelect_topic() {
		return select_topic;
	}

	public void setSelect_topic(String select_topic) {
		this.select_topic = select_topic;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", password=" + password + ", sex=" + sex + ", school=" + school
				+ ", class_name=" + class_name + ", major=" + major + ", teacher_id=" + teacher_id + ", mobile="
				+ mobile + "]";
	}

	// ����gogogo
	// �޸���Ϣ
	@SuppressWarnings("finally")
	public boolean changeInfo(String iid, String password, String sex, String learn_class, String major,
			String teacher_id, String mobile) {
		Statement statement = null;
		int count = 0;
		try {
			statement = JDBCTools.getConnection().createStatement();
			// password,sex,school,class,major,teacher_id,mobile
			String sql = "update student set password='" + password + "',sex='" + sex + "',class='" + learn_class
					+ "',major='" + major + "',teacher_id='" + teacher_id + "',mobile='" + mobile + "' where id='" + iid
					+ "'";
			count = statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTools.release(null, statement, null);
			return count > 0 ? true : false;
		}

	}

	// ѡ��
	// public void selectTopic() {
	//
	// }

	// �鿴֪ͨ
	// public void readNotice() {
	//
	// }

	// �鿴����
	// public void readData() {
	//
	// }

	// �ϴ�����
	@SuppressWarnings("finally")
	public boolean sendData(String sid, String sname, String content) {
		Statement statement = null;
		int count = 0;
		try {
			statement = JDBCTools.getConnection().createStatement();
			int permission = 0;
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

	// �鿴����
	@SuppressWarnings("finally")
	public String readCommand(String stuID) {
		Statement statement = null;
		ResultSet rSet = null;
		String returnMsg = "";
		try {
			statement = JDBCTools.getConnection().createStatement();
			String sql = "select guider_command from comment where sid='" + stuID + "'";
			rSet = statement.executeQuery(sql);
			while (rSet.next()) {
				String tmp = rSet.getString("guider_command") + "<br>";
				returnMsg = returnMsg + tmp;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTools.release(null, statement, rSet);
			return returnMsg;
		}
	}

	@SuppressWarnings("finally")
	public String getTeacIDByTopic(int topicID) {
		// ����topic���õ�creater
		// ����teacher���õ�id
		// �õ�id��newһ��teacher����
		// Ȼ�����checknum + 1
		// select a.id from teacher a,topic b where a.name=b.creater and b.id=iMsg
		Statement statement = null;
		ResultSet rSet = null;
		String returnMsg = "";
		try {
			statement = JDBCTools.getConnection().createStatement();
			// select a.id from teacher a,topic b where a.name=b.creater and b.id=iMsg
			String sql = "select a.id from teacher a,topic b where a.name=b.creater and b.id=" + topicID;
			rSet = statement.executeQuery(sql);
			while (rSet.next()) {
				returnMsg = rSet.getString("a.id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTools.release(null, statement, rSet);
			return returnMsg;
		}
	}

	@SuppressWarnings("finally")
	public boolean updateTeacID(String stuID, String teacID) {
		Statement statement = null;
		int count = 0;
		try {
			statement = JDBCTools.getConnection().createStatement();
			String sql = "update student set teacher_id='" + teacID + "' where id='" + stuID + "'";
			count = statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTools.release(null, statement, null);
			return count > 0 ? true : false;
		}
	}

	@SuppressWarnings("finally")
	public boolean hasTeacher(String stuID) {
		Statement statement = null;
		ResultSet rSet = null;
		String msg = "";
		try {
			statement = JDBCTools.getConnection().createStatement();
			String sql = "select teacher_id from student where id='" + stuID + "'";
			rSet = statement.executeQuery(sql);
			while (rSet.next()) {
				msg = rSet.getString("teacher_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTools.release(null, statement, rSet);
			return msg.trim().length() > 6 ? true : false;
		}
	}
}
