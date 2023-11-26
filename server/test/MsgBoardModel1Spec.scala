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
        "get correct at least one default test messages" in {
            val msg = (getMessages("mlewis"))(0)
            (msg.from.contains("mlewis") && msg.content.contains("Hello") && msg.to == Some("web")) mustBe (true)
        }
        "create new users with no messages related to himself but with all public messages" in {
            createUser("u00", "121212") mustBe (true)
            val msgs = getMessages("u00")
            msgs.isEmpty mustBe (false)
            msgs.forall(_.to == None) mustBe (true)
        }
        "cannot create new users with existing usernames" in {
            createUser("mlewis", "prof") mustBe (false)
        }
        "add new message for default users" in {
            putMessage("mlewis", "Web, are you OK?", Some("web"))
            getMessages("mlewis").exists(_.content == "Web, are you OK?") mustBe (true)
            getMessages("web").exists(_.content == "Web, are you OK?") mustBe (true)
        }
    }
}