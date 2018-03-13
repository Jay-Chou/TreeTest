package Thread;

import org.apache.log4j.Logger;

public class MyThread implements Runnable {

	private static Logger logger = Logger.getLogger(MyThread.class);
	private int tikit = 2000;

	private boolean flag = false;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			this.excute();
		} catch (Exception e) {
			logger.error("线程错误");
		}
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public synchronized void excute() {
		while (this.tikit > 0) {
			synchronized (this) {
				if (this.tikit > 0) {
					System.out.println(Thread.currentThread().getName() + " 牛批:" + this.tikit--);
				}
			}
		}
	}

	public void setFlag(boolean _flag) {
		this.flag = _flag;
	}

	public boolean getFlag() {
		return this.flag;
	}

}
