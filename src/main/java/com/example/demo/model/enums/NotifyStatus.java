package com.example.demo.model.enums;

public enum NotifyStatus {

    NotNotify(0,false),
    OneHourBefore(60*60*1000,true),
    OneDayBefore(24*60*60*1000,true);


    private long notifyBeforeTime;
    private boolean notify;



    NotifyStatus(long notifyBeforeTime, boolean notify) {
        this.notifyBeforeTime = notifyBeforeTime;
        this.notify = notify;
    }



    public long getNotifyBeforeTime() {
        return notifyBeforeTime;
    }

    public void setNotifyBeforeTime(long notifyBeforeTime) {
        this.notifyBeforeTime = notifyBeforeTime;
    }


    public boolean isNotify() {
        return notify;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }

    @Override
    public String toString() {
        return "NotifyStatus{" +
                "notifyBeforeTime=" + notifyBeforeTime +
                ", notify=" + notify +
                '}';
    }



}
