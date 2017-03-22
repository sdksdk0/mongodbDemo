package cn.tf.mongodb.demo.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import cn.tf.mongodb.demo.bean.User;
import cn.tf.mongodb.demo.dao.UserDao;

@Repository
public class UserDaoImpl implements UserDao {

	private MongoOperations mongoTemplate;

	// @Autowired
	// @Qualifier("mongoTemplate")
	public void setMongoTemplate(MongoOperations mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public List<User> get() {
		List<User> user = mongoTemplate.findAll(User.class);
		return user;
	}

	public User getOne(Integer id) {
		User user = mongoTemplate.findOne(new Query(Criteria.where("userId")
				.is(id)), User.class);
		return user;
	}

	public void findAndModify(Integer id, Integer age) {
		/*mongoTemplate.updateFirst(new Query(Criteria.where("userId").is(id)),
				new Update().inc("age", age), User.class);*/
		
		mongoTemplate.updateFirst(new Query(Criteria.where("userId").is(id)),
				new Update().set("age", age), User.class);
	}

	public void insert(User u) {
		mongoTemplate.insert(u);
	}

	public void removeOne(Integer id) {
		Criteria criteria = Criteria.where("userId").in(id);

		if (criteria != null) {
			Query query = new Query(criteria);
			if (query != null
					&& mongoTemplate.findOne(query, User.class) != null)
				mongoTemplate.remove(mongoTemplate.findOne(query, User.class));
		}

	}

	/**
	 * 修改多条 <br>
	 * ------------------------------<br>
	 * 
	 * @param criteriaUser
	 * @param user
	 */
	public void update(User criteriaUser, User user) {
		Criteria criteria = Criteria.where("age").gt(criteriaUser.getAge());
		;
		Query query = new Query(criteria);
		Update update = Update.update("name", user.getName()).set("age",
				user.getAge());
		mongoTemplate.updateMulti(query, update, User.class);
	}

	/**
	 * 按条件查询, 分页 <br>
	 * ------------------------------<br>
	 * 
	 * @param criteriaUser
	 * @param skip
	 * @param limit
	 * @return
	 */
	public List<User> find(User criteriaUser, int skip, int limit) {
		Query query = getQuery(criteriaUser);
		query.skip(skip);
		query.limit(limit);
		return mongoTemplate.find(query, User.class);
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
		Query query = getQuery(criteriaUser);
		Update update = Update.update("age", updateUser.getAge()).set("name",
				updateUser.getName());
		return mongoTemplate.findAndModify(query, update, User.class);
	}

	/**
	 * 查询出来后 删除 <br>
	 * ------------------------------<br>
	 * 
	 * @param criteriaUser
	 * @return
	 */
	public User findAndRemove(User criteriaUser) {
		Query query = getQuery(criteriaUser);
		return mongoTemplate.findAndRemove(query, User.class);
	}

	/**
	 * count <br>
	 * ------------------------------<br>
	 * 
	 * @param criteriaUser
	 * @return
	 */
	public long count(User criteriaUser) {
		Query query = getQuery(criteriaUser);
		return mongoTemplate.count(query, User.class);
	}

	/**
	 * 条件查询
	 * 
	 * @param criteriaUser
	 * @return
	 */
	public Query getQuery(User criteriaUser) {
		if (criteriaUser == null) {
			criteriaUser = new User();
		}
		Query query = new Query();
		if (criteriaUser.getId() != null) {
			Criteria criteria = Criteria.where("userId").is(criteriaUser.getId());
			query.addCriteria(criteria);
		}
		if (criteriaUser.getAge() > 0) {
			Criteria criteria = Criteria.where("age").gt(criteriaUser.getAge());
			query.addCriteria(criteria);
		}
		if (criteriaUser.getName() != null) {
			Criteria criteria = Criteria.where("name").regex(
					"^" + criteriaUser.getName());
			query.addCriteria(criteria);
		}
		return query;
	}

}
