package app

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.SpringApplication
import org.springframework.context.annotation.ComponentScan
//remove if not needed

object Application {
    def main(args: Array[String]) {
    SpringApplication.run(classOf[Application])
  } 

}

@ComponentScan
@EnableAutoConfiguration
class Application {

}
