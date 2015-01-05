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
class IdCard (
    @JsonProperty("card_name") 
    @BeanProperty 
    @(NotNull @beanGetter)
    @(NotEmpty @beanGetter)
    val card_name: String
    	
    , @JsonProperty("card_number") 
    @BeanProperty 
    @(NotNull @beanGetter) 
    @(NotEmpty @beanGetter)
    val card_number: String
    	
    , @JsonProperty("expiration_date") 
    @BeanProperty 
    val expiration_date: String) {
  @BeanProperty val card_id = UUID.randomUUID().toString()
}