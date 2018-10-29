package io.github.alvarosanzrodrigo.projectresourcemanager

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import io.github.alvarosanzrodrigo.projectresourcemanager.Models.Document
import android.widget.TextView
import android.R.attr.category
import android.app.Activity
import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.view.LayoutInflater
import android.widget.ImageView


class AdapterDocument(var items: ArrayList<Document>, var activity: Activity) : BaseAdapter(){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var v = convertView

        if (convertView == null) {
            val inf = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            v = inf.inflate(R.layout.document_list_model, null)
        }

        val dir = items[position]

        val type = v?.findViewById(R.id.document_list_model_type) as TextView
        type.text = dir.type

        val date = v.findViewById(R.id.document_list_model_date) as TextView
        date.text = """${dir.date.day}/${dir.date.month}/${dir.date.year}"""

        val imagen = v.findViewById(R.id.document_list_model_image) as ImageView
        imagen.setImageDrawable(dir.image)

        return v
    }

    override fun getItem(position: Int): Any {
        return items.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }

}