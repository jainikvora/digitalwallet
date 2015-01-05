package service

import org.springframework.data.repository.Repository
import org.springframework.data.mongodb.core.MongoOperations
import scala.collection.mutable.HashMap
import user.User
import app.DateUtility
import user.wallet.IdCard
import user.wallet.WebLogin
import user.wallet.BankAccount
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Criteria.where

object UserService {
  val COLLECTION_NAME: String = "Users"
  var mongoOps: MongoOperations = MongoDbFactory.mongoTemplate

  var users: HashMap[String, User] = HashMap()

  def getUser(userId: String): User = {
    UserDao.getUser(userId)
  }

  def addUser(newUser: User): String = {
    UserDao.addUser(newUser)
    newUser.getUser_id
  }

  def updateUser(userId: String, updatedUser: User): User = {
    var existingUser: User = UserDao.getUser(userId)
    existingUser.setName(updatedUser.getName)
    existingUser.setEmail(updatedUser.getEmail)
    existingUser.setPassword(updatedUser.getPassword)
    existingUser.setUpdated_at(DateUtility.getCurrentDate)
    UserDao.updateUser(existingUser)
    UserDao.getUser(userId) 
  }

  def addUserIdCard(userId: String, newIdCard: IdCard): IdCard = {
    var existingUser: User = UserDao.getUser(userId)
    existingUser.addIdCard(newIdCard)
    UserDao.updateUser(existingUser)
    UserDao.getUser(userId).getIdCard(newIdCard.getCard_id)
  }

  def removeUserIdCard(userId: String, cardId: String) {
    var existingUser: User = UserDao.getUser(userId)
    existingUser.removeIdCard(cardId)
    UserDao.updateUser(existingUser)
  }

  def addUserWebLogin(userId: String, newWebLogin: WebLogin): WebLogin = {
    var existingUser: User = UserDao.getUser(userId)
    existingUser.addWebLogin(newWebLogin)
    UserDao.updateUser(existingUser)
    UserDao.getUser(userId).getWebLogin(newWebLogin.getLogin_id)
  }

  def removeUserWebLogin(userId: String, loginId: String) {
    var existingUser: User = UserDao.getUser(userId)
    existingUser.removeWebLogin(loginId)
    UserDao.updateUser(existingUser)
  }

  def addUserBankAcc(userId: String, newBankAcc: BankAccount): BankAccount = {
    var existingUser: User = UserDao.getUser(userId)
    existingUser.addBankAccount(newBankAcc)
    UserDao.updateUser(existingUser)
    UserDao.getUser(userId).getBankAccount(newBankAcc.getBa_id)
  }

  def removeUserBankAcc(userId: String, baId: String) {
    var existingUser: User = UserDao.getUser(userId)
    existingUser.removeBankAccount(baId)
    UserDao.updateUser(existingUser)
  }
}