package com.example.noamm_000.talkwithcompviawifi.CommonServerAndClient;

import java.io.Serializable;

/**
 * Created by noamm_000 on 10/03/2017.
 */
public class DataObject implements Serializable {

    
	private static final long serialVersionUID = 123456789;
	private float xDelta;
    private float yDelta;
    private boolean isClickedUp;
    private boolean isClickedDown;

    public DataObject(float dx, float dy, boolean isClickedUp, boolean isClickedDown) {
        this.xDelta = dx;
        this.yDelta = dy;
        this.isClickedUp = isClickedUp;
        this.isClickedDown = isClickedDown;
    }

    public boolean isClickedUp() {
        return isClickedUp;
    }

    public void setClickedUp(boolean clickedUp) {
        isClickedUp = clickedUp;
    }

    public boolean isClickedDown() {
        return isClickedDown;
    }

    public void setClickedDown(boolean clickedDown) {
        isClickedDown = clickedDown;
    }

    public float getxDelta() {
        return xDelta;
    }

    public void setxDelta(float xDelta) {
        this.xDelta = xDelta;
    }

    public float getyDelta() {
        return yDelta;
    }

    public void setyDelta(float yDelta) {
        this.yDelta = yDelta;
    }
}
