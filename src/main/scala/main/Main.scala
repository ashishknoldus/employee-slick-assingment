package main

import models.{Dependent, DependentRepo, Employee, EmployeeRepo}
import scala.concurrent.ExecutionContext.Implicits.global


object Main {
  def main(args: Array[String]): Unit = {

    val create  = EmployeeRepo.create
    val insertEmployee = EmployeeRepo.insert(Employee(12, "Ashish Tomer", "ashish1269@gmail.com", "ashish1269", 24, "male", "1234567"))
    val deleteEmployee = EmployeeRepo.delete(23)
    val updateEmployee = EmployeeRepo.update("mahesh@gmail.com", "Mahesh Singh Kandpal")

    val insertOrUpdateEmployee = EmployeeRepo.insertOrUpdate(Employee(12, "Ashish Tomer", "ashish1269@gmail.com", "ashishtomer", 24, "male", "@$#1$#t0m3r"))

    val result = insertEmployee.map{ result => s"$result row inserted"}.recover {
      case ex: Throwable => ex.getMessage
    }

    val result2 = deleteEmployee.map{ result => s"$result row inserted"}.recover {
      case ex: Throwable => ex.getMessage
    }

    val result3 = updateEmployee.map{ result => s"$result row inserted"}.recover {
      case ex: Throwable => ex.getMessage
    }

    val result4 = insertOrUpdateEmployee.map{ result => s"$result row inserted"}.recover {
      case ex: Throwable => ex.getMessage
    }

    result.map(output => println(s"Insert : $output"))
    result2.map(output => println(s"Delete : $output"))
    result3.map(output => println(s"Update : $output"))
    result4.map(output => println(s"InsertOrUpdate : $output"))

    Thread.sleep(2000)

  }
}
