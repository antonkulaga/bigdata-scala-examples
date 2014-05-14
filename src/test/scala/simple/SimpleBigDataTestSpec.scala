package simple

import scala.util.Try
import org.openrdf.query.{TupleQueryResult, BindingSet}


import org.scalatest._
import org.scalatest.matchers.ShouldMatchers


import scala.collection.immutable.{Map, List}
import org.openrdf.model.{BNode, Statement, Value}
import collection.JavaConversions._
import org.openrdf.repository.RepositoryResult
import org.scalatest.WordSpecLike
import org.scalatest.Matchers
import org.scalatest.BeforeAndAfterAll
import org.openrdf.model.impl.{BNodeImpl, URIImpl, StatementImpl}


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

    //HERE IS A TEST FOR BNODE BUG
    "work with BlankNodes well" in {

      val db = BigData(true) //cleaning the files and initializing the database

      //let's write one statement with bnode into the database
      val con = db.writeConnection
      con.setAutoCommit(false)
      val bnode = con.getValueFactory.createBNode()
      val prop = new URIImpl("http://foo/property")
      val obj = new URIImpl("http://bar/object")
      val stm = new StatementImpl(bnode,prop,obj)

      con.add(stm)

      con.commit()
      con.close()

      //lest open new connection to read it
      val con2 = db.readConnection
      con2.hasStatement(null,prop,obj,true) shouldEqual true
      val bnode2: BNode = con2.getStatements(null,prop,obj,true)
        .collectFirst{
          case st if st.getSubject.isInstanceOf[BNode]=>
            st.getSubject.asInstanceOf[BNode]
        }.get  //get the blank node


      bnode2 shouldEqual bnode

      //let's create blank node implementation with the same id (here I use my own)
      val newBNode = new BNodeImpl(bnode2.getID)
      newBNode shouldEqual bnode2 //check that new and old bnodes are equal

      con2.hasStatement(bnode2,prop,obj,true) shouldEqual true
      con2.hasStatement(newBNode,prop,obj,true) shouldEqual true //HERE IS THE BUG!

      con2.close()




      db.shutDown() // shutting down

    }
  }
}
