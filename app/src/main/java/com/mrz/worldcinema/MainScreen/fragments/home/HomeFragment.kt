package com.mrz.worldcinema.MainScreen.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import com.mrz.worldcinema.R
import com.mrz.worldcinema.api.ApiRequests
import com.mrz.worldcinema.constants.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.home_fragment.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private val myAdapter by lazy { MyAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.home_fragment, container, false)

        getCover()

        items_container.adapter = myAdapter
        items_container.layoutManager = LinearLayoutManager(, LinearLayoutManager.HORIZONTAL, false)

        return root
    }

    private var url: String = ""
    var idCover: String = ""
    private fun getCover() {
        buildNewRetrofit().create(ApiRequests::class.java).getCover().subscribeOn(
                Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    url = it.backgroundImage ?: ""
                    idCover = it.movieId ?: ""
                }.subscribeBy(
                        onNext = {
                            Glide.with(this)
                                    .load(Constants.IMG_URL +url)
                                    .into(ivHeader)
                            Log.d("testGif", "Succesful")
                        }, onError = {
                    Log.d("testGif", "onError url = $url")
                }
                )
    }

    fun buildNewRetrofit(): Retrofit {

        val client = OkHttpClient.Builder()
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        return retrofit

    }
}