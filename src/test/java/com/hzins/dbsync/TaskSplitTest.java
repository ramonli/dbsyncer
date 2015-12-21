package com.hzins.dbsync;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class TaskSplitTest {

	@Test
	public void test() throws Exception {
		BlockingQueue<JobDef> taskDefQueue = new LinkedBlockingQueue<JobDef>();
		DbSyncer syncer = new DbSyncer(1);

		syncer.splitTask(taskDefQueue, 8, 1, 11);

		while (true) {
			JobDef def = taskDefQueue.poll(1, TimeUnit.SECONDS);
			if (def != null) {
				System.out.println(def.getBeginId() + " - " + def.getEndId());
			}
			else {
				break;
			}
		}
	}

}
