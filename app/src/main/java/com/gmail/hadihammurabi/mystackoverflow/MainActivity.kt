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
import kotlinx.android.synthetic.main.api_error.*
import kotlinx.android.synthetic.main.loading.*

class MainActivity : AppCompatActivity() {

    var profile: Profile? = null
    var loading: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getProfile()

    }

    private fun getProfile() {
        fetchProfileData().subscribe(
            {
                onSuccess(it)
            }, {
                onError()
            }
        )
    }

    private fun fetchProfileData(): Observable<ProfileResponse> {
        setContentView(R.layout.loading)
        return ProfileService.create().getProfile()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    private fun onSuccess(result: ProfileResponse) {
        profile = Profile(result.items[0].display_name, result.items[0].location, result.items[0].profile_image, result.items[0].website_url)

        setContentView(R.layout.activity_main)
        v_display_name.text = profile?.display_name
        v_location.text = profile?.location
        v_website_url.text = profile?.website_url
        Picasso.get().load(profile?.profile_image).into(v_profile_image)
    }

    private fun onError() {
        setContentView(R.layout.api_error)
        v_error_message.text = "Gagal mengambil data."
        v_btn_reload.setOnClickListener {
            finish()
            overridePendingTransition(0, 0)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
    }

}
