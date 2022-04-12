package com.example.retrofitapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val imageList = mutableListOf<item_data>()
    private var currentPage: Int = 1
    private var loading: Boolean = true
    private  val adapter = My_Adapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        setContentView(binding.root)
        getImages()
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.recView)
        var layoutManager = LinearLayoutManager(this)
        binding.recView.layoutManager = layoutManager
        binding.recView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val indexRv = (binding.recView.layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition()
                if(indexRv!=0){
                    binding.scrollToTopBtn.visibility = View.VISIBLE
                } else {
                    binding.scrollToTopBtn.visibility = View.GONE
                }

                if(!loading){
                    if(layoutManager.findLastCompletelyVisibleItemPosition() == imageList.size-1) {
                        currentPage += 1
                        getImages()
                        loading = true;
                    }
                }
            }
        })

        binding.scrollToTopBtn.setOnClickListener{
            binding.recView.smoothScrollToPosition(0)
            binding.recView.smoothScrollBy(5,0)
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            currentPage = 1
            getImages()
            binding.swipeRefreshLayout.isRefreshing = false
        }



    }
    override fun onStart() {
        super.onStart()
        binding.swipeRefreshLayout.isRefreshing = true;
        getImages()
    }
    private fun getImages() {
        binding.errorTextView.visibility = View.GONE
        binding.errorBtn.visibility = View.GONE

        var service: retrofitApi = retrofitApi.create()

        lifecycleScope.launch {
            try {
                val response = service.getImages(Constants.clientId, currentPage)
                if (response.isSuccessful) {
                    if (currentPage == 1) {
                        imageList.clear()
                    }
                    response.body()?.let {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            imageList.addAll(responseBody)
                        }

                        adapter.submitList(imageList.toMutableList())

                        binding.swipeRefreshLayout.isRefreshing = false;
                        loading = false
                    }
                } else {
                    Log.d("TAG", response.message())
                    Toast.makeText(this@MainActivity, "error loading data...", Toast.LENGTH_LONG)
                        .show()
                }
            } catch (e: UnknownHostException) {
                if (imageList.isEmpty()) {

                    binding.errorTextView.visibility = View.VISIBLE
                    binding.errorBtn.visibility = View.VISIBLE
                    binding.swipeRefreshLayout.isRefreshing = false;
                } else {
                    Log.e("TAG", e.stackTraceToString())
                    Toast.makeText(this@MainActivity, "No Internet Connection", Toast.LENGTH_LONG)
                        .show()
                }
            } catch (e: Exception) {
                Log.e("TAG", e.stackTraceToString())
                Toast.makeText(this@MainActivity, "An Exception Occurred", Toast.LENGTH_LONG).show()
            }
        }
    }
}