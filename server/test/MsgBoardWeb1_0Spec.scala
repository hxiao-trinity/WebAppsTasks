import org.scalatestplus.play.HtmlUnitFactory
import org.scalatestplus.play.OneBrowserPerSuite
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneServerPerSuite

class MsgBoardWeb1_0Specs extends PlaySpec with GuiceOneServerPerSuite with OneBrowserPerSuite with HtmlUnitFactory {
    "MsgBoardWeb1_0" must {

        "login and access functions" in {
            go to s"http://localhost:$port/login"
            pageTitle mustBe("Login")
            find(cssSelector("h2")).isEmpty mustBe false
            find(cssSelector("h2")).foreach(e => e.text mustBe("Message App"))
            click on "username-login"
            textField("username-login").value = "mlewis"
            click on "password-login"
            pwdField("password-login").value = "prof"
            submit()
            eventually{
                pageTitle mustBe("Message App")
                find(cssSelector("h2")).isEmpty mustBe(false)
                find(cssSelector("h2")).foreach(e => e.text mustBe("Message App"))
            }
        }

        "put message" in {

            // Sending a message
            textField("message-to").value = "web"
            textArea("message-content").value = "Web, how are you?"
            click on "put-message"

            eventually {
                pageTitle mustBe "Message App"
                findAll(cssSelector("li")).toList.map(_.text) mustBe "232323323232323232323"
            }
        }

        "log out" in {
            pageTitle mustBe "Message App"
            click on "logout1"
            eventually {
                pageTitle mustBe("Login")
            }
        }

        "create user and put 2 messages" in {
            go to s"http://localhost:$port/login"
            pageTitle mustBe "Login"
            find(cssSelector("h2")).isEmpty mustBe false
            find(cssSelector("h2")).foreach(e => e.text mustBe("Message App"))

            click on "username-create"
            textField("username-create").value = "randomWebAppStudent"
            click on "password-create"
            pwdField("password-create").value = "password123456"
            submit()
            eventually{
                pageTitle mustBe "Message App"
                find(cssSelector("h2")).isEmpty mustBe(false)
                find(cssSelector("h2")).foreach(e => e.text mustBe("Message App"))
            }

            textField("message-to").value = "mlewis" 
            textArea("message-content").value = "Dr Lewis, a quick question."
            click on "put-message"

            textField("message-to").value = "mlewis" 
            textArea("message-content").value = "Why I got this error?"
            click on "put-message"

            eventually {
                pageTitle mustBe "Message App"
                // Additional checks to verify the messages
            }
        }


        


    }

}
