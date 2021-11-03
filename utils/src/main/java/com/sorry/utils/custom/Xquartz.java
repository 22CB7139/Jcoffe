package com.sorry.utils.custom;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.FileOutputStream;
import java.io.IOException;

public class Xquartz implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            new FileOutputStream("c:\\tmp\\pwned.txt").write("pwned".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
