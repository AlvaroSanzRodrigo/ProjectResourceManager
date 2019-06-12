package io.github.alvarosanzrodrigo.projectresourcemanager.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import io.github.alvarosanzrodrigo.projectresourcemanager.models.Document
import io.github.alvarosanzrodrigo.projectresourcemanager.R
import io.github.alvarosanzrodrigo.projectresourcemanager.enums.DocumentTypes

class AdapterDocument(var items: ArrayList<Document>) : RecyclerView.Adapter<AdapterDocument.MyViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    var mCallBack: OnClickedItemListener? = null




    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        // create a new view
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.document_list_model, parent, false) as View
        // set the view's size, margins, paddings and layout parameters
        return MyViewHolder(view)
    }

    interface OnClickedItemListener {
        fun onItemSelected(document: Document)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        //holder.view.findViewById<TextView>(R.id.document_list_model_type).text = items[position].type

        when (items[position].type) {
            DocumentTypes.TEXT ->
            {
                holder.view.findViewById<ImageView>(R.id.document_list_model_image).setImageResource(R.drawable.ic_text_dark)
            }
            DocumentTypes.PICTURE -> Picasso.get()
                .load("file://" + items[position].path)
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_image_error)
                .into(holder.view.findViewById<ImageView>(R.id.document_list_model_image))

            DocumentTypes.AUDIO ->
            {
                holder.view.findViewById<ImageView>(R.id.document_list_model_image).setImageResource(R.drawable.ic_audio_dark)
            }

            DocumentTypes.VIDEO ->
            {
                holder.view.findViewById<ImageView>(R.id.document_list_model_image).setImageResource(R.drawable.ic_video_dark)
            }

            DocumentTypes.ERROR -> Picasso.get()
                .load(R.drawable.ic_image_error)
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_image_error)
                .into(holder.view.findViewById<ImageView>(R.id.document_list_model_image))
        }


        holder.itemView.setOnClickListener {
            items[position].documentId?.let { mCallBack?.onItemSelected(items[position]) }
        }

        holder.view.findViewById<TextView>(R.id.document_list_model_type).text = items[position].title
        holder.view.findViewById<TextView>(R.id.document_list_model_date).text = items[position].date.toString()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = items.size
}