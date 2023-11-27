import org.scalatestplus.play.HtmlUnitFactory
import org.scalatestplus.play.OneBrowserPerSuite
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneServerPerSuite

class MsgBoardWeb1_0Specs extends PlaySpec with GuiceOneServerPerSuite with OneBrowserPerSuite with HtmlUnitFactory {
    "MsgBoardWeb1_0" must {


        "login and access functions" in {
            
            go to s"http://localhost:$port/login"
            //eventually{
                find("h2").foreach(e => e.text mustBe("Login"))
                click on "username-login"
                textField("username-login").value = "mlewis"
                click on "password-login"
                pwdField("password-login").value = "prof"
                submit()
            //}
        }



    }

}
