package org.agh.pracinz.evog.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*
import org.agh.pracinz.evog.R
import org.agh.pracinz.evog.di.manual.Db
import org.agh.pracinz.evog.di.manual.ViewModels
import org.agh.pracinz.evog.model.data.LoggedAcountContextHolder
import org.agh.pracinz.evog.model.db.CredentialsDao
import org.agh.pracinz.evog.view.doAsync
import org.agh.pracinz.evog.view.events.list.EventListActivity
import org.agh.pracinz.evog.view.login.LoginActivity
import org.agh.pracinz.evog.view.notification.NotificationListFragment
import org.agh.pracinz.evog.viewmodel.login.LogInViewModel


class MainActivity : AppCompatActivity() {

    lateinit var dao: CredentialsDao
    lateinit var logInViewModel: LogInViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dao = Db.get(applicationContext).credentialsDao()

        setContentView(R.layout.activity_main)
        checkIfLogged()

        mainViewPager.adapter = TabAdapter(supportFragmentManager).apply {
            addTab(EventListActivity(), "Events")
            addTab(NotificationListFragment(), "notifications")
        }
        mainTabLayout.setupWithViewPager(mainViewPager)
        logInViewModel = ViewModels.loginViewModel

    }


    private fun checkIfLogged() {
        if (LoggedAcountContextHolder.account == null) {
            dao.get()?.let {
                logInViewModel.logIn(it)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(
                        onError = { goToLoginActivity() },
                        onSuccess = { acc ->
                            LoggedAcountContextHolder.account = acc
                            finish()
                            startActivity(intent)
                        })
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.itemId.let {
            when (it) {
                R.id.logout -> logout()
                else -> {
                }
            }
        }
        return true
    }

    fun logout() {
        doAsync {
            val credentials = LoggedAcountContextHolder.account!!.credentials
            dao.delete(credentials.login)
            goToLoginActivity()
        }
    }

    fun goToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        finish()
        startActivity(intent)
    }
}
