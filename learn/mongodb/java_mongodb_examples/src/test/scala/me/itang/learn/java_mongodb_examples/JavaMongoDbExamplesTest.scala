package me.itang.learn.java_mongodb_examples

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import com.mongodb.Mongo
import com.mongodb.BasicDBObject
import com.mongodb.DBCursor
import org.scalatest.BeforeAndAfterEach
import com.mongodb.DB
import com.mongodb.DBCollection
import com.mongodb.util.JSON
import com.mongodb.DBObject

trait Dbable { self: MongoDbTestSuite =>
  def withMongo[T](proc: Mongo => T) = {
    val m = mongo
    proc(m)
    m.close()
  }
  def withDb[T](name: String = "testDb")(proc: DB => T) = {
    withMongo { m =>
      proc(m.getDB(name))
    }
  }

  def withCollection[T](name: String)(proc: DBCollection => T) = {
    withDb() { db =>
      proc(db.getCollection(name))
    }
  }

  def dropDb(implicit dbName: String = Db) = withMongo { m =>
    m.dropDatabase(Db)
  }
}
class MongoDbTestSuite extends FunSuite with ShouldMatchers with BeforeAndAfterEach with Dbable {
  protected implicit val Db = "testDb"

  override def beforeEach() {
    dropDb()
    withDb() { db =>
      db.addUser("itang", "test".toCharArray)
    }
  }

  protected def mongo = new Mongo("localhost", 27017)
}

class JavaMongoDbExamplesTest extends MongoDbTestSuite {
  // Set up the temp file needed by the test
  test("connection") {
    import collection.JavaConversions._
    mongo.getDatabaseNames.foreach(println)
  }

  test("get db") {
    withDb() { _ should not be (null) }
  }

  test("get Collections") {
    withCollection("users") { users =>
      println(users.count)
    }
  }

  test("create a document to store key and value") {
    withCollection("users") { users =>
      val document = new BasicDBObject
      document.put("name", "livetang")
      document.put("pwd", "test")
      users.insert(document)

      // search query
      val searchQuery = new BasicDBObject
      searchQuery.put("name", "livetang")

      // query it
      val cursor: DBCursor = users.find(searchQuery);
      // loop over the cursor and display the retrieved result
      cursor.next().get("name") should be("livetang")
    }
  }

  test("authenticate") {
    withDb() { db =>
      db.authenticate("itang", "test".toCharArray) should be(true)
      //    evaluating {
      //      testDb.authenticate("itang", "bad".toCharArray)
      //    } should produce[com.mongodb.MongoException]
    }
  }
  test("authenticate_bad") {
    withDb() { db =>
      db.authenticate("itang", "bad".toCharArray) should be(false)
      db.getCollection("users").count
    }
  }

  test("insert document") {
    withCollection("c1") { c1 =>
      val document = new BasicDBObject()
      document.put("database", "testDb")
      document.put("table", "hosting")

      val documentDetail = new BasicDBObject()
      documentDetail.put("records", "99")
      documentDetail.put("index", "vps_index1")
      documentDetail.put("active", "true")

      document.put("detail", documentDetail)

      c1.insert(document)

      c1.find().count should be(1)
      c1.find().next.get("detail").asInstanceOf[BasicDBObject].
        get("active") should be("true")
    }
  }

  test("update document") {
    withCollection("c1") { c1 =>
      def hostB() = c1.findOne(new BasicDBObject().append("hosting", "hostB"))

      init(c1) //

      c1.count should be(3)

      hostB.get("type") should be("dedicated server")

      val newDocument = new BasicDBObject();
      newDocument.put("hosting", "hostB");
      newDocument.put("type", "shared host");
      newDocument.put("clients", 111);
      c1.update(new BasicDBObject().append("hosting", "hostB"), newDocument)

      hostB.get("type") should be("shared host")
    }
  }

  test("delete document") {
    withCollection("c1") { c1 =>
      def findByHosting(v: String) = c1.findOne(new BasicDBObject().append("hosting", v))
      def hostB() = findByHosting("hostB")
      init(c1) //

      c1.count should be(3)

      c1.remove(new BasicDBObject().append("hosting", "hostB"))
      c1.count should be(2)
      hostB() should be(null)

      c1.remove(findByHosting("hostC"))
      c1.count should be(1)

    }
  }

  private def init(col: DBCollection) = {
    val jsonList = List("""{  "hosting" : "hostA" , "type" : "vps" , "clients" : 1000}""",
      """ {  "hosting" : "hostB" , "type" : "dedicated server" , "clients" : 100}""",
      """{"hosting" : "hostC" , "type" : "vps" , "clients" : 900}""")
    jsonList.map(it => JSON.parse(it).asInstanceOf[DBObject])
      .foreach { it =>
        col.insert(it)
      }
  }

}


