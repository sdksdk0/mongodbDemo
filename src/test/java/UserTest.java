

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.tf.mongodb.demo.bean.User;
import cn.tf.mongodb.demo.dao.UserDao;

//"classpath:sysconfig/springmvc/spring-mvc.xml",

@org.junit.runner.RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:sysconfig/spring/db.xml" })
public class UserTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Autowired
	UserDao userDao;

	/**
	 * 查询
	 * **/
	@Test
	public void getAll() {
		List<User> list = userDao.get();
		System.out.println("list:" + list);

		User user = userDao.getOne(1);
		System.out.println("user:" + user);
	}
	
	@Test
	public void findAndUpdate(){
		userDao.findAndModify(1, 10);
	}
	
	@Test
	public void insert(){
		User user=new User();
		user.setUserId(1);
		user.setName("tf1");
		user.setAge(100);
		userDao.insert(user);
	}
	
	@Test
	public void removeOne(){
		userDao.removeOne(1);
	}

}
