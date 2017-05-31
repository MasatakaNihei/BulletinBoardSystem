package beans;

import java.io .Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
public class UserBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	@Size(min = 6, max = 20, message = "ログインIDは6～20文字です。")
	@NotNull(message = "ログインIDは必須項目です。")
	//Pattern(regexp = "^[\\d\\u\\l]+$", message = "ログインIDに使えるのは半角英数字のみです。")
	private String loginId;
	
	@NotNull(message = "パスワードは必須項目です。")
	@Size(min = 6, max = 255, message = "パスワードは6～255文字です。" )
	@Pattern(regexp = "^[ -~｡-ﾟ]+$", message ="パスワードに使えるのは半角文字(記号可)のみです。")
	private String password;
	
	@NotNull(message = "ユーザー名は必須項目です。")
	@Size(min = 1 , max = 10, message = "ユーザー名は10文字以内です。")
	private String name;
	
	@NotNull(message = "支店は必須項目です。")
	@Size(min = 1, message = "支店は必須項目です。")
	private String branchId;
	
	private String branchName;
	private String positionId;
	private String positionName;
	private String isStopped;
	private Date createdAt;
	private Date updatedAt;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBranchID() {
		return branchId;
	}
	public void setBranchID(String branchID) {
		this.branchId = branchID;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getPositionId() {
		return positionId;
	}
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getIsStopped() {
		return isStopped;
	}
	public void setIsStopped(String isStopped) {
		this.isStopped = isStopped;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	

}
