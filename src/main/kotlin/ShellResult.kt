sealed class ShellResult {
    data class Output(val output: String): ShellResult()
    data class ExitCode(val exitCode: Int): ShellResult()
    data class IsSuccess(val isSuccess: Boolean): ShellResult()
}