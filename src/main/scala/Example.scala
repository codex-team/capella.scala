package codex

import codex.capella.Capella
import codex.capella.Filters

object Example extends App {

  Capella.uploadFile("./sobaki-6df25d04eb75.jpg") match {
    case Left(x) => println("Result: " + x)
    case Right(x) => println("Exception: " + x)
  }

  Capella.uploadUrl("https://ifmo.su/public/app/img/products/capella.png") match {
    case Left(x) => println("Result: " + x)
    case Right(x) => println("Exception: " + x)
  }

  val transformations = Seq(
    Filters.resize(700, 700),
    Filters.crop(500),
    Filters.resize(250),
    Filters.crop(200, 200),
    Filters.crop(100, 100, (50, 50)),
    Filters.pixelize(10)
  )

  val url = "https://capella.pics/07d4fa39-7465-474a-9e01-15a71bb71c32"

  println("New url: " + transformations.foldLeft(url){ (previousres, f) => f(previousres) })

}
