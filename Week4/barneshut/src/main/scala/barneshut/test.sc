import barneshut._

val b = new Body(123f, 18f, 26f, 0f, 0f)
val nw = Empty(17.5f, 27.5f, 5f)
val ne = Empty(22.5f, 27.5f, 5f)
val sw = Empty(17.5f, 32.5f, 5f)
val se = Empty(22.5f, 32.5f, 5f)
val quad = Fork(nw, ne, sw, se)
quad.centerX
quad.centerY
quad