package io.github.alvarosanzrodrigo.projectresourcemanager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var dl: DrawerLayout
    private lateinit var t: ActionBarDrawerToggle
    private lateinit var nv: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dl = findViewById(R.id.activity_main)
        t = ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close)
        dl.addDrawerListener(t)
        t.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        nv = findViewById(R.id.nv)
        setListeners()
    }

    private fun setListeners(){
        nv.setNavigationItemSelectedListener { item ->
            val id = item.itemId
            when (id) {
                R.id.account -> {
                    Toast.makeText(this@MainActivity, "My Account", Toast.LENGTH_SHORT).show()
                    dl.closeDrawers()
                    true
                }
                R.id.settings -> {
                    Toast.makeText(this@MainActivity, "Settings", Toast.LENGTH_SHORT).show()
                    dl.closeDrawers()
                    true
                }
                R.id.new_proyect -> {
                    Toast.makeText(this@MainActivity, "New Project", Toast.LENGTH_SHORT).show()
                    dl.closeDrawers()
                    true
                }
                R.id.credits -> {
                    Toast.makeText(this@MainActivity, "Credits", Toast.LENGTH_SHORT).show()
                    dl.closeDrawers()
                    true
                }
                else -> true
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (t.onOptionsItemSelected(item)) true else super.onOptionsItemSelected(item)
    }
}
