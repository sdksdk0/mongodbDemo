package cn.tf.mongodb.demo.bean;

import java.io.Serializable;

//@Document( collection = "user" )
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private Integer userId;
	private String name;
	private Integer age;

	public User() {

	}

	public User(Integer userId, String name, Integer age) {
		this.userId = userId;
		this.name = name;
		this.age = age;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userId=" + userId + ", name=" + name
				+ ", age=" + age + "]";
	}

}
