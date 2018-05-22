package com.harsha.mastglobal.rest;

import com.harsha.mastglobal.model.UserRepoResponse;
import com.harsha.mastglobal.model.UserResponse;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by harsha on 5/21/18.
 */

public interface ApiInterface {

    @GET("{username}")
    Call<UserResponse> getUser(@Path("username") String username);

    @GET("{username}/repos")
    Call<ArrayList<UserRepoResponse>> getUserRepo(@Path("username") String username);
}
