package cn.tf.mongodb.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tf.mongodb.demo.bean.User;
import cn.tf.mongodb.demo.dao.UserDao;

@Controller
@RequestMapping("user")
public class UserController {
	
	@Autowired
	UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@RequestMapping("test")
	@ResponseBody
	public String test(){
		return "hello";
	}
	
	@ResponseBody
	@RequestMapping("get")
	public List<User> get(HttpServletRequest request,HttpServletResponse response){
		List<User> user=userDao.get();
		System.out.println(this.getClass().getName()+":"+user);
		return user;
	}

}
