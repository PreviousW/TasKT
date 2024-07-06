import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask

class AsyncTaskTimer(private val plugin: JavaPlugin) {
    private var delay: Long = 0
    private var period: Long = 0
    private var task: (() -> Unit)? = null

    private var bukkitTask: BukkitTask? = null

    fun delay(delay: Long = 0L) {
        this.delay = delay
    }

    fun period(period: Long) {
        this.period = period
    }

    fun timeToTick(time: Long): Long {
        return time * 20
    }

    fun run(action: () -> Unit) {
        this.task = action
    }

    fun execute() {
        requireNotNull(task) { "태스크 블럭이 정의되지 않았어요!" }
        bukkitTask = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, task!!, delay, period)
    }

    fun stop() {
        requireNotNull(task) { "태스크 블럭이 정의되지 않았어요!" }
        val taskId = bukkitTask?.taskId ?: return
        plugin.server.scheduler.cancelTask(taskId)
    }

    fun cancelTaskIf(condition: () -> Boolean) {
        val taskId = bukkitTask?.taskId ?: return
        if (condition()) {
            plugin.server.scheduler.cancelTask(taskId)
        }
    }


    fun runAsync(init: AsyncTaskTimer.() -> Unit): AsyncTaskTimer {
        val taskTimer = AsyncTaskTimer(this.plugin)
        taskTimer.init()
        return taskTimer
    }
}

fun Plugin.tasKT(init: AsyncTaskTimer.() -> Unit): AsyncTaskTimer {
    return AsyncTaskTimer(this).init()
}
