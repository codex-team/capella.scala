# Capella SDK for Scala

This package contains methods for images upload to the Capella Server

## Installation

### Sbt

```scala
sbt build
sbt run
```

## Usage

```scala
uploadUrl("https://ifmo.su/public/app/img/products/capella.png") match {
  case Left(x) => println("Result: " + x)
  case Right(x) => println("Exception: " + x)
}
```

```scala
uploadFile("C:\\capella.jpg") match {
  case Left(x) => println("Result: " + x)
  case Right(x) => println("Exception: " + x)
}
```

Response implements capella response struct:
`success`, `message`, `id`, `url`

`success` is `true` when CodeX capella saved the image

`url` - special allocated URL for uploaded image. If `success` is `false` this propery 
takes value of nil

`message` - in case of error you will get a message. 

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