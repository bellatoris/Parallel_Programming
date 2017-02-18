import scala.collection.GenSeq
import kmeans.KMeans
import kmeans.Point

// GenSeq((0, 0, 1), (0,0, -1), (0,1,0), (0,10,0)) and
// 'oldMeans' == GenSeq((0, -1, 0), (0, 2, 0)) and
// 'eta' == 12.25
val kMeans = new KMeans()
val points = GenSeq(new Point(0, 0, 1), new Point(0,0, -1), new Point(0,1,0), new Point(0,10,0))
val means = GenSeq(new Point(0, -1, 0), new Point(0, 2, 0))
val eta = 12.25
val a = kMeans.kMeans(points, means,eta)