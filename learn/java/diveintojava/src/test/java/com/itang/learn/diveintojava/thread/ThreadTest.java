package com.itang.learn.diveintojava.thread;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class ThreadTest extends Assert {
	/**
	 * 如何构造线程对象并启动线程.
	 */
	@Test
	public void test_new_thread() throws InterruptedException {
		trace();
		// method 1：匿名类覆写Thread run方法
		final Thread t1 = new Thread() {
			@Override
			public void run() {
				trace();
			}
		};
		t1.start();

		// method2: 子类覆写 run 方法
		MyThreadTask t2 = new MyThreadTask();
		t2.start();
		Thread.sleep(100);
		// 线程实例 == 通过Thread.currentThread()获取的线程对象
		assertEquals(t2, t2.currThread);

		// method3: 将Runnable对象作为参数传入Thread构造器
		Thread t3 = new Thread(new Runnable() {
			@Override
			public void run() {
				trace();
			}
		});
		t3.start();
	}

	@Test
	public void test_varible_visibility() {

	}

	@Test
	public void test_synchronization() throws InterruptedException {
		class OneThread extends Thread {
			private Object lock;

			public OneThread(Object lock) {
				this.lock = lock;
			}

			@Override
			public void run() {
				for (int i = 0; i < 100; i++)
					synchronized (lock) {
						try {
							lock.wait();
							System.out.print("-" + 1 + "  ");
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
			}
		}

		class TwoThread extends Thread {
			private Object lock;

			public TwoThread(Object lock) {
				this.lock = lock;
			}

			@Override
			public void run() {
				for (int i = 0; i < 100; i++) {
					synchronized (lock) {
						System.out.print(2);
						lock.notifyAll();
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		Object lock = new Object();
		Thread t1 = new OneThread(lock);
		t1.start();
		Thread.sleep(100);
		TwoThread t2 = new TwoThread(lock);
		t2.start();
		t1.join();
		t2.join();
		// output : 2-1 2-1 2-1 2-1 .....
	}

	class MyThreadTask extends Thread {
		public Thread currThread;

		@Override
		public void run() {
			trace();
			currThread = Thread.currentThread();
		}
	}

	private static void trace() {
		System.out.println(Thread.currentThread() + " run at "
				+ new Date().getTime());
	}
}
