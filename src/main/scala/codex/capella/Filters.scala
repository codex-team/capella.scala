package codex.capella

/**
  * Pipeline helper class for the support of the following syntax:
  * url |> filterOne |> filterTwo
  */
object Pipeline {

  implicit class RichPipes[Y](y: Y) {
    def |>[Z](f: Y => Z) = f(y)

    def &>[X, Z](f: (X, Y) => Z): (X => Z) = (x: X) => f(x, y)
  }

}

/**
  * Filtering helpers for image result URL
  */
object Filters {

  /**
    * Pixelize filter.
    * Render image using large colored blocks.
    *
    * @param pixels - Number of pixels on the largest side
    * @return Filtered image URL
    */
  def pixelize(pixels: Int): String => String = {
    (url: String) => s"$url/pixelize/" + pixels.toString
  }

  /**
    * Crop filter.
    * Cover the target rectangle by the image.
    *
    * @param width      - Target rectangles width or target squares size if no height was given
    * @param height     - (optional) Target rectangle height
    * @param additional - (optional) Crop an area from specified point
    * @return Filtered image URL
    */
  def crop(width: Int, height: Option[Int] = None, additional: Option[(Int, Int)] = None): String => String = {
    (url: String) =>
      s"$url/crop/" + width.toString + (height match {
        case Some(x) => s"x$x"
        case None => ""
      }) + (additional match {
        case Some(x) => s"&${x._1},${x._2}"
        case None => ""
      })
  }

  /**
    * Resize filter.
    * Scale the image to the largest size such that both its width and its height can fit inside the target rectangle.
    *
    * @param width  - Maximum images width or maximum target squares size if no height was given
    * @param height - (optional) Maximum image's height
    * @return
    */
  def resize(width: Int, height: Option[Int] = None): String => String = {
    (url: String) =>
      s"$url/resize/" + width.toString + (height match {
        case Some(x) => s"x$x"
        case None => ""
      })
  }

  /**
    * Apply sequence of filters to the image URL and get result URL of the filtered image.
    *
    * @param url     - source URL from capella.pics
    * @param filters - sequence of filters
    * @return - Result URL of the filtered image
    */
  def applyFilters(url: String, filters: Seq[String => String]): String = {
    filters.foldLeft(url) { (accumulator, f) => f(accumulator) }
  }

  /* Partial calls */

  def crop(width: Int, height: Int): String => String = {
    crop(width, Some(height))
  }

  def crop(width: Int, height: Int, additional: (Int, Int)): String => String = {
    crop(width, Some(height), Some(additional))
  }

  def crop(width: Int, height: Int, paddingLeft: Int, paddingTop: Int): String => String = {
    crop(width, Some(height), Some((paddingLeft, paddingTop)))
  }

  def resize(width: Int, height: Int): String => String = {
    resize(width, Some(height))
  }

}