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

case class Employee(
                    id: Int,
                    name: String,
                    email: String,
                    userName: String,
                    age: Int,
                    gender: String,
                    password: String
                   )


private[models] trait EmployeeTable extends MysqlDBComponent{
  this: DBComponent =>
  import driver.api._

  //"experienced_employee" is the table name
  //Employee is case class <-> Can be replaced here with tuple
  private[models] class EmployeeTable(tag: Tag) extends Table[Employee](tag, "employee"){

    val id: Rep[Int] = column[Int]("id")
    val name: Rep[String] = column[String]("name")
    val userName: Rep[String] = column[String]("userName")
    val email: Rep[String] = column[String]("email", O.PrimaryKey)
    val age: Rep[Int] = column[Int]("age")
    val gender: Rep[String] = column[String]("gender")
    val password: Rep[String] = column[String]("password")

    def * = (id, name, email, userName, age, gender, password) <>(Employee.tupled, Employee.unapply)
  }

  val employeeTableQuery:TableQuery[EmployeeTable] = TableQuery[EmployeeTable] //employeeTableQuery is used to create and execute queries on EmployeeTable

}

trait EmployeeRepo extends EmployeeTable {

  this: DBComponent =>
  import driver.api._

  //db is the object that is specified with the details of DB

  def dropTable = db.run {employeeTableQuery.schema.drop}

  def create = db.run{ employeeTableQuery.schema.create } //db.run return the O/P wrapped in Future

  def insert(emp: Employee) = db.run{ employeeTableQuery += emp }

  def insertOrUpdate(emp: Employee) = db.run{ employeeTableQuery.insertOrUpdate(emp) }

  def delete(exp: Int) = db.run{ employeeTableQuery.filter(employee => employee.age <= 18).delete }

  def getAll()  = db.run{ employeeTableQuery.to[List].result}

  def update(email: String, name: String): Future[Int] = {
    val query = employeeTableQuery.filter(_.email === email)
      .map(_.name).update(name)

db.run(query)
}
}

object EmployeeRepo extends EmployeeRepo //with MysqlDBComponent
