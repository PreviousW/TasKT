package io.github.FlagFan34272.taskt

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask

class AsyncRunner(private val plugin: JavaPlugin) {
    private var delay: Long = 0
    private var period: Long = 0
    private var task: (() -> Unit)? = null

    private var bukkitTask: BukkitTask? = null

    private val tasks: MutableSet<Pair<BukkitTask, Int>> = mutableSetOf()

    f

    fun run(action: () -> Unit) {
        this.task = action
    }

    fun exec() {
        requireNotNull(task) { "태스크 블럭이 정의되지 않았어요!" }
        bukkitTask = Bukkit.getScheduler().runTaskAsynchronously(plugin, task!!)
        tasks.add(Pair(bukkitTask!!, bukkitTask!!.taskId))
    }

    fun stop() {
        requireNotNull(task) { "태스크 블럭이 정의되지 않았어요!" }
        if (tasks.firstOrNull { it.first == task } != null) {
            val tk = tasks.firstOrNull { it.first == task }
            Bukkit.getScheduler().cancelTask(tk!!.second)
            tasks.remove(tk)
        }
    }

    fun cancelTaskIf(condition: () -> Boolean) {
        val taskId = bukkitTask?.taskId ?: return
        if (condition()) {
            plugin.server.scheduler.cancelTask(taskId)
        }
    }


    fun runAsync(init: AsyncRunner.() -> Unit): AsyncRunner {
        val taskTimer = AsyncRunner(this.plugin)
        taskTimer.init()
        return taskTimer
    }
}
