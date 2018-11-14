package io.github.alvarosanzrodrigo.projectresourcemanager.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import io.github.alvarosanzrodrigo.projectresourcemanager.Models.Document
import io.github.alvarosanzrodrigo.projectresourcemanager.R

class AdapterDocument(var items: ArrayList<Document>) : RecyclerView.Adapter<AdapterDocument.MyViewHolder>() {

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder.
        // Each data item is just a string in this case that is shown in a TextView.
        class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)


        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): MyViewHolder {
            // create a new view
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.document_list_model, parent, false) as View
            // set the view's size, margins, paddings and layout parameters
            return MyViewHolder(view)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element

            holder.view.findViewById<TextView>(R.id.document_list_model_type).text = items[position].type
            holder.view.findViewById<ImageView>(R.id.document_list_model_image).setImageDrawable(items[position].image)
            holder.view.findViewById<TextView>(R.id.document_list_model_date).text = items[position].date.toString()
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = items.size
    }