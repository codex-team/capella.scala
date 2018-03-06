package codex.capella

object Filters {

  def pixelize(power: Int): (String => String) = {

    (url: String) => s"$url/pixelize/" + power.toString

  }

  def crop(width: Int, height: Option[Int] = None, additional: Option[(Int, Int)] = None) = {

    (url: String) => s"$url/crop/" + width.toString + (height match {

      case Some(x) => s"x$x"

      case None => ""

    }) + (additional match {

      case Some(x) => s"&${x._1},${x._2}"

      case None => ""

    })

  }

  def resize(width: Int, height: Option[Int] = None) = {

    (url: String) => s"$url/resize/" + width.toString + (height match {

      case Some(x) => s"x$x"

      case None => ""

    })

  }

  /* Partial calls */

  def crop(width: Int, height: Int): String => String = {
    crop(width, Some(height))
  }

  def crop(width: Int, height: Int, additional: (Int, Int)): String => String = {
    crop(width, Some(height), Some(additional))
  }

  def resize(width: Int, height: Int): String => String = {
    resize(width, Some(height))
  }

}