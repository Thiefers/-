package entity;

// ����
public class Comment {
	// ���۱��
	private static int id = 0;
	// ѧ��ѧ��
	private String sid;
	// ����
	private String name;
	// ѧ��רҵ
	private String major;
	// ������Ŀ
	private String paper_title;
	// ��ʦ����
	private String guider_command;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getPaper_title() {
		return paper_title;
	}

	public void setPaper_title(String paper_title) {
		this.paper_title = paper_title;
	}

	public String getGuider_command() {
		return guider_command;
	}

	public void setGuider_command(String guider_command) {
		this.guider_command = guider_command;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", sid=" + sid + ", name=" + name + ", major=" + major + ", paper_title="
				+ paper_title + ", guider_command=" + guider_command + "]";
	}

}
