package uz.project.hunarbrand.profile.auth.signup.send_user_info.Utills

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import uz.project.hunarbrand.R

class DropdownAdapter(context: Context, resource: Int, objects: List<String>) :
    ArrayAdapter<String>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val textView = view.findViewById<TextView>(R.id.dropdownTextView)
        textView.maxLines = 1
        textView.ellipsize = TextUtils.TruncateAt.END
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val textView = view.findViewById<TextView>(R.id.dropdownTextView)
        textView.maxLines = 1
        textView.ellipsize = TextUtils.TruncateAt.END
        return view
    }

}