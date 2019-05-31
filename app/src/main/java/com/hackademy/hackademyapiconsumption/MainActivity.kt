package com.hackademy.hackademyapiconsumption

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private val userApiService by lazy {
        UserApiServiceFactory.createService()
    }

    private val randomUser by lazy {
        userApiService.getRandomUser()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            getRandomUser()
        }
    }

    private fun updateUi(user:UserInfo){
        tv_user_name.text = user.username.first + " " + user.username.last
        tv_phone.text = user.phone
        tv_email.text = user.email

    }

    private fun getRandomUser() = randomUser
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { toast("Iniciando petición") }
        .doFinally { toast("Finalizó petición") }
        .subscribeBy(
            onSuccess = {
                Log.d("HackademyTag", it.toString())
                updateUi(it.results[0])
            },
            onError = {
                Log.e("HackademyTag", it.toString())
            }
        )


}

fun Context.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
