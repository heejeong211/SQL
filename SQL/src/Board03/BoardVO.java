package Board03;

import java.sql.Date;

//테이블의 데이터를 담거나
//사용자의 데이터를 담을 Value Object 객체, (캡슐화 사용-사용자가 코드를 알 필요가 없음)
//Data Transfer Object(DTO) 라고도 부를 수 있다.
//컨트롤러라는 애가 sql만 처리함.

public class BoardVO {
	private int id;
	private String title;
	private String content;
	private Date writtenDate;
	private Date updatedDate;
	
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
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	 public Date getWrittenDate() {
		 return writtenDate;
	 }
	 
	 public void setWrittenDate(Date writtenDate) {
		 this.writtenDate = writtenDate;
	 }
	 
	 public Date getUpdatedDate () {
		 return updatedDate;
	 }
	 
	 public void setUpdatedDate(Date updatedDate) {
		 this.updatedDate = updatedDate;
	 }
	 
	 public String toString() {
		 return id + "\t" + title + "\t" + writtenDate;
	 }
}
//이것만 가져다 써도 됨.
