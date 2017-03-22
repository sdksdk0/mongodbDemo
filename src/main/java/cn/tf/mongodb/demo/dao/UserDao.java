package cn.tf.mongodb.demo.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

import cn.tf.mongodb.demo.bean.User;



public interface UserDao {

	List<User> get();

	User getOne(Integer id);

	
	public void findAndModify(Integer id, Integer age);

	void insert(User u);
	
	void removeOne(Integer id);
	
	//修改多条
	void update(User criteriaUser, User user) ;
	
	// 按条件查询, 分页 <br>
	public List<User> find(User criteriaUser, int skip, int limit);
	
	//根据条件查询出来后 再去修改
	public User findAndModify(User criteriaUser, User updateUser);
	//查询出来后 删除 
	public User findAndRemove(User criteriaUser) ;
	//统计
	public long count(User criteriaUser);
	//条件查询
	public Query getQuery(User criteriaUser);
	

}
