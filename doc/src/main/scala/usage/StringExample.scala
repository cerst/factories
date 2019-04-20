package usage

// #string_example
import com.github.cerst.factories.DefaultConstraints._
import com.github.cerst.factories._

import scala.util.matching.Regex

final case class IpV4Address(stringValue: String) extends AnyVal

object IpV4Address {

  val regex: Regex = """\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}""".r

  def apply(string: String): IpV4Address = {
    string.unsafeCreate(new IpV4Address(_), _ matches regex)
  }
}
// #string_example
