package app

import java.text.SimpleDateFormat
import java.util.Date

object DateUtility {
  var dt: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
  
  def getCurrentDate(): String = {
    var dt: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    dt.format(new Date())
  }
  
  def getDateFromString(date: String) : Date = {
    dt.parse(date)
  }
  
  
}