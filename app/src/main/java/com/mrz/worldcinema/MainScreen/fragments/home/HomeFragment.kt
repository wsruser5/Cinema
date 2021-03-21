package com.mrz.worldcinema.MainScreen.fragments.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import com.mrz.worldcinema.R
import com.mrz.worldcinema.api.ApiRequests
import com.mrz.worldcinema.constants.Constants
import com.mrz.worldcinema.data.MoviesListItem
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
    private val moviesAdapter by lazy { MoviesAdapter() }

    private var filter: String = "inTrend"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.home_fragment, container, false)
        getCover()


        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreference = context?.getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
        val token:String = sharedPreference?.getString("token","").toString()

        btnTrends.setOnClickListener {
            filter = "inTrend"
            getMovies("inTrend")
            underForYou.visibility = View.INVISIBLE
            underNew.visibility = View.INVISIBLE
            underTrend.visibility = View.VISIBLE
        }
        btnForYou.setOnClickListener {
            filter = "forMe"
            getMovies("forMe")
            underForYou.visibility = View.VISIBLE
            underNew.visibility = View.INVISIBLE
            underTrend.visibility = View.INVISIBLE
        }
        btnNew.setOnClickListener {
            filter = "new"
            getMovies("new")
            underForYou.visibility = View.INVISIBLE
            underNew.visibility = View.VISIBLE
            underTrend.visibility = View.INVISIBLE
        }

        if (filter == "inTrend") {
            underForYou.visibility = View.INVISIBLE
            underNew.visibility = View.INVISIBLE
            underTrend.visibility = View.VISIBLE
        }
        if (filter == "forMe") {
            underForYou.visibility = View.VISIBLE
            underNew.visibility = View.INVISIBLE
            underTrend.visibility = View.INVISIBLE
        }
        if (filter == "new") {
            underForYou.visibility = View.INVISIBLE
            underNew.visibility = View.VISIBLE
            underTrend.visibility = View.INVISIBLE
        }
        getMovies(filter)
        items_container.adapter = moviesAdapter
        items_container.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        getLastVideo(token)
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

    private lateinit var films: List<MoviesListItem>
    private fun getMovies(filter: String) {
        buildNewRetrofit().create(ApiRequests::class.java).getMovies(filter).subscribeOn(
                Schedulers.newThread()
        )
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    films = it
                }.subscribeBy(
                        onNext = {
                            val movies = films

                            movies.let{
                                moviesAdapter.setData(it)
                            }

                            Log.d("testGif", movies.toString())
                        }, onError = {
                    Toast.makeText(context, "Ошибка на сервере", Toast.LENGTH_SHORT).show()
                }
                )
    }

    private var urlLast: String = ""
    private var nameLast: String = ""
    private var idLast: String = ""
    private fun getLastVideo(token:String) {
        buildNewRetrofit().create(ApiRequests::class.java).getLastVideo("lastView", token).subscribeOn(
                Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    urlLast = it[0].poster
                    nameLast = it[0].name
                    idLast = it[0].movieId
                }.subscribeBy(
                        onNext = {
                            Glide.with(this)
                                    .load(Constants.IMG_URL + urlLast)
                                    .into(ivMainLastVideo)
                            tvMainLastVideoName.text = nameLast
                            Log.d("testGif", "LastVideo")
                        }, onError = {
                    Log.e("testGif", "LastVideoError")
                    Toast.makeText(context, "Ошибка на сервере", Toast.LENGTH_SHORT).show()
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