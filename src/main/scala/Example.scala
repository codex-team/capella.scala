import codex.capella.Capella

object Example extends App{

  Capella.uploadFile("./sobaki-6df25d04eb75.jpg") match {
    case Left(x) => println("Result: " + x)
    case Right(x) => println("Exception: " + x)
  }

  Capella.uploadUrl("https://ifmo.su/public/app/img/products/capella.png") match {
    case Left(x) => println("Result: " + x)
    case Right(x) => println("Exception: " + x)
  }

}
