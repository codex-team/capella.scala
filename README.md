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

## Docs

CodeX Capella [documentation](https://github.com/codex-team/capella#readme)

## Contribution

Feel free to aks a question, report a bug or fork and improve a package
