package com.carpedia.carpediatest.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rusci on 22-Dec-17.
 */

public class ModelMain {
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("job_title")
    @Expose
    private String jobTitle;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}
