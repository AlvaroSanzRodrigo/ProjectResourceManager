package io.github.alvarosanzrodrigo.projectresourcemanager.activities

import android.Manifest
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import io.github.alvarosanzrodrigo.projectresourcemanager.Fragments.ProjectDocumentsManagerFragment
import io.github.alvarosanzrodrigo.projectresourcemanager.R
import io.github.alvarosanzrodrigo.projectresourcemanager.adapters.AdapterProjects
import io.github.alvarosanzrodrigo.projectresourcemanager.fragments.NewProjectDialogFragment
import io.github.alvarosanzrodrigo.projectresourcemanager.fragments.ProjectListFragment
import io.github.alvarosanzrodrigo.projectresourcemanager.fragments.ProjectManagerFragment
import io.github.alvarosanzrodrigo.projectresourcemanager.utils.ManagePermissions

class MainActivity : AppCompatActivity(), AdapterProjects.OnClickedItemListener {


    override fun onItemSelected(projectId: Int, projectName: String) {
        System.err.println("projectId: $projectId ; ProjectName: $projectName")
        changeFragment(projectId, projectName)
    }


    private lateinit var managePermissions: ManagePermissions


    // Receive the permissions request result
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE -> {
                val isPermissionsGranted = managePermissions
                    .processPermissionsResult(requestCode, permissions, grantResults)

                if (isPermissionsGranted) {
                    // Do the task now
                    Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Permissions denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //list of all the permissions needed for the application:
        val permissionsList = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
        )

        managePermissions = ManagePermissions(this,permissionsList,
            PERMISSIONS_REQUEST_CODE
        )
        managePermissions.checkPermissions()


        /*
        if (ContextCompat.checkSelfPermission(this.applicationContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                , 1)
        }
        */


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

    fun changeFragment(projectId: Int, projectName: String) {

        /*
        val bundle = Bundle()

        bundle.putString("projectName", projectName)
        bundle.putInt("projectId", projectId)
        val fragment = ProjectManagerFragment()
        fragment.arguments = bundle
        */
        val bundle = Bundle()
        bundle.putString("projectName", projectName)
        bundle.putInt("projectId", projectId)
        val documentsFragment: Fragment =
            ProjectDocumentsManagerFragment()
        documentsFragment.arguments = bundle


        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, documentsFragment)
            addToBackStack(null)

        }.commit()
    }

    companion object {
        const val PERMISSIONS_REQUEST_CODE = 123
    }


}
