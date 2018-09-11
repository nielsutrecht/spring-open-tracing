package com.nibado.example.opentracing.shared;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Client {
    @GET("{count}")
    Call<Response> count(@Path("count") int count);
}
