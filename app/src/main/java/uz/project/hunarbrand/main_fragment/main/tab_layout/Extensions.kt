package uz.project.hunarbrand.main_fragment.main.tab_layout

import android.os.Bundle
import androidx.fragment.app.Fragment

fun <T: Fragment>T.withArguments(action: Bundle.() -> Unit): T{
    return apply {
        val args = Bundle().apply(action)
        arguments =args
    }
}