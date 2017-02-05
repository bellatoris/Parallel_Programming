object testWhile {
  println(3)
  var i = 0
  while (i < 3) {
    var j = 0
    while (j < 3) {
      println("hi")
      j+= 1
    }
    i += 1
  }

  for (i <- 1 to 2) yield
    for (j <- 1 to 2)
      yield (i, j)




  for (i <- 1 to 2;
       j <- 1 to 2)
    yield (i, j)

}