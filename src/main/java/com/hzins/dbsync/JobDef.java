package com.hzins.dbsync;

import java.util.concurrent.atomic.AtomicLong;

public class JobDef {
	// include
	private long beginId;
	// exclude
	private long endId;
	private AtomicLong ID = new AtomicLong(0);

	public JobDef(long beginId, long endId) {
		super();
		this.beginId = beginId;
		this.endId = endId;
	}

	public long getBeginId() {
		return beginId;
	}

	public void setBeginId(long beginId) {
		this.beginId = beginId;
	}

	public long getEndId() {
		return endId;
	}

	public void setEndId(long endId) {
		this.endId = endId;
	}

	public AtomicLong getID() {
		return ID;
	}

	public void setID(AtomicLong iD) {
		ID = iD;
	}

}
