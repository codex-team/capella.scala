# Capella SDK for Scala

[![Build Status](https://semaphoreci.com/api/v1/n0str/capella-scala/branches/master/badge.svg)](https://semaphoreci.com/n0str/capella-scala)

This package contains methods for images upload to the Capella

## Installation

### From binary

```
java -jar capella.jar
```

### From sources

```bash
git clone https://github.com/codex-team/capella.scala.git
cd capella.scala
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

* `success` is `true` when Capella successfully saved the image
* `url` - special allocated URL for uploaded image. If `success` is `false` this property takes value of nil
* `id` – an unique image identifier which is equal to the part of the `url` 
* `message` - in the case of error you will get a message. 

## Filters

You can apply filters to the image's URL. 
Currently we support the following filters:
* pixelize – render image using large colored blocks.
* crop – cover the target rectangle by the image.
* resize – scale the image.

More information about Capella filters by the link: [https://github.com/codex-team/capella#filters](https://github.com/codex-team/capella#filters)

Example of resize filter
```scala
import codex.capella.Pipeline._
import codex.capella.Filters

val url = "https://capella.pics/07d4fa39-7465-474a-9e01-15a71bb71c32"
val filteredUrl = url |> Filters.resize(700, 700)
println(filteredUrl)

// got: https://capella.pics/07d4fa39-7465-474a-9e01-15a71bb71c32/resize/700x700
```

You can also apply sequence of filters using pipeline operator or applyFilters method.

```scala
import codex.capella.Pipeline._
import codex.capella.Filters

val url = "https://capella.pics/07d4fa39-7465-474a-9e01-15a71bb71c32"
val filteredUrl = url |> Filters.resize(700, 700) |> Filters.crop(100, 100, (50, 50))

val filteredUrlAnother = Filters.applyFilters(url, Seq(Filters.resize(700, 700), Filters.crop(500)))

println(filteredUrlAnother)
// got: https://capella.pics/07d4fa39-7465-474a-9e01-15a71bb71c32/resize/700x700/crop/500
```

## Cli Usage

Currently support the following arguments
* -f/--file - upload local file
* -u/--url - upload file by URL
* -i - apply filter to the result URL 

Upload local file to Capella
```bash
java -jar capella.jar --file capella.png
// output: https://capella.pics/fa10d5bc-f1e0-4527-b69d-a1b8334e7ae8
```

Upload file to Capella by URL
```bash
java -jar capella.jar --url https://ifmo.su/public/app/img/products/hawk.png
// output: https://capella.pics/a05702c9-ba2a-47d0-8d6c-07596588a0e5
```

Upload and consistently apply several filters

```bash
java -jar capella.jar --url https://ifmo.su/public/app/img/products/hawk.png -i crop:250,250
// output: https://capella.pics/bd2f6c54-b6da-4b9d-ad6b-36b089e3c470/crop/250x250
```

```bash
java -jar capella.jar -i crop:250,250 -i resize:300 -i pixelize:100 --url https://ifmo.su/public/app/img/products/hawk.png
// output: https://capella.pics/9cba0795-d4c9-4b43-aac8-82d7c6e3ef72/crop/250x250/resize/300/pixelize/100
```

If you not specify --file or --url, filters will by applied to the standard input
```bash
echo "https://capella.pics/9cba0795-d4c9-4b43-aac8-82d7c6e3ef72" | java -jar capella.jar -i crop:250,250,100,100 -i pixelize:100
// output: https://capella.pics/9cba0795-d4c9-4b43-aac8-82d7c6e3ef72/crop/250x250&100,100/pixelize/100
```

```bash
java -jar capella.jar --url https://ifmo.su/public/app/img/products/hawk.png | java -jar capella.jar -i pixelize:100 | java -jar capella.jar -i resize:500
// output: https://capella.pics/8d4305cf-ffd0-4d3e-8eaa-327bcc179b00/pixelize/100/resize/500
```

## API Documentation

Full documentation of Capella can be found on GitHub –
https://github.com/codex-team/capella

## Issues and improvements

Ask a question or report a bug on the [create issue page](https://github.com/codex-team/capella.scala/issues/new).

Know how to improve **capella.scala**? [Fork it](https://github.com/codex-team/capella.scala) and send pull request.

You can also write questions and suggestions to the [CodeX Team’s
email](team@ifmo.su).

## License

[MIT](https://github.com/codex-team/codex.notes/blob/master/LICENSE)