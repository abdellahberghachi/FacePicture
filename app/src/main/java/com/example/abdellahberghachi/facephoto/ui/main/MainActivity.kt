package com.example.abdellahberghachi.facephoto.ui.main

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Intents.Insert.EMAIL
import android.util.Log
import android.widget.Toast
import com.example.abdellahberghachi.facephoto.R
import com.example.abdellahberghachi.facephoto.ui.albums.AlbumsActivity
import com.example.abdellahberghachi.facephoto.utils.BaseActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.FacebookCallback
import com.facebook.login.LoginManager
import timber.log.Timber


class MainActivity : BaseActivity() {

    lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    override fun initEvents() {
        // init callback for login if user was succuessfully logged then we go to albums activity
        LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {

                        // Check  user_photos permissions
                        var granted: Boolean? = false
                        val permissions = AccessToken.getCurrentAccessToken().permissions
                        for (permission in permissions) {
                            Timber.e(permission)
                            if (permission == "user_photos") {
                                granted = true
                            }
                        }
                        if (granted!!) {
                            startActivity(Intent(this@MainActivity, AlbumsActivity::class.java))
                        } else {
                            Toast.makeText(this@MainActivity, "Permission not granted.", Toast.LENGTH_LONG).show()
                        }

                    }

                    override fun onCancel() {

                    }

                    override fun onError(exception: FacebookException) {
                        Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    override fun initUi() {
        // add permission for photos
        login_button.setReadPermissions(Arrays.asList("user_photos"))
    }

    override fun initWi() {
        callbackManager = CallbackManager.Factory.create()

        //check if user is already logged then start automatically the ablums activity
        if (AccessToken.getCurrentAccessToken() != null) {
            startActivity(Intent(this@MainActivity, AlbumsActivity::class.java))
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

    }
}
