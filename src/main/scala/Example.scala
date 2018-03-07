package codex

import codex.capella.Filters
import codex.capella.CapellaApi
import codex.capella.Pipeline._


object Example extends App {

// Upload local image file to capella.pics

  CapellaApi.uploadFile("./sobaki-6df25d04eb75.jpg") match {
    case Left(x) => println("Result: " + x)
    case Right(x) => println("Exception: " + x)
  }

// Upload image to capella.pics by URL

  CapellaApi.uploadUrl("https://ifmo.su/public/app/img/products/capella.png") match {
    case Left(x) => println("Result: " + x)
    case Right(x) => println("Exception: " + x)
  }

// Apply Sequence of filters to the result URL

  val url = "https://capella.pics/07d4fa39-7465-474a-9e01-15a71bb71c32"

  val transformations = Seq(
    Filters.resize(700, 700),
    Filters.crop(500),
    Filters.resize(250),
    Filters.crop(200, 200),
    Filters.crop(100, 100, (50, 50)),
    Filters.pixelize(10)
  )

  println("Result: " + Filters.applyFilters(url, transformations))

// Apply filters to the result URL with pipeline operator

  val newUrl = url |>
    Filters.resize(700, 700) |>
    Filters.crop(500) |>
    Filters.resize(250) |>
    Filters.crop(200, 200) |>
    Filters.crop(100, 100, (50, 50)) |>
    Filters.pixelize(10)

  println("Result: " + newUrl)

}