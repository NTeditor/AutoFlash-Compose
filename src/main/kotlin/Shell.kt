import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class Shell() {
    fun cmd(command: List<String>): Flow<ShellResult> = flow {
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
        } catch (e: IOException) {
            print("IOException")
        } finally {
            emit(ShellResult.ExitCode(proc.waitFor()))
            reader.close()
            proc.destroy()
        }
    }

    fun checkADB(): Boolean {
        return try {
            val proc = ProcessBuilder("adb", "version").start()
            val reader = BufferedReader(InputStreamReader(proc.inputStream))
            reader.useLines { it.any { it.contains("Android Debug Bridge") } }
        } catch (e: IOException) {
            false
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

        fun cmd(command: List<String>): Flow<ShellResult> = flow {
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
            } catch (e: IOException) {
                print("IOException")
            } finally {
                emit(ShellResult.ExitCode(proc.waitFor()))
                reader.close()
                proc.destroy()
            }
        }
    }
}