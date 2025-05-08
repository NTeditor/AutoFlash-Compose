import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.IOException

class Shell(private val command: List<String>) {
    fun start(): Flow<ShellResult> = flow {
        val proc = ProcessBuilder(command).redirectErrorStream(true).start()
        val reader = proc.inputStream.bufferedReader()

        processList.add(proc)
        try {
            while (true) {
                val line = withContext(Dispatchers.IO) {
                    reader.readLine()
                }
                if (line == null) break
                emit(ShellResult.Output(line))
            }

            emit(ShellResult.IsSuccess(true))
        } catch (e: IOException) {
            e.printStackTrace()
            emit(ShellResult.IsSuccess(false))
        } finally {
            emit(ShellResult.ExitCode(proc.waitFor()))
            reader.close()
            proc.destroy()
        }
    }

    companion object {
        private val processList = ArrayList<Process?>()

        fun stop() {
            for (process in processList) {
                if (process != null && process.isAlive) {
                    process.destroy()
                }
            }
            processList.clear()
        }
    }
}