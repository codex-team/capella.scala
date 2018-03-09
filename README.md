# Capella SDK for Scala

This package contains methods for images upload to the CodeX Capella

## Installation

### Sbt

```scala
sbt compile
sbt run
```

## Usage

```scala
import codex.capella.CapellaApi

CapellaApi.uploadUrl("https://ifmo.su/public/app/img/products/capella.png") match {
  case Left(x) => println("Result: " + x)
  case Right(x) => println("Exception: " + x)
}
```

```scala
CapellaApi.uploadFile("C:\\capella.jpg") match {
  case Left(x) => println("Result: " + x)
  case Right(x) => println("Exception: " + x)
}
```

Response object contains the following parameters:
`success`, `message`, `id`, `url`

* `success` is `true` when CodeX Capella successfully saved the image
* `url` - special allocated URL for uploaded image. If `success` is `false` this property takes value of nil
* `id` – an unique image identifier which is equal to the part of the `url` 
* `message` - in the case of error you will get a message. 

## Filters

You can apply filters to the image's URL. 
Currently we support the following filters:
* pixelize – render image using large colored blocks.
* crop – cover the target rectangle by the image.
* resize – scale the image.

More information about CodeX Capella filters by the link: [https://github.com/codex-team/capella#filters](https://github.com/codex-team/capella#filters)

Example of resize filter
```$scala
import codex.capella.Pipeline._
import codex.capella.Filters

val url = "https://capella.pics/07d4fa39-7465-474a-9e01-15a71bb71c32"
val filteredUrl = url |> Filters.resize(700, 700)
println(filteredUrl)

// got: https://capella.pics/07d4fa39-7465-474a-9e01-15a71bb71c32/resize/700x700
```

You can also apply sequence of filters using pipeline operator or applyFilters method.

```$scala
import codex.capella.Pipeline._
import codex.capella.Filters

val url = "https://capella.pics/07d4fa39-7465-474a-9e01-15a71bb71c32"
val filteredUrl = url |> Filters.resize(700, 700) |> Filters.crop(100, 100, (50, 50))

val filteredUrlAnother = Filters.applyFilters(url, Seq(Filters.resize(700, 700), Filters.crop(500)))

println(filteredUrlAnother)
// got: https://capella.pics/07d4fa39-7465-474a-9e01-15a71bb71c32/resize/700x700/crop/500
```

## API Documentation

Full documentation of CodeX Capella can be found on GitHub –
https://github.com/codex-team/capella

## Issues and improvements

Ask a question or report a bug on the [create issue page](https://github.com/codex-team/capella.scala/issues/new).

Know how to improve **capella.scala**? [Fork it](https://github.com/codex-team/capella.scala) and send pull request.

You can also write questions and suggestions to the [CodeX Team’s
email](team@ifmo.su).

## License

[MIT](https://github.com/codex-team/codex.notes/blob/master/LICENSE)