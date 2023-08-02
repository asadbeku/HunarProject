package uz.project.hunarbrand.main_fragment.main.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import uz.project.hunarbrand.R
import uz.project.hunarbrand.splash_screen.SplashScreenFragment
import uz.project.hunarbrand.databinding.ActivityMainBinding
import uz.project.hunarbrand.db.Database

class ActivityFragment : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        Database.init(this)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fullContainer, SplashScreenFragment())
            addToBackStack(null)
            commit()
        }
    }
}