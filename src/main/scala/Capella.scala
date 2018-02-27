package codex.capella

import scalaj.http._
import java.io._

case class customException(smth:String)  extends Exception(smth)

object Capella extends App {

  val apiUrl = "https://capella.pics/upload"

  def uploadFile(filepath: String): Either[String, Exception] = {
    try {

      val fileBytes: BufferedInputStream = new BufferedInputStream(new FileInputStream(filepath))
      val byteArray = Stream.continually(fileBytes.read).takeWhile(_ != -1).map(_.toByte).toArray
      val response: HttpResponse[String] = Http(apiUrl).postMulti(MultiPart("file", "image", "application/octet-stream", byteArray)).asString

      Left(response.body.toString)

    } catch {
      case e: FileNotFoundException => Right(customException("File not found: " + filepath))
      case e: HttpStatusException => Right(customException("API return response with code: " + e.code))
      case e: Exception => Right(customException("Exception: " + e))
    }
  }

  def uploadUrl(url: String): Either[String, Exception] = {
    try {
      val response: HttpResponse[String] = Http(apiUrl).postForm(Seq("link" -> url)).asString

      Left(response.body.toString)

    } catch {
      case e: HttpStatusException => Right(customException("API return response with code: " + e.code))
      case e: Exception => Right(customException("Exception: " + e))
    }
  }

  //  uploadFile("C:\\sobaki-6df25d04eb75.jpg") match {
  //    case Left(x) => println("Result: " + x)
  //    case Right(x) => println("Exception: " + x)
  //  }
  //

  uploadUrl("https://ifmo.su/public/app/img/products/capella.png") match {
    case Left(x) => println("Result: " + x)
    case Right(x) => println("Exception: " + x)
  }

}