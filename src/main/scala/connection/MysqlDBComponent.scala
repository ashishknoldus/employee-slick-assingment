package connection

/**
  * Created by knoldus on 13/3/17.
  */

trait MysqlDBComponent extends DBComponent{

  val driver  = slick.jdbc.MySQLProfile

  import driver.api._

  val db: Database = Database.forConfig("db")
}
