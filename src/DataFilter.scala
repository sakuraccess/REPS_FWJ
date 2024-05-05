import scala.io.Source
import scala.util.Try

object DataFilter {
  def extractDateTimeAndPower(data: List[List[String]]): List[(String, Double)] = {
    data.flatMap {
      case startTime :: _ :: powerGeneration :: Nil =>
        Try((startTime, if (powerGeneration == "0" || powerGeneration.isEmpty) 0.0 else powerGeneration.toDouble)).toOption
      case _ => None  // Safely ignore rows that do not match the expected format
    }
  }

  def datahour(data: List[List[String]]): List[(String, Double)] = {
    val dateTimePower = extractDateTimeAndPower(data)
    dateTimePower.grouped(4).toList.map { hourlyData =>
      val averagePower = if (hourlyData.nonEmpty) hourlyData.map(_._2).sum / hourlyData.size else 0
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
