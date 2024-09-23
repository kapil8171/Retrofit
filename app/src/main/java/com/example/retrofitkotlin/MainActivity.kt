package com.example.retrofitkotlin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.retrofitkotlin.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNewMeme.setOnClickListener {
            getData()
        }
    }

    private fun getData() {
        // Show progress bar
        binding.progressBar.visibility = View.VISIBLE

        RetrofitInstance.apiInterface.getData().enqueue(object : Callback<responceDataClass?> {
            override fun onResponse(
                call: Call<responceDataClass?>,
                response: Response<responceDataClass?>
            ) {
                // Hide progress bar
                binding.progressBar.visibility = View.GONE

                binding.memeTitle.text = response.body()?.title
                binding.memeAuthor.text = response.body()?.author

                Glide.with(this@MainActivity)
                    .load(response.body()?.url)
                    .into(binding.memeImage)
            }

            override fun onFailure(call: Call<responceDataClass?>, t: Throwable) {
                // Hide progress bar
                binding.progressBar.visibility = View.GONE

                Toast.makeText(this@MainActivity, "${t.localizedMessage}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}
