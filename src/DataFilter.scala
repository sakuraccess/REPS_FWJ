import scala.io.Source
import scala.util.Try

object DataFilter {
  def extractDateTimeAndPower(data: List[List[String]]): List[(String, Double)] = {
    data.flatMap {
      case List(startTime, _, powerGeneration) =>
        val cleanPowerGeneration = powerGeneration.replace("\"", "") // Remove double quotes
        Try(cleanPowerGeneration.toDouble).toOption match {
          case Some(pg) =>
            Some((startTime, pg))
          case None =>
            println(s"Failed to parse cleaned power generation value from: '$cleanPowerGeneration'")
            None
        }
      case _ =>
        println("Warning: Data format does not match expected pattern")
        None
    }
  }
  def datahour(data: List[List[String]]): List[(String, Double)] = {
    val dateTimePower = extractDateTimeAndPower(data)
    val groupedData = dateTimePower.grouped(4).toList.filter(_.nonEmpty)
    groupedData.map { hourlyData =>
      val averagePower = hourlyData.map(_._2).sum / hourlyData.size
      (hourlyData.head._1, averagePower)
    }
  }


  def dataday(data: List[List[String]]): List[(String, Double)] = {
    val dateTimePower = extractDateTimeAndPower(data)
    dateTimePower.grouped(96).toList.map { dailyData =>
      val averagePower = if (dailyData.nonEmpty) dailyData.map(_._2).sum / dailyData.size else 0
      (dailyData.head._1, averagePower)
    }
  }

  def dataweek(data: List[List[String]]): List[(String, Double)] = {
    val dateTimePower = extractDateTimeAndPower(data)
    dateTimePower.grouped(672).toList.map { weeklyData =>
      val averagePower = if (weeklyData.nonEmpty) weeklyData.map(_._2).sum / weeklyData.size else 0
      (weeklyData.head._1, averagePower)
    }
  }

  def datamonth(data: List[List[String]]): List[(String, Double)] = {
    val dateTimePower = extractDateTimeAndPower(data)
    dateTimePower.grouped(2880).toList.map { monthlyData =>
      val averagePower = if (monthlyData.nonEmpty) monthlyData.map(_._2).sum / monthlyData.size else 0
      (monthlyData.head._1, averagePower)
    }
  }
}
