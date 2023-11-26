package test

import models.MsgBoardModel1._
import org.scalatest.Succeeded
import org.scalatestplus.play.PlaySpec
import org.specs2.control.eff.create

import java.time.LocalDateTime

class MsgBoardModel1Spec extends PlaySpec{
    "MsgBoardModel1" must {
        "correctly validate login" in {
            validateUser("mlewis", "prof") mustBe (true)
            validateUser("mlewis", "pppppppppppppppp") mustBe (false)
            validateUser("uuuuuuuuuuuuuuu", "prof") mustBe (false)
        }
        "cannot create new users with existing usernames" in {
            createUser("mlewis", "password") mustBe (false)
        }
        "get correct default test messages" in {
            val msgs = (getMessages("mlewis"))
            msgs.size >= 2 mustBe (true)
            msgs(0).from.contains("mlewis") mustBe (true) 
            msgs(0).content.contains("Hello, Web") mustBe (true) 
            msgs(0).to == Some("web") mustBe (true)
            msgs(1).from.contains("web") mustBe (true) 
            msgs(1).content.contains("people") mustBe (true) 
            msgs(1).to == None mustBe (true)
        }
        "create new users with no messages related to himself but with all public messages" in {
            createUser("u00", "121212") mustBe (true)
            val msgs = getMessages("u00")
            msgs.isEmpty mustBe (false)
            msgs.forall(_.to == None) mustBe (true)
        }
        "add new message for default users" in {
            putMessage("mlewis", "Web, are you OK?", Some("web"))
            getMessages("mlewis").exists(_.content == "Web, are you OK?") mustBe (true)
            getMessages("web").exists(_.content == "Web, are you OK?") mustBe (true)
        }
        "add new message for new users" in {
            createUser("u01", "121212") mustBe (true)
            putMessage("u01", "Dr Lewis, I'm u01", Some("mlewis"))
            getMessages("mlewis").exists(_.content == "Dr Lewis, I'm u01") mustBe (true)
            getMessages("u01").exists(_.content == "Dr Lewis, I'm u01") mustBe (true)
        }

    }
}