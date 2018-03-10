import codex.capella.{FilterArg, Filters}
import com.joefkelley.argyle._

import scala.util.{Failure, Success}

case class CapellaConfig(f: List[String])

trait Filter

case class Crop(breed: String, name: String) extends Filter

case class Resize(breed: String, name: String) extends Filter

case class Pixelize(pixels: Int) extends Filter


object Run extends App {

  def process(url: String, filters: Seq[String]) = {

    val filterChain: Seq[String => String] = filters.map(
      (filter) => filter.split(":") match {
        case Array(filterType: String, params: String) =>
          Filters.callFilter(FilterArg(filterType, params.split(",").map(x => x.toInt)))
        case _ => (x: String) => x
      }
    )

    println(Filters.applyFilters(mockUrl, filterChain))

  }

  val mockUrl = "http://capella.pics/test"

  val a = (
    repeated[String]("--filter", "-f")
    ).to[CapellaConfig]

  a.parse(args) match {
    case Success(params) => process(mockUrl, params.f)
    case Failure(e) => throw e
  }


}