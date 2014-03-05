package simple

import scala.util.Try
import org.openrdf.query.{TupleQueryResult, BindingSet}


import org.scalatest._
import org.scalatest.matchers.ShouldMatchers


import scala.collection.immutable.{Map, List}
import org.openrdf.model.{Statement, Value}
import collection.JavaConversions._
import org.openrdf.repository.RepositoryResult
import org.scalatest.WordSpecLike
import org.scalatest.Matchers
import org.scalatest.BeforeAndAfterAll


/**
@note some implicit conversions are used for query results, consider looking into simple package object
@note if you are unfamiliar with ScalaTest, look at http://www.scalatest.org/
@note if you are not good at scala, look at http://twitter.github.io/scala_school/
*/
class SimpleBigDataTestSpec  extends  WordSpec with Matchers with SimpleTestData {
  self=> //alias to this, to avoid confusion

  "Bigdata Example" should {

    "read something successfully" in {

      val db = BigData(true) //cleaning the files and initializing the database

      self.addTestData(db) //add test data ( see SimpleTestData )


      val tryLove:Try[RepositoryResult[Statement]]= db.read{con=>
        con.getStatements(null,self.loves,null,true)
      }
      tryLove.isSuccess shouldBe true

      val resLove = tryLove.get.toList
      resLove.size shouldEqual 6

      val query = "SELECT ?s ?o WHERE { ?s <http://denigma.org/relations/resources/loves>  ?o }"
      val queryLove= db.select(query)
      queryLove.isSuccess shouldBe  true
      queryLove.get.toList.size shouldEqual 6

      db.shutDown() // shutting down
    }

    "Provide errors for wrong queries" in {

      val db = BigData(true) //cleaning the files and initializing the database
      self.addTestData(db) //add test data ( see SimpleTestData )

      val wrongQuery =
      """
        | SELECT ?subject ?property ?object WHERE
        | {
        | ?subject ?property ?object .
        | FILTER( STR(?property) "lov*") .
        | }
        | LIMIT 50
        | """
        .stripMargin('|')

      //UNCOMMENT FOLLOWING LINES TO SEE TIMEOUTS
     val queryFreeze= db.select(wrongQuery)
     queryFreeze.isFailure shouldBe true
     db.shutDown() // shutting down

    }
  }
}
