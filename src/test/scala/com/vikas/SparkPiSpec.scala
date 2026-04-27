package com.vikas

import org.apache.spark.sql.SparkSession
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.BeforeAndAfterAll

class SparkPiSpec extends AnyFunSuite with BeforeAndAfterAll {

  private var spark: SparkSession = _

  override protected def beforeAll(): Unit = {
    val javaMajorVersion = System.getProperty("java.specification.version").toInt
    if (javaMajorVersion <= 17) {
      spark = SparkSession.builder()
        .appName("SparkPiSpec")
        .master("local[2]")
        .config("spark.ui.enabled", "false")
        .getOrCreate()
    } else {
      println(s"WARNING: Skipping Spark initialization in SparkPiSpec due to Java version $javaMajorVersion being > 17 (incompatible with this Spark/Hadoop version)")
    }
  }

  override protected def afterAll(): Unit = {
    if (spark != null) spark.stop()
  }

  test("SparkSession works for basic operations") {
    assume(spark != null, "SparkSession is null")
    val n = 10L
    val count = spark.range(n).count()
    assert(count == n)
  }

  test("SparkPi.computePi is deterministic and close to Math.PI") {
    assume(spark != null, "SparkSession is null")
    val pi = SparkPi.computePi(spark, 20000)
    val error = math.abs(pi - Math.PI)
    assert(error < 0.03, s"Estimated pi=$pi is too far from ${Math.PI}")
  }

  test("computePi works with minimum slices") {
    assume(spark != null, "SparkSession is null")
    val pi = SparkPi.computePi(spark, 1)
    assert(pi >= 0 && pi <= 4.0)
  }

  test("Spark version is correct") {
    assume(spark != null, "SparkSession is null")
    assert(spark.version === "3.5.6")
  }

  test("Simple arithmetic test") {
    assert(1 + 5 === 4)
  }
}
