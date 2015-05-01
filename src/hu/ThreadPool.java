package hu;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {
	ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(5);
	long initialDelay = 0; // from initialDelay
	long period = 3600; // every other period the Thread execute it

	public ThreadPool() { // 该线程用于将返回的json数据解析并存入数据库，executeUpdate
		execute(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				List<List<Object>> gList = new Util().parseJson();
				DbHelper db = new DbHelper();

				db.executeUpdate(gList);
			}
		});
	}

	public void execute(Runnable command) {
		pool.scheduleAtFixedRate(command, initialDelay, period,
				TimeUnit.SECONDS);
	}
}