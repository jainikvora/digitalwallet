package service

import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Criteria.where
import user.User

object UserDao {
  val COLLECTION_NAME: String = "Users"
  var mongoOps: MongoOperations = MongoDbFactory.mongoTemplate
  
  def getUser(userId: String): User = {
    mongoOps.findOne(new Query(where("_id").is(userId)), classOf[User], COLLECTION_NAME )
  }
  
  def addUser(user: User) = {
    mongoOps.insert(user, COLLECTION_NAME)
  }
  
  def updateUser(user: User) = {
    mongoOps.save(user, COLLECTION_NAME )
  }

}