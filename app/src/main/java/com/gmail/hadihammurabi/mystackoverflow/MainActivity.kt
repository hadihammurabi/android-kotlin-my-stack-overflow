package com.gmail.hadihammurabi.mystackoverflow

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.gmail.hadihammurabi.mystackoverflow.models.Profile
import com.gmail.hadihammurabi.mystackoverflow.models.ProfileResponse
import com.gmail.hadihammurabi.mystackoverflow.services.ProfileService
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.loading.*

class MainActivity : AppCompatActivity() {

    var profile: Profile? = null
    var loading: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading)

        fetchProfileData().subscribe(
            {
                profile = Profile(it.items[0].display_name, it.items[0].location, it.items[0].profile_image, it.items[0].website_url)

                setContentView(R.layout.activity_main)
                v_display_name.text = profile?.display_name
                v_location.text = profile?.location
                v_website_url.text = profile?.website_url
                Picasso.get().load(profile?.profile_image).into(v_profile_image)
            }, {
                Toast.makeText(applicationContext, "Gagal mengambil data.", Toast.LENGTH_SHORT).show()
            }
        )

    }

    private fun fetchProfileData(): Observable<ProfileResponse> {
        return ProfileService.create().getProfile()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}
