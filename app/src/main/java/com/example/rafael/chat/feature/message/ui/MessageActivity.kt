package com.example.rafael.chat.feature.message.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.example.rafael.chat.MyApplication
import com.example.rafael.chat.R
import com.example.rafael.chat.extensions.toast
import com.example.rafael.chat.feature.login.ui.LoginActivity
import com.example.rafael.chat.feature.message.domain.entities.Message
import com.example.rafael.chat.feature.message.presentation.MessagePresenter
import com.example.rafael.chat.feature.message.presentation.MessageView
import com.example.rafael.chat.feature.nickname.ui.NickNameActivity
import com.example.rafael.chat.feature.privatemessage.ui.PrivateMessageActivity
import com.example.rafael.chat.shared.extensions.hide
import com.example.rafael.chat.shared.extensions.show
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject


class MessageActivity : AppCompatActivity(), MessageView, NavigationView.OnNavigationItemSelectedListener, MessageAdapter.Listener {

    var items = ArrayList<Any>()
    var channel = ""

    @Inject
    lateinit var presenter: MessagePresenter

    companion object {
        fun launchIntent(context: Context) = Intent(context, MessageActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        MyApplication.coreComponent.inject(this)
        presenter.bind(this)

        navigationBar()

        recyclerView.layoutManager = LinearLayoutManager(this)
        (recyclerView.layoutManager as LinearLayoutManager).stackFromEnd = true
        recyclerView.adapter = MessageAdapter(items, this)

      channel = if(presenter.getPreference() == "")
          "Global 1"
        else
          presenter.getPreference()


        presenter.changeChannel(channel)
        supportActionBar?.title = channel

        bindListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId) {

            R.id.log_out -> {
                logout()
                true
            }
            R.id.change_nick -> {
                changeNick()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.channel_one -> {
                val channelName = getString(R.string.channel_one)

                presenter.changeChannel(channelName)
                toolbar.title = channelName
            }
            R.id.channel_two -> {
                val channelName = getString(R.string.channel_two)

                presenter.changeChannel(channelName)
                toolbar.title = channelName
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun removeAllItems() {
        items.mapIndexed { index, item -> Pair(index, item) }
                .asReversed()
                .forEach { (index) ->
                    items.removeAt(index)
                    recyclerView.adapter.notifyItemRemoved(index)
                }

        loading.show()

    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun navigationBar() {
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

        finish()
    }

    private fun changeNick() {
        val intent = NickNameActivity.launchIntent(this)
        startActivity(intent)
        finish()
    }

    override fun showMessage(message: Message) {
        items.add(message)

        recyclerView.adapter.notifyItemInserted(items.size-1)
        recyclerView.layoutManager.scrollToPosition(items.size - 1)

        loading.hide()
    }

    override fun showError(throwable: Throwable) {
        Timber.e(throwable)
        toast(R.string.error_generic)
    }

    override fun onClickMessage(leftMessage: Message) {
        val intent = PrivateMessageActivity.launchIntent(this, leftMessage)
        startActivity(intent)
    }

    private fun bindListeners() {
        btnSend.setOnClickListener {
            if (input.text.isNotEmpty())
                presenter.sendMessage(input.text.toString())

            input.text.clear()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbind()
    }

}
