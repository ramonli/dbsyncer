package com.hzins.dbsync;

public class JobDef {
	// include
	private long beginId;
	// exclude
	private long endId;
	
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

}
