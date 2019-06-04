package io.github.alvarosanzrodrigo.projectresourcemanager

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import io.github.alvarosanzrodrigo.projectresourcemanager.adapters.AdapterProjects
import io.github.alvarosanzrodrigo.projectresourcemanager.daoRepositories.ProjectDaoRepository
import io.github.alvarosanzrodrigo.projectresourcemanager.fragments.NewProjectDialogFragment
import io.github.alvarosanzrodrigo.projectresourcemanager.fragments.ProjectListFragment
import io.github.alvarosanzrodrigo.projectresourcemanager.fragments.ProjectManagerFragment
import io.github.alvarosanzrodrigo.projectresourcemanager.models.Project

class MainActivity : AppCompatActivity(), AdapterProjects.OnClickedItemListener{


    override fun onItemSelected(projectId: Int, projectName: String) {
        System.err.println("projectId: $projectId ; ProjectName: $projectName")
        changeFragment(projectId, projectName)
    }

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
        if (ContextCompat.checkSelfPermission(this.applicationContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                , 1)
        }



        if (findViewById<FrameLayout>(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return
            }

            // Create a new Fragment to be placed in the activity layout
            val firstFragment = ProjectListFragment()

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.arguments = intent.extras

            // Add the fragment to the 'fragment_container' FrameLayout
            supportFragmentManager.beginTransaction().apply {
                add(R.id.fragment_container, firstFragment)
            }.commit()
        }
    }

    fun changeFragment(projectId: Int, projectName: String){
        val bundle = Bundle()
        bundle.putString("projectName", projectName)
        bundle.putInt("projectId", projectId)
        val fragment = ProjectManagerFragment()
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)

        }.commit()
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
                    val newFragment = NewProjectDialogFragment()
                    newFragment.show(supportFragmentManager, "Cart")

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
