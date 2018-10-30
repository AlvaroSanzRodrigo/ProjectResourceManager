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
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout

class AdapterDocument(var items: ArrayList<Document>) : RecyclerView.Adapter<AdapterDocument.MyViewHolder>() {

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder.
        // Each data item is just a string in this case that is shown in a TextView.
        class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)


        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): AdapterDocument.MyViewHolder {
            // create a new view
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.recycler_view_item, parent, false) as View
            // set the view's size, margins, paddings and layout parameters
            return MyViewHolder(view)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.view.findViewById<TextView>(R.id.textView).text = items[position].type
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = 4
    }