package service

import com.mashape.unirest.http.Unirest
import com.mashape.unirest.http.HttpResponse
import com.mashape.unirest.http.JsonNode

object RoutingNumberValidator {
  
  def validateRN(routingNumber: String): String = {
    var bankName: String = ""
    var jsonResponse: HttpResponse[JsonNode] = Unirest.get("https://www.routingnumbers.info/api/data.json?rn="+routingNumber)
    		 .asJson()
    if(jsonResponse.getBody().getObject().getInt("code") == 200) {
      bankName = jsonResponse.getBody().getObject().getString("customer_name")
    }
    bankName
  }
}