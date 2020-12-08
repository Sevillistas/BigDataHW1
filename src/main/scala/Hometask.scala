import java.io.{BufferedWriter, File, FileWriter}
import com.google.gson.Gson
import scala.io.Source._

object Hometask {

  def main(args: Array[String]): Unit = {

    val filmId: String = "1541"
    val allFilmMarks = Array(0, 0, 0, 0, 0)
    val filmByIdMarks = Array(0, 0, 0, 0, 0)

    val filmsStats = fromFile("src/main/data/films.data").getLines.map(row => row.split('\t')).toList

    val allMarks = filmsStats.map(col => col(2)).groupBy(identity).mapValues(el => el.length).toSeq.sortBy(_._1)
    allMarks.foreach(el => allFilmMarks(el._1.toInt - 1) = el._2)

    val filmMarks = filmsStats.filter(el => el(1) == filmId)
      .map(col => col(2)).groupBy(identity).mapValues(el => el.length).toSeq.sortBy(_._1)
    filmMarks.foreach(el => filmByIdMarks(el._1.toInt - 1) = el._2)

    val result = FilmsMarks(filmByIdMarks, allFilmMarks)
    val json = serialize(result)
    writeToFile(json, "src/main/data/result.json")
  }

  def serialize(obj: FilmsMarks): String = {
    val gson = new Gson
    val jsonResult = gson.toJson(obj)
    jsonResult
  }

  def writeToFile(string: String, path: String): Unit = {
    val file = new File(path)
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(string)
    bw.close()
  }

}

case class FilmsMarks(hist_film: Array[Int], hist_all: Array[Int])