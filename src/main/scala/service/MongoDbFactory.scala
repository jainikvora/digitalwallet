package service

import org.springframework.context.ApplicationContext
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import app.config.MongoConfig

object MongoDbFactory {
  
  val ctx: ApplicationContext = new AnnotationConfigApplicationContext(classOf[MongoConfig])
  
  def mongoTemplate() : MongoOperations = {
    ctx.getBean("mongoTemplate").asInstanceOf[MongoOperations]
  }
  
}