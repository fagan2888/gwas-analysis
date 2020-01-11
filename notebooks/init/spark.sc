import $ivy.`sh.almond::almond-spark:0.6.0`
import $ivy.`org.apache.spark::spark-sql:2.4.4` // Necessary with older versions of almond that support 2.11
import org.apache.spark.sql._
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.DataFrame
Logger.getLogger("org").setLevel(Level.WARN)

val ss = {
  NotebookSparkSession
    .builder()
    // See https://github.com/almond-sh/almond/blob/620011b6edd152a84d3ac2637d45620a8b95af02/modules/scala/almond-spark/src/main/scala/org/apache/spark/sql/almondinternals/NotebookSparkSessionBuilder.scala
    .progress(enable=true, keep=false)
    .config("spark.sql.shuffle.partitions", "1")
    .config("spark.ui.enabled", "false")
    .config("spark.driver.host", "localhost")
    .master("local[*]")
    .getOrCreate()
}

implicit class DFOPs(df: DataFrame) {
    def fn[T](fn: DataFrame => T): T = fn(df)
}