package app.config

import com.mongodb.Mongo
import org.springframework.data.mongodb.config.AbstractMongoConfiguration
import org.springframework.context.annotation.Configuration
import com.mongodb.MongoClient
import org.springframework.data.authentication.UserCredentials
import com.mongodb.MongoCredential
import org.springframework.data.mongodb.MongoDbFactory

@Configuration
class MongoConfig extends AbstractMongoConfiguration {
  def getDatabaseName:String = "walletdb"
  
  override
  def getUserCredentials() : UserCredentials = new UserCredentials("walletapp","wa113t")
  
  def mongo:Mongo = new MongoClient("ds047040.mongolab.com",47040)
  
}