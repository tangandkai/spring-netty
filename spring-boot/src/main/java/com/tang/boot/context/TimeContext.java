package com.tang.boot.context;

public class TimeContext {

    private ThreadLocal<String> timeContext = ThreadLocal.withInitial(()->"");

    public void setTimeContext(String time){
        timeContext.set(time);
    }

    public String getTimeContext(){
        return timeContext.get();
    }

    public void release(){
        timeContext.remove();
    }
}
