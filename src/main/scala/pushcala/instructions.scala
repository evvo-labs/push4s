package pushcala


// This object holds the map of all instruction names to the actual instruction.
object Instructions {

  private val map: Map[String, InstructionDef] = List(
    BooleanNot
  ).map(x => x.name -> x).toMap

  def apply(name: String): Option[InstructionDef] = map.get(name)

  def containsDefinitionFor(name: String): Boolean = map.contains(name)
}


// Instructions are functions that accept a push state and produce a new one
// Although they can be referenced by symbols
sealed trait InstructionDef extends (PushInterpreterState => PushInterpreterState) {
  def name: String
  override def apply(state: PushInterpreterState): PushInterpreterState
}

object BooleanNot extends InstructionDef {
  val name = "boolean_not"

  override def apply(state: PushInterpreterState): PushInterpreterState = {
    val newBooleanStack = state.boolean.contents match {
      case firstBool :: rest => !firstBool :: rest
      case _ => List()
    }

    state.copy(boolean = PushStack[Boolean](newBooleanStack))
  }
}