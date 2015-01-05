package user.wallet

import java.util.Calendar
import scala.beans.BeanProperty
import com.fasterxml.jackson.annotation.JsonProperty
import scala.annotation.meta.beanGetter
import javax.validation.constraints.NotNull
import org.hibernate.validator.constraints.NotEmpty
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document
class BankAccount(
    @JsonProperty("account_number") 
    @BeanProperty 
    @(NotNull @beanGetter) 
    @(NotEmpty @beanGetter)
    val account_number: String
    	
    , @JsonProperty("routing_number") 
    @BeanProperty 
    @(NotNull @beanGetter) 
    @(NotEmpty @beanGetter)
    val routing_number: String
    
    , @JsonProperty("account_name") 
    @BeanProperty 
    val account_name: String) {
  @BeanProperty val ba_id = UUID.randomUUID().toString()
}