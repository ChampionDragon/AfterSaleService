package com.bs.afterservice.zxing;

import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;


public final class ViewfinderResultPointCallbackMy implements ResultPointCallback {

	  private final MyViewfinderView viewfinderView;

	  public ViewfinderResultPointCallbackMy(MyViewfinderView viewfinderView) {
	    this.viewfinderView = viewfinderView;
	  }

	  public void foundPossibleResultPoint(ResultPoint point) {
	    viewfinderView.addPossibleResultPoint(point);
	  }

	}