package cn.tf.mongodb.test;

/*import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;*/

public class MongoDBJDBC {
	
	
	public static void main(String[] args) {
		/*try {
			MongoClient  mongoClient=new MongoClient("127.0.0.1",27017);

			//连接到数据库
			MongoDatabase  mongoDatabase=mongoClient.getDatabase("tf");
			System.out.println("连接成功");
			
			//mongoDatabase.createCollection("test");
			//System.out.println("创建集合成功");
			
			//获取集合
			MongoCollection<Document> collection = mongoDatabase.getCollection("test");
			
			//插入文档
			createDocument(collection);
			
			//查询文档
			findIterable(collection);
			
			//更新文档
			//updateMany(collection);
			
			//删除文档
			//deleteMany(collection);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	
	
	
	
	
	
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
	
	//查询文档
	public  static void  findIterable(MongoCollection<Document>  collection){
		FindIterable<Document>  findIterable=collection.find();
		MongoCursor<Document> iterator = findIterable.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
	}
	
	
	
	//更新文档
	public  static void  updateMany(MongoCollection<Document>  collection){
		collection.updateMany(Filters.eq("likes",100),new Document("$set",new Document("likes",200))); 
		
		//查询文档
		findIterable(collection);
	}
	
	//删除
	public  static void deleteMany(MongoCollection<Document>  collection){
		//删除符合条件的第一个文档
		
		collection.deleteOne(Filters.eq("likes",200));
		
		
		//删除所有符合条件的文档
		collection.deleteMany(Filters.eq("likes",200));
		
		//查询文档
		findIterable(collection);
		
		*/
		
	}

}
