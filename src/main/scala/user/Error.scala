package user

import scala.beans.BeanProperty

class Error (@BeanProperty var message: String, @BeanProperty var requestMethod: String){

}