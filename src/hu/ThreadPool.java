package hu;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {
	ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(5);
	long initialDelay = 0; // from initialDelay
	long period = 1; // every other period the Thread execute it

	public void execute(Runnable command) {
		pool.scheduleAtFixedRate(command, initialDelay, period,
				TimeUnit.MINUTES);
	}
}