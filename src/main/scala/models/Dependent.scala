package models

/**
  * Created by knoldus on 13/3/17.
  */
import connection.MysqlDBComponent
import connection.DBComponent

import scala.concurrent.Future

/**
  * Created by knoldus on 10/3/17.
  */

case class Dependent(
                     name: String,
                     email: String,
                     relation: String,
                     empId: Int
                   )


private[models] trait DependentTable extends MysqlDBComponent with EmployeeTable{
  this: DBComponent =>
  import driver.api._

  //"experienced_employee" is the table name
  //Employee is case class <-> Can be replaced here with tuple
  private[models] class DependentTable(tag: Tag) extends Table[Dependent](tag, "dependent"){

    val name: Rep[String] = column[String]("name")
    val relation: Rep[String] = column[String]("relation")
    val email: Rep[String] = column[String]("email", O.PrimaryKey)
    val empId: Rep[Int] = column[Int]("empId")

    def employeeDependentFK = foreignKey(
      "employee_dependent_FK", empId, employeeTableQuery
    )(_.id)


    def * = (name, email, relation, empId) <>(Dependent.tupled, Dependent.unapply)
  }

  val dependentTableQuery:TableQuery[DependentTable] = TableQuery[DependentTable] //DependentTableQuery is used to create and execute queries on EmployeeTable

}

trait DependentRepo extends DependentTable {

  this: DBComponent =>
  import driver.api._

  //db is the object that is specified with the details of DB
  // val db = Database.forConfig("mySqlDb") //mySqlDb is the name of configuration in application.conf

  def dropTable = db.run {dependentTableQuery.schema.drop}

  def create = db.run{ dependentTableQuery.schema.create } //db.run return the O/P wrapped in Future

  def insert(dependent: Dependent) = db.run{ dependentTableQuery += dependent }

  def delete(id: Int) = db.run{ dependentTableQuery.filter(dependent => dependent.empId === id).delete }

  def update(email: String, name: String): Future[Int] = {
    val query = dependentTableQuery.filter(_.email === email)
      .map(_.name).update(name)

    db.run(query)
  }
}

object DependentRepo extends DependentRepo with MysqlDBComponent
