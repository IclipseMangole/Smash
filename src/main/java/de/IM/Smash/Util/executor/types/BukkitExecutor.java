package de.IM.Smash.Util.executor.types;


import de.IM.Smash.Util.executor.Callback;
import de.IM.Smash.Util.executor.IExecutor;
import de.IM.Smash.Util.executor.ResultRunnable;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class BukkitExecutor extends IExecutor {
    private JavaPlugin plugin;


    public BukkitExecutor(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Callback execute(final Runnable runnable, final Callback callback) {
        Bukkit.getScheduler().runTask(plugin, () -> {
            runnable.run();
            callback.done();
        });
        return callback;
    }

    @Override
    public Callback executeAsync(final Runnable runnable, final Callback callback) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            runnable.run();
            callback.done();
        });
        return callback;
    }

    @Override
    public Callback repeat(Consumer<Integer> consumer, int times, long interval, TimeUnit timeUnit, Callback callback) {
        TimerTask timer = new TimerTask(interval, timeUnit);
        timer.task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if (times == timer.count) {
                timer.cancelTask();
                callback.done();
                return;
            }

            consumer.accept(timer.count);
            timer.countUp();
        }, 0, timer.interval);
        return callback;
    }

    @Override
    public Callback repeatAsync(Consumer<Integer> consumer, int times, long interval, TimeUnit timeUnit, Callback callback) {
        TimerTask timer = new TimerTask(interval, timeUnit);
        timer.task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            if (times == timer.count) {
                timer.cancelTask();
                callback.done();
                return;
            }

            consumer.accept(timer.count);
            timer.countUp();
        }, 0, timer.interval);
        return callback;
    }

    @Override
    public Callback executeLater(Runnable runnable, long delay, TimeUnit timeUnit, Callback callback) {
        if (timeUnit != null) {
            delay = timeUnit.toSeconds(delay) * 20;
        }
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            runnable.run();
            callback.done();
        }, delay);
        return callback;
    }

    @Override
    public Callback executeLaterAsync(Runnable runnable, long delay, TimeUnit timeUnit, Callback callback) {
        if (timeUnit != null) {
            delay = timeUnit.toSeconds(delay) * 20;
        }
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
            runnable.run();
            callback.done();
        }, delay);
        return callback;
    }

    @Override
    public Callback executeResult(final ResultRunnable runnable, final Callback callback) {
        Bukkit.getScheduler().runTask(plugin, () -> {
            runnable.run();
            if (runnable.hadSuccess())
                callback.done();
        });
        return callback;
    }

    @Override
    public Callback executeResultAsync(final ResultRunnable runnable, final Callback callback) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            runnable.run();
            if (runnable.hadSuccess())
                callback.done();
        });
        return callback;
    }

    @Override
    public boolean isAsyncThread() {
        return !Bukkit.isPrimaryThread();
    }

    private class TimerTask {
        public BukkitTask task;
        public int count;
        public long interval;

        public TimerTask(long interval, TimeUnit unit) {
            if (unit == null) {
                this.interval = interval;
                return;
            }
            this.interval = unit.toSeconds(interval) * 20;
        }

        public void countUp() {
            count += 1;
        }

        public void cancelTask() {
            task.cancel();
        }
    }
}
