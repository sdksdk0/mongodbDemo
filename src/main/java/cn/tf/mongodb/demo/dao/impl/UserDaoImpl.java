package cn.tf.mongodb.demo.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import cn.tf.mongodb.demo.bean.User;
import cn.tf.mongodb.demo.dao.UserDao;
import cn.tf.mongodb.demo.service.UserService;
import cn.tf.mongodb.demo.service.impl.UserServiceImpl;

@Repository
public class UserDaoImpl implements UserDao {

	UserService  userService=new UserServiceImpl();
	
	
	public List<User> get() {
		
		return userService.get();
	}
	

	public User getOne(Integer id) {
		return userService.getOne(id);
	}

	public void findAndModify(Integer id, Integer age) {
		userService.findAndModify(id, age);
	}

	public void insert(User u) {
		userService.insert(u);
	}

	public void removeOne(Integer id) {
		userService.removeOne(id);

	}

	/**
	 * 修改多条 <br>
	 * ------------------------------<br>
	 * 
	 * @param criteriaUser
	 * @param user
	 */
	public void update(User criteriaUser, User user) {
		userService.update(criteriaUser, user);
	}

	//按条件查询, 分页 <br>

	public List<User> find(User criteriaUser, int skip, int limit) {
		return userService.find(criteriaUser, skip, limit);
	}

	/**
	 * 根据条件查询出来后 再去修改 <br>
	 * ------------------------------<br>
	 * 
	 * @param criteriaUser
	 *            查询条件
	 * @param updateUser
	 *            修改的值对象
	 * @return
	 */
	public User findAndModify(User criteriaUser, User updateUser) {
		return userService.findAndModify(criteriaUser, updateUser);
	}

	/**
	 * 查询出来后 删除 <br>
	 * ------------------------------<br>
	 * 
	 * @param criteriaUser
	 * @return
	 */
	public User findAndRemove(User criteriaUser) {
		return userService.findAndRemove(criteriaUser);
	}

	/**
	 * 统计
	 * ------------------------------<br>
	 * 
	 * @param criteriaUser
	 * @return
	 */
	public long count(User criteriaUser) {
		return  userService.count(criteriaUser);
	}

	/**
	 * 条件查查询
	 * 
	 * @param criteriaUser
	 * @return
	 */
	public Query getQuery(User criteriaUser) {
		return userService.getQuery(criteriaUser);
	}

}
