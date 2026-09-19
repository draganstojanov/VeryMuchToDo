package work.stojanov.todo.ui.settings.autocomplete

data class AutocompleteEditItem(
    val text: String,
    var checkForDeletion: Boolean = false,
    var editState: Boolean = false
)
