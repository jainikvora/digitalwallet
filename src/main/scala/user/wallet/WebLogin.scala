package user.wallet

import java.util.Calendar
import scala.beans.BeanProperty
import com.fasterxml.jackson.annotation.JsonProperty
import scala.annotation.meta.beanGetter
import javax.validation.constraints.NotNull
import org.hibernate.validator.constraints.NotEmpty
import java.util.UUID
import org.springframework.data.mongodb.core.mapping.Document

@Document
class WebLogin (
    @JsonProperty("url") 
    @BeanProperty 
    @(NotNull @beanGetter) 
    @(NotEmpty @beanGetter)
    val url: String
    	
    , @JsonProperty("login") 
    @BeanProperty 
    @(NotNull @beanGetter) 
    @(NotEmpty @beanGetter)
    val login: String
    
    , @JsonProperty("password") 
    @BeanProperty 
    @(NotNull @beanGetter) 
    @(NotEmpty @beanGetter)
    val password: String) {
  @BeanProperty val login_id = UUID.randomUUID().toString()
}