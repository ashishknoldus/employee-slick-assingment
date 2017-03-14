package main

import models.{Dependent, DependentRepo, Employee, EmployeeRepo}
import scala.concurrent.ExecutionContext.Implicits.global


object Main {
  def main(args: Array[String]): Unit = {


    val insertEmployee = EmployeeRepo.insert(Employee(23, "Mahesh Kandpal", "mahesh@gmail.com", "mahesh1234", 23, "male", "1@mm@#3$#"))

    val result = insertEmployee.map{ result => s"$result row inserted"}.recover {
      case ex: Throwable => ex.getMessage
    }

    result.map(println(_))
    Thread.sleep(10000)

  }
}
