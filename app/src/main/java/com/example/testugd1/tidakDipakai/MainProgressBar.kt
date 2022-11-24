package com.example.testugd1.tidakDipakai

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.testugd1.R
import com.squareup.picasso.Picasso

class MainProgressBar : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progressbar)

        val url_1: String = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.kibrispdr.org%2Fripper-dragon-nest.html&psig=AOvVaw0fzU535l39SSQIAPh1sTHv&ust=1669375212689000&source=images&cd=vfe&ved=0CBAQjRxqFwoTCJib7bTZxvsCFQAAAAAdAAAAABAJ"
        val url_2: String = "https://www.google.com/url?sa=i&url=http%3A%2F%2Fdnthai.blogspot.com%2F2012%2F06%2Fpriest.html&psig=AOvVaw2DXRWiUKmsxK1J0XoQl8tQ&ust=1669375282531000&source=images&cd=vfe&ved=0CBAQjRxqFwoTCNimmNbZxvsCFQAAAAAdAAAAABAP"

        val imgGlide = findViewById<ImageView>(R.id.ImgViewGlide)
        val imgPicasso = findViewById<ImageView>(R.id.ImgViewPicasso)

        //Now ImageView with Glide Library
        Glide
            .with(this)
            .load(url_1)
//            .centerCrop()
            .placeholder(R.drawable.progress_animation)
            .error(R.drawable.try_later)
            .into(imgGlide);

        //Now ImageView with Piscasso Library
        Picasso.get()
            .load(url_2)
            .placeholder(R.drawable.progress_animation)
            .error(R.drawable.try_later)
            .into(imgPicasso)
    }
}