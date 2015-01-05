package app.conrollers

import org.springframework.web.bind.annotation.RestController
import user._
import user.wallet._
import collection.JavaConverters._
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.util.UriComponentsBuilder
import scala.beans.BeanProperty
import org.apache.log4j.Logger
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.http.HttpStatus
import javax.validation.Valid
import org.springframework.validation.BindingResult
import scala.util.control.Exception
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import scala.collection.mutable.MutableList
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import com.fasterxml.jackson.databind.ObjectMapper
import java.text.SimpleDateFormat
import java.text.DateFormat
import app.DateUtility
import exception.BadRequestException
import exception.NotModifiedException
import exception.NotModifiedException
import service.UserService
import service.RoutingNumberValidator

@RequestMapping(value = Array("/api/v1"))
@RestController
class WalletController {

  // Get a user by user_id
  @RequestMapping(method = Array(RequestMethod.GET), value = Array("/users/{user_id}"))
  def getUsers(@PathVariable user_id: String, request: HttpServletRequest, response: HttpServletResponse): User = {

    var user: User = UserService.getUser(user_id)

    var requestEtag: String = request.getHeader("ETag")
    var objectEtag: String = DateUtility.getDateFromString(user.getUpdated_at).getTime().toString

    if (requestEtag != null && requestEtag.equals(objectEtag)) {
      throw new NotModifiedException
    } else {
      response.addHeader("ETag", DateUtility.getDateFromString(user.getUpdated_at).getTime().toString)
      user
    }
  }

  // Create a new user
  @RequestMapping(method = Array(RequestMethod.POST), value = Array("/users"))
  @ResponseStatus(HttpStatus.CREATED)
  def setUser(@RequestBody @Valid userRequest: User, bindingResult: BindingResult): Any = {
    if (bindingResult.hasErrors()) {
      throw new BadRequestException(filterErrorFields(bindingResult) + "-" + "Create User")
    } else {
      UserService.addUser(userRequest)
      userRequest
    }
  }

  // Update an existing user by user_id
  @RequestMapping(method = Array(RequestMethod.PUT), value = Array("/users/{user_id}"))
  @ResponseStatus(HttpStatus.CREATED)
  def updateUser(@RequestBody userRequest: User, @PathVariable user_id: String): User = {
    UserService.updateUser(user_id, userRequest)
  }

  // Operations for user id cards
  @RequestMapping(method = Array(RequestMethod.POST), value = Array("/users/{user_id}/idcards"))
  @ResponseStatus(HttpStatus.CREATED)
  def addUserIdCard(@RequestBody @Valid userRequest: IdCard, bindingResult: BindingResult, @PathVariable user_id: String): Any = {

    if (bindingResult.hasErrors()) {
      throw new BadRequestException(filterErrorFields(bindingResult) + "-" + "Create User Id Card")
    } else {
      UserService.addUserIdCard(user_id, userRequest)
    }
  }

  @RequestMapping(method = Array(RequestMethod.GET), value = Array("/users/{user_id}/idcards"))
  def getUserIdCards(@PathVariable user_id: String): java.util.List[IdCard] = {
    UserService.getUser(user_id).idCards.values.toList.asJava
  }

  @RequestMapping(method = Array(RequestMethod.DELETE), value = Array("/users/{user_id}/idcards/{card_id}"))
  @ResponseStatus(HttpStatus.NO_CONTENT)
  def removeUserIdCard(@PathVariable user_id: String, @PathVariable card_id: String) {
    UserService.removeUserIdCard(user_id, card_id)
  }

  // Operations for user web logins

  @RequestMapping(method = Array(RequestMethod.POST), value = Array("/users/{user_id}/weblogins"))
  @ResponseStatus(HttpStatus.CREATED)
  def addUserWebLogin(@RequestBody @Valid userRequest: WebLogin, bindingResult: BindingResult, @PathVariable user_id: String): WebLogin = {
    if (bindingResult.hasErrors()) {
      throw new BadRequestException(filterErrorFields(bindingResult) + "-" + "Create User Web Login")
    } else {
      UserService.addUserWebLogin(user_id, userRequest)
    }
  }

  @RequestMapping(method = Array(RequestMethod.GET), value = Array("/users/{user_id}/weblogins"))
  def getUserWebLogins(@PathVariable user_id: String): java.util.List[WebLogin] = {
    UserService.getUser(user_id).webLogins.values.toList.asJava
  }

  @RequestMapping(method = Array(RequestMethod.DELETE), value = Array("/users/{user_id}/weblogins/{login_id}"))
  @ResponseStatus(HttpStatus.NO_CONTENT)
  def removeUserWebLogin(@RequestBody userRequest: String, @PathVariable user_id: String, @PathVariable login_id: String) {
    UserService.removeUserWebLogin(user_id, login_id)
  }

  // Operations for user bank accounts		
  @RequestMapping(method = Array(RequestMethod.POST), value = Array("/users/{user_id}/bankaccounts"))
  @ResponseStatus(HttpStatus.CREATED)
  def addUserBankAccount(@RequestBody @Valid userRequest: BankAccount, bindingResult: BindingResult, @PathVariable user_id: String): BankAccount = {
    if (bindingResult.hasErrors()) {
      throw new BadRequestException(filterErrorFields(bindingResult) + "-" + "Create User Bank Account")
    } else {
      // Validation of Routing Number
      var bankName: String = RoutingNumberValidator.validateRN(userRequest.getRouting_number)
      if(bankName.equals("")) {
        throw new BadRequestException("Routing Number" + "-" + "Create User Bank Account")
      } else {
    	UserService.addUserBankAcc(user_id, new BankAccount(userRequest.getAccount_number,userRequest.getRouting_number,bankName))
      }
      
      //UserService.addUserBankAcc(user_id, userRequest)
    }
  }

  @RequestMapping(method = Array(RequestMethod.GET), value = Array("/users/{user_id}/bankaccounts"))
  def getUserBankAccount(@PathVariable user_id: String): java.util.List[BankAccount] = {
    UserService.getUser(user_id).bankAccouts.values.toList.asJava
  }

  @RequestMapping(method = Array(RequestMethod.DELETE), value = Array("/users/{user_id}/bankaccounts/{ba_id}"))
  @ResponseStatus(HttpStatus.NO_CONTENT)
  def removeUserBankAccount(@RequestBody userRequest: String, @PathVariable user_id: String, @PathVariable ba_id: String) {
    UserService.removeUserBankAcc(user_id, ba_id)
  }

  @ExceptionHandler(value = Array(classOf[BadRequestException]))
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  def handleBadRequestException(e: Exception): Any = {
    createCustomError(e.getMessage())
  }

  def createCustomError(message: String): Error = {
    new Error("Following required fields are not provided/valid: " + message.split("-")(0), message.split("-")(1))
  }

  def filterErrorFields(bindingResult: BindingResult): String = {
    var errorFields: String = ""
    var errorList: MutableList[String] = MutableList()
    for (error <- bindingResult.getFieldErrors().asScala) {
      errorList.+=(error.getField())
    }
    for (error <- errorList.distinct) {
      errorFields = if (errorFields == "") error else errorFields + ", " + error
    }
    errorFields
  }

  @ExceptionHandler(value = Array(classOf[NotModifiedException]))
  @ResponseStatus(HttpStatus.NOT_MODIFIED)
  @ResponseBody
  def handleNotModifiedException(e: Exception): Any = {
    ""
  }

}