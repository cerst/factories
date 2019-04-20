package usage

// #numeric_example
import com.github.cerst.factories.DefaultConstraints._
import com.github.cerst.factories._

final case class PersonId(intValue: Int) extends AnyVal

object PersonId {

  def apply(intValue: Int): PersonId = {
    intValue.unsafeCreate(new PersonId(_), _ >= 0)
  }

}
// #numeric_example
