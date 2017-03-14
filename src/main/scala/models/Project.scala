package models

import com.example.connection.PostgresDBComponent
import connection.MysqlDBComponent
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

/**
  * Created by knoldus on 10/3/17.
  */

case class Project(empId: Int, name: String)

trait ProjectTable extends EmployeeTable with MysqlDBComponent{

  private[example] class ProjectTable(tag: Tag) extends Table[Project](tag, "project"){

    val empId: Rep[Int] = column[Int]("id", O.PrimaryKey)
    val name: Rep[String] = column[String]("name")

    def employeeProjectFK = foreignKey(
      "employee_project_FK", empId, employeeTableQuery
    )(_.id)
    def * = (empId, name) <>(Project.tupled, Project.unapply)
  }


  val projectTableQuery:TableQuery[ProjectTable] = TableQuery[ProjectTable] //employeeTableQuery is used to create and execute queries on EmployeeTable

}

trait ProjectRepo extends ProjectTable {

  //db is the object that is specified with the details of DB
  val db = Database.forConfig("myPostgresDB") //myPostgresDB is the name of configuration in application.conf

  def dropTable = db.run {projectTableQuery.schema.drop}

  def create = db.run{ projectTableQuery.schema.create } //db.run return the O/P wrapped in Future

  def insert(project: Project) = db.run{ projectTableQuery += project }

  def delete(empId: Int) = db.run{ projectTableQuery.filter(project => project.empId === empId).delete }

  def update(id: Int, name: String): Future[Int] = {
    val query = projectTableQuery.filter(_.empId === id)
      .map(_.name).update(name)

    db.run(query)
  }
}

object ProjectRepo extends ProjectRepo with MysqlDBComponent
