package com.jedsada.example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.sample.HelloQuery
import com.jedsada.graphql.example.R
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class MainActivity : AppCompatActivity() {

    private val BASE_URL = "http://192.168.2.39:3000/graphql"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

        val apolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .build()

        val helloQuery = HelloQuery.builder().build()

        val githuntFeedCall = apolloClient.query(helloQuery)
        githuntFeedCall.enqueue(object : ApolloCall.Callback<HelloQuery.Data>() {
            override fun onResponse(response: Response<HelloQuery.Data>) {
                Log.d("POND", response.data().toString())
            }

            override fun onFailure(e: ApolloException) {
                Log.d("POND", e.message)
            }

        })
    }
}