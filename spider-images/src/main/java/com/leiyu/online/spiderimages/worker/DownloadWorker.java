package com.leiyu.online.spiderimages.worker;

import java.util.List;

public abstract class DownloadWorker<T> {
    
    public void execute(){
        List<T> lists = this.selectTasks();
        this.executeTasks(lists);
    }

    protected abstract void executeTasks(List<T> lists);

    protected abstract List<T> selectTasks();
}
