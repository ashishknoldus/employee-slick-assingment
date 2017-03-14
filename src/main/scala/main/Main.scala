package main

import models.{Dependent, DependentRepo, EmployeeRepo}

object Main {
  def main(args: Array[String]): Unit = {

    DependentRepo.create
    EmployeeRepo.create

  }
}
