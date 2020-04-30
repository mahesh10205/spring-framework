package com.advance.act.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

	private static final long serialVersionUID = -8008609305283522933L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column( nullable = false, length = 50)
	private String firstName;
	
	@Column( nullable = false, length = 50)
	private String lastName;
	
	@Column( nullable = false, length = 50)
	private String email;
	

	@Column(nullable = false, length = 50)
	private String userId;
	
	@Column( nullable = false, length = 500)
	private String encryptedPwd;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEncryptedPwd() {
		return encryptedPwd;
	}

	public void setEncryptedPwd(String encryptedPwd) {
		this.encryptedPwd = encryptedPwd;
	}
}
