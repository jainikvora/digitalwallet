package user

import java.util.Calendar
import user.wallet._
import scala.beans.BeanProperty
import org.apache.log4j.Logger
import scala.collection.mutable.MutableList
import scala.collection.immutable.HashMap
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.validator.constraints.NotEmpty
import javax.validation.constraints.NotNull
import scala.annotation.meta.beanGetter
import java.util.Date
import java.text.SimpleDateFormat
import app.DateUtility
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document(collection="Users")
class User(
    @JsonProperty("email") 
    @BeanProperty 
    @(NotNull @beanGetter) 
    @(NotEmpty @beanGetter)
    var email: String
    	
    , @JsonProperty("password") 
    @BeanProperty 
    @(NotNull @beanGetter) 
    @(NotEmpty @beanGetter)
    var password: String
    	
    , @JsonIgnoreProperties(ignoreUnknown=true) 
    @JsonProperty("name") 
    @BeanProperty
    var name: String) {

  
  @Id
  @BeanProperty val user_id: String = UUID.randomUUID().toString()
  @BeanProperty val created_at: String = DateUtility.getCurrentDate
  @BeanProperty var updated_at: String = DateUtility.getCurrentDate

  var bankAccouts: HashMap[String, BankAccount] = HashMap()
  var idCards: HashMap[String, IdCard] = HashMap()
  var webLogins: HashMap[String, WebLogin] = HashMap()
  
  def getIdCard(cardId: String): IdCard = {
    this.idCards(cardId)
  }

  def addIdCard(idCardToAdd: IdCard) {
    this.idCards = this.idCards + (idCardToAdd.getCard_id -> idCardToAdd)
  }

  def removeIdCard(cardId: String) {
    this.idCards = this.idCards.-(cardId)
  }

  def getWebLogin(loginId: String): WebLogin = {
    this.webLogins(loginId)
  }

  def addWebLogin(webLogin: WebLogin) {
    this.webLogins = this.webLogins + (webLogin.getLogin_id -> webLogin)
  }

  def removeWebLogin(loginId: String) {
    this.webLogins = this.webLogins -(loginId)
  }

  def getBankAccount(accId: String): BankAccount = {
    this.bankAccouts(accId)
  }
  
  def addBankAccount(ba: BankAccount) {
    this.bankAccouts = this.bankAccouts + (ba.getBa_id -> ba)
  }
  
  def removeBankAccount(accId: String) {
    this.bankAccouts = this.bankAccouts.-(accId)
  }

}