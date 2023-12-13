import org.scalatestplus.play.HtmlUnitFactory
import org.scalatestplus.play.OneBrowserPerSuite
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneServerPerSuite

class MsgBoardWeb1_0Specs extends PlaySpec with GuiceOneServerPerSuite with OneBrowserPerSuite with HtmlUnitFactory {
    "MsgBoardWeb1_0" must {

        "login and access initial messages" in {
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
                findAll(cssSelector("li")).toList.exists(_.text.contains("Hello, people")) mustBe true
                findAll(cssSelector("li")).toList.exists(_.text.contains("Hello, Web")) mustBe true
            }
        }

        "put message" in {
            pageTitle mustBe "Message App" 
            textField("message-to").value = "web"
            textArea("message-content").value = "Web, how are you?"
            click on "put-message"

            eventually {
                pageTitle mustBe "Message App"
                findAll(cssSelector("li")).toList.exists(_.text.contains("Web, how are you?")) mustBe true
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
            textField("username-create").value = "2320stu"
            click on "password-create"
            pwdField("password-create").value = "dataabs"
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
            textArea("message-content").value = "How can I implement an AVL tree?"
            click on "put-message"

            eventually {
                pageTitle mustBe "Message App"
                pageTitle mustBe "Message App"
                findAll(cssSelector("li")).toList.exists(_.text.contains("2320stu TO mlewis: Dr Lewis, a quick question.")) mustBe true
                findAll(cssSelector("li")).toList.exists(_.text.contains("2320stu TO mlewis: How can I implement an AVL tree?")) mustBe true
                click on "logout1"
                eventually {
                    click on "username-login"
                    textField("username-login").value = "mlewis"
                    click on "password-login"
                    pwdField("password-login").value = "prof"
                    submit()
                    eventually{
                        pageTitle mustBe "Message App"
                        find(cssSelector("h2")).isEmpty mustBe(false)
                        find(cssSelector("h2")).foreach(e => e.text mustBe("Message App"))
                        findAll(cssSelector("li")).toList.exists(_.text.contains("2320stu TO mlewis: Dr Lewis, a quick question.")) mustBe true
                        findAll(cssSelector("li")).toList.exists(_.text.contains("2320stu TO mlewis: How can I implement an AVL tree?")) mustBe true
                    }
                }

            }
        }


        


    }

}
