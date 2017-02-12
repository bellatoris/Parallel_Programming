import reductions._

val a = ParallelParenthesesBalancing.balance("".toCharArray)
val array = Array.fill[Float](5){scala.util.Random.nextInt(20)}
array(0) = 0
val tree = LineOfSight.upsweep(array,1,5,1)
val b = Array.fill[Float](5)(0)
LineOfSight.downsweep(array, b, 0, tree)
b
LineOfSight.parLineOfSight(array, b, 2)
b

//Array(5.0, 10.0, 10.0, 1.0, 15.0)