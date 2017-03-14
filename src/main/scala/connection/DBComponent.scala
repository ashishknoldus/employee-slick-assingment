package connection

/**
  * Created by knoldus on 13/3/17.
  */
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile

trait DBComponent {

  val driver: JdbcProfile
  import driver.api._
  val db: Database

}