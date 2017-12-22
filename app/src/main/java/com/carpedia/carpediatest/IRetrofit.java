package com.carpedia.carpediatest;

import com.carpedia.carpediatest.Model.ModelEmployee;
import com.carpedia.carpediatest.Model.ModelMain;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by rusci on 22-Dec-17.
 */

public interface IRetrofit {
    @GET("jobs")
    Call<List<ModelMain>> getJobs();

    @GET("employee")
    Call<List<ModelEmployee>> getEmployee();
}
