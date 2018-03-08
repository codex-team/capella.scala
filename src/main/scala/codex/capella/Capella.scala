package codex.capella

import java.io._

import scalaj.http._

case class customException(smth: String) extends Exception(smth)

/**
  * Capella API object. Upload file to the capella.pics by URL or local path.
  */
object CapellaApi {

  /**
    * Upload API endpoint
    */
  val apiUrl = "https://capella.pics/upload"

  /**
    * Upload local image file to the capella.pics and get result.
    *
    * @param filepath - local path to a file
    * @return String answer from API or Exception
    */
  def uploadFile(filepath: String): Either[String, Exception] = {
    try {
      val fileBytes: BufferedInputStream = new BufferedInputStream(new FileInputStream(filepath))
      val byteArray = Stream.continually(fileBytes.read).takeWhile(_ != -1).map(_.toByte).toArray
      val response: HttpResponse[String] =
        Http(apiUrl).postMulti(MultiPart("file", "image", "application/octet-stream", byteArray)).asString
      Left(response.body.toString)
    } catch {
      case e: FileNotFoundException => Right(customException("File not found: " + filepath))
      case e: HttpStatusException => Right(customException("API return response with code: " + e.code))
      case e: Exception => Right(customException("Exception: " + e))
    }
  }

  /**
    * Upload image by URL to the capella.pics and get result.
    *
    * @param url - URL of an image
    * @return String answer from API or Exception
    */
  def uploadUrl(url: String): Either[String, Exception] = {
    try {
      val response: HttpResponse[String] = Http(apiUrl).postForm(Seq("link" -> url)).asString
      Left(response.body.toString)
    } catch {
      case e: HttpStatusException => Right(customException("API return response with code: " + e.code))
      case e: Exception => Right(customException("Exception: " + e))
    }
  }

}