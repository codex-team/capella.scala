import codex.capella.{CapellaApi, FilterArg, Filters}
import com.joefkelley.argyle._
import spray.json._
import DefaultJsonProtocol._

import scala.util.{Failure, Success}

case class CapellaConfig(filters: List[String], file: Option[String], url: Option[String])
case class CapellaApiOutput(id: String, url: String, success: Boolean, message: String)

object MyJsonProtocol extends DefaultJsonProtocol {
  implicit val capellaApiOutput = jsonFormat4(CapellaApiOutput)
}

import MyJsonProtocol._

object Run extends App {

  /**
    * Process arguments
    *
    * @param args - cli parameters (filter, file, url)
    */
  def process(args: CapellaConfig): Unit = {

    // result URL
    var url = ""

    // load local file to Capella
    if (args.file.isDefined) {
      CapellaApi.uploadFile(args.file.get) match {
        case Left(x) => url = x.parseJson.convertTo[CapellaApiOutput].url
        case Right(x) => println("Exception: " + x)
      }
    }

    // load file to Capella by URL
    if (args.url.isDefined) {
      CapellaApi.uploadUrl(args.url.get) match {
        case Left(x: String) => url = x.parseJson.convertTo[CapellaApiOutput].url
        case Right(x) => println("Exception: " + x)
      }
    }

    // apply filters from -i argument
    val filterChain: Seq[String => String] = args.filters.map(
      (filter) => filter.split(":") match {
        case Array(filterType: String, params: String) =>
          Filters.callFilter(FilterArg(filterType, params.split(",").map(x => x.toInt)))
        case _ => (x: String) => x
      }
    )

    // if URL is empty, let's take it from stdin
    if (url.isEmpty) {
      url = io.Source.stdin.getLines().next
    }

    println(Filters.applyFilters(url, filterChain))

  }

  val a = (
    repeated[String]("--filter", "-i") and
    optional[String]("--file", "-f") and
    optional[String]("--url", "-u")
    ).to[CapellaConfig]

  a.parse(args) match {
    case Success(params) => process(params)
    case Failure(e) => throw e
  }


}