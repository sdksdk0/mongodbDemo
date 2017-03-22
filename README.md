# mongodbDemo
Spring+SpringMVC+MongoDB案例


     MongoDB是一个NoSql数据库，MongoDB 是一个基于分布式文件存储的数据库。由 C++ 语言编写。旨在为 WEB 应用提供可扩展的高性能数据存储解决方案。 MongoDB 是一个介于关系数据库和非关系数据库之间的产品，是非关系数据库当中功能最丰富，最像关系数据库的。其存储结构为BSON。

下面是通过一个Spring+SpringMVC+MongoDB的案例来说明mongodb的基本使用。mongdb的基本结构这里就不再重复说明。从入门到应用。源码地址：https://github.com/sdksdk0/mongodbDemo

一、入门

MongoDB的提供了一个面向文档存储， 将数据存储为一个文档，数据结构由键值(key=>value)对组成。MongoDB 文档类似于 JSON 对象。字段值可以包含其他文档，数组及文档数组。

首先从入门程序开始。

1、新建maven工程，导入mongodb的驱动包。

         <dependency>
                        <groupId>org.mongodb</groupId>
                        <artifactId>mongodb-driver</artifactId>
                        <version>3.4.2</version>
        </dependency>

2、连接数据库

                MongoClient  mongoClient=new MongoClient("127.0.0.1",27017);
                  //连接到数据库
                  MongoDatabase  mongoDatabase=mongoClient.getDatabase("tf");
                  System.out.println("连接成功");

3、创建集合并获取集合
          
                 mongoDatabase.createCollection("test");
                  //System.out.println("创建集合成功");
                  
                  //获取集合
                  MongoCollection<Document> collection = mongoDatabase.getCollection("test");

4、插入文档
   
      //插入文档
      public static void createDocument(MongoCollection<Document>  collection){
            
            Document  document=new Document("title","MongoDB")
                 .append("description", "database")
                 .append("likes", 100)
                 .append("by", "Fly");
            ArrayList<Document> documents = new ArrayList<Document>();
            documents.add(document);
            collection.insertMany(documents);
            System.out.println("文档插入成功");
            
      }


5、查询文档

      //查询文档
      public  static void  findIterable(MongoCollection<Document>  collection){
            FindIterable<Document>  findIterable=collection.find();
            MongoCursor<Document> iterator = findIterable.iterator();
            while(iterator.hasNext()){
                  System.out.println(iterator.next());
            }
      }




6、更新文档

    //更新文档
      public  static void  updateMany(MongoCollection<Document>  collection){
            collection.updateMany(Filters.eq("likes",100),new Document("$set",new Document("likes",200)));
            
            //查询文档
            findIterable(collection);
      }
      
7、删除文档

     //删除
      public  static void deleteMany(MongoCollection<Document>  collection){
            //删除符合条件的第一个文档
            
            collection.deleteOne(Filters.eq("likes",200));
            
            //删除所有符合条件的文档
            collection.deleteMany(Filters.eq("likes",200));
            
            //查询文档
            findIterable(collection);         
      }





二、应用

     这个案例的主要内容是，我们在spring中配置mongodb的数据源，如何可以添加修改删除和条件查询，因为是面向对象的，所以这里可以使用的是HQL语句来操作mongodb数据库。
这里主要用的是用户userId 、name、age来演示。

1、web.xml配置

<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xmlns="http://java.sun.com/xml/ns/javaee" xmlns:web="http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
      xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
      id="sys" version="2.5">
      <description>mongo</description>
      <display-name>service Java Deploy V1.0.0</display-name>
      <context-param>
            <param-name>webAppRootKey</param-name>
            <param-value>mongo</param-value>
      </context-param>
      <!-- 加载log4j -->
      <context-param>
            <param-name>log4jConfigLocation</param-name>
            <param-value>/WEB-INF/classes/sysconfig/properties/log4j.properties
            </param-value>
      </context-param>
      <!-- 加载xml config -->
      <context-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath*:syscoreConfig/*/*.xml
            classpath:sysconfig/spring/*.xml
            classpath:sysconfig/springmvc/*.xml
            </param-value>
      </context-param>
      <!-- 编码 -->
      <filter>
            <filter-name>encodingFilter</filter-name>
            <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
            <init-param>
                  <param-name>encoding</param-name>
                  <param-value>UTF-8</param-value>
            </init-param>
            <init-param>
                  <param-name>forceEncoding</param-name>
                  <param-value>true</param-value>
            </init-param>
      </filter>
      <filter-mapping>
            <filter-name>encodingFilter</filter-name>
            <url-pattern>/*</url-pattern>
      </filter-mapping>
      <!-- 定义LOG4J监听器 -->
      <listener>
            <listener-class>org.springframework.web.util.Log4jConfigListener</listener-class>
      </listener>
      <!-- 监听 自动装配ApplicationContext的配置信息 -->
      <listener>
            <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
      </listener>
      <!-- spring mvc -->
      <servlet>
            <servlet-name>springMvc</servlet-name>
            <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
            <init-param>
                  <param-name>ContextConfigLocation</param-name>
                  <param-value>classpath:sysconfig/spring-servlet.xml</param-value>
            </init-param>
            <load-on-startup>1</load-on-startup>
      </servlet>
      <servlet-mapping>
            <servlet-name>springMvc</servlet-name>
            <url-pattern>/</url-pattern>
      </servlet-mapping>
      <welcome-file-list>
            <welcome-file>index.jsp</welcome-file>
      </welcome-file-list>
</web-app>




2、spring-mvc.xml配置

扫描注解：

<!-- com.rui.pro.dao.*.impl/**,com.rui.pro.service.*.impl/**,com.rui.pro.controller/** -->
      <context:component-scan base-package=" **.dao.***,**.service.***,**.controller.**"/>
      <bean
            class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping">
            <property name="order" value="0" />
      </bean>
      <bean id="messageAdapter"
            class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter">
            <property name="messageConverters">
                  <list>
                        <bean
                              class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter" />
                  </list>
            </property>
      </bean>
      <bean
            class="org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter" />
      <bean class="org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter" />
      <bean id="exceptionMessageAdapter"
            class="org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver">
            <property name="messageConverters">
                  <list>
                        <!-- Support JSON MappingJacksonHttpMessageConverter -->
                        <bean
                              class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter" />
                  </list>
            </property>
      </bean>





3、db.xml配置
这里配置的是mongodb数据库的属性以及一些包

      <mongo:mongo id="mongo" host="localhost" port="27017">
            <mongo:options connections-per-host="8"
                  threads-allowed-to-block-for-connection-multiplier="4"
                  connect-timeout="1000" max-wait-time="1500" auto-connect-retry="true"
                  socket-keep-alive="true" socket-timeout="1500" slave-ok="true"
                  write-number="1" write-timeout="0" write-fsync="true" />
      </mongo:mongo>
      <mongo:db-factory id="mongoDbFactory" dbname="tf"
            mongo-ref="mongo" />
      <mongo:mapping-converter base-package="cn.tf.mongodb.demo.bean"
            id="mongoConverter" />
      <bean id="mongoTemplate" class="org.springframework.data.mongodb.core.MongoTemplate">
            <constructor-arg name="mongoDbFactory" ref="mongoDbFactory" />
            <constructor-arg name="mongoConverter" ref="mongoConverter" />
      </bean>
      <mongo:repositories base-package="cn.tf.mongodb.demo.bean" />
      <bean id="userDao" class="cn.tf.mongodb.demo.dao.impl.UserDaoImpl">
            <property name="mongoTemplate" ref="mongoTemplate" />
      </bean>

      <!-- 扫描实现 -->
      <context:annotation-config />



4、bean类

      private String id;
      private Integer userId;
      private String name;
      private Integer age;

设置其setter/getter方法、构造方法、toString等。


5、Dao层


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


6、实现类

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


添加成功之后我们可以使用db.user.find(）命令查看。




7、controller层

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

8、我们可以在浏览器中访问








源码地址：https://github.com/sdksdk0/mongodbDemo



