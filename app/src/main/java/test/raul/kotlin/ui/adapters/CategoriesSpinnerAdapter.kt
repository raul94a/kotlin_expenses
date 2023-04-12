package test.raul.kotlin.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text
import test.raul.kotlin.R
import test.raul.kotlin.database.entity.Category

class CategoriesSpinnerAdapter(context: Context, private val items: List<Category>) : ArrayAdapter<Category>(context, 0, items) {

    // Override the getView() method to return the custom layout for the Spinner item
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Inflate the custom layout for the Spinner item
        val customLayout = LayoutInflater.from(context).inflate(R.layout.custom_categories_spinner_item, parent, false)

        // Find the views within the custom layout using their IDs
        val imageView = customLayout.findViewById<ImageView>(R.id.imageView)
        val textView = customLayout.findViewById<TextView>(R.id.textView)
        // Set the values for the ImageView and TextView
        val item = items[position] // Get the data model item for the current position

        imageView.setBackgroundColor(item.getColor()) // Replace with your actual image resource ID or image loading logic
        textView.text = item.name // Replace with your actual text value from your data model
        // Return the custom layout view as the Spinner item
        return customLayout
    }

    // Override the getDropDownView() method to return the custom layout for the Spinner drop-down item
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Call the getView() method to reuse the custom layout for the Spinner drop-down item
        return getView(position, convertView, parent)
    }
}