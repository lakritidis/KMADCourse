package com.example.madcourse

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kmadcourse.Product
import com.example.kmadcourse.R
import java.util.LinkedList
import java.util.Locale

class ProductListAdapter(context: Context, private val mProductList: LinkedList<Product>
) : RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    class ProductListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val itemLayout: View = v
        val tvProductId: TextView = v.findViewById(R.id.tv_product_id)
        val tvProductTitle: TextView = v.findViewById(R.id.tv_product_title)
        val tvProductPrice: TextView = v.findViewById(R.id.tv_product_price)
        val tvProductQty: TextView = v.findViewById(R.id.tv_product_qty)
        val btnInc: ImageButton = v.findViewById(R.id.btn_inc)
        val btnDec: ImageButton = v.findViewById(R.id.btn_dec)
        val btnDel: ImageButton = v.findViewById(R.id.btn_del)

        init {
            btnInc.setOnClickListener {
                val updQty = tvProductQty.text.toString().toInt() + 1
                tvProductQty.text = updQty.toString()
            }

            btnDec.setOnClickListener {
                val updQty = tvProductQty.text.toString().toInt() - 1
                if (updQty >= 0) {
                    tvProductQty.text = updQty.toString()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val mItemView = mInflater.inflate(R.layout.product_layout, parent, false)
        return ProductListViewHolder(mItemView)
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        val mCurrent = mProductList[position]

        holder.tvProductId.text = mCurrent.id.toString()
        holder.tvProductQty.text = "0"
        holder.tvProductTitle.text = mCurrent.title
        holder.tvProductPrice.text =
            String.format(Locale.getDefault(), "%.2f $", mCurrent.price)

        holder.itemLayout.setOnClickListener {
            println(mCurrent.stringify())
        }
    }

    override fun getItemCount(): Int = mProductList.size
}