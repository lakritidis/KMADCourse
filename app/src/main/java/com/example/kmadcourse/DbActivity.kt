package com.example.kmadcourse

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.LinkedList

class DbActivity : AppCompatActivity() {

    private lateinit var ct: Context
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var myDB: SQLiteDatabase
    private lateinit var productList: LinkedList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_db)

        ct = this

        val btnSrc: ImageButton = findViewById(R.id.btn_src)
        val btnNewRec: ImageButton = findViewById(R.id.btn_new_rec)
        val etSrc: EditText = findViewById(R.id.et_src)

        btnSrc.setOnClickListener {

            val query = """
                SELECT * FROM products 
                WHERE product_title LIKE '%${etSrc.text}%'
            """.trimIndent()

            val c1 = myDB.rawQuery(query, null)

            val column1 = c1.getColumnIndex("product_id")
            val column2 = c1.getColumnIndex("product_title")
            val column3 = c1.getColumnIndex("product_price")

            productList.clear()

            if (c1.moveToFirst()) {
                do {
                    val productId = c1.getInt(column1)
                    val productTitle = c1.getString(column2)
                    val productPrice = c1.getString(column3)

                    productList.add(
                        Product(
                            productId,
                            productTitle,
                            productPrice.toDouble()
                        )
                    )
                } while (c1.moveToNext())
            }

            c1.close()

            val anotherAdapter = ProductListAdapter(ct, productList)
            mRecyclerView.adapter = anotherAdapter
        }
/*
        btnNewRec.setOnClickListener {
            val intent = Intent(this, RecordActivity::class.java)

            val b = Bundle()
            b.putInt("product_id", 0)

            intent.putExtras(b)

            startActivity(intent)
        }

 */
    }

    override fun onResume() {
        super.onResume()
        populateRecycler()
    }

    private fun populateRecycler() {

        mRecyclerView = findViewById(R.id.db_product_recycler)

        productList = LinkedList()

        myDB = openOrCreateDatabase(
            "products_db",
            MODE_PRIVATE,
            null
        )

        val c = myDB.rawQuery("SELECT * FROM products", null)

        if (c.moveToFirst()) {

            val columnID = c.getColumnIndex("product_id")
            val columnTitle = c.getColumnIndex("product_title")
            val columnPrice = c.getColumnIndex("product_price")

            do {

                val productId = c.getInt(columnID)
                val productTitle = c.getString(columnTitle)
                val productPrice = c.getDouble(columnPrice)

                productList.add(
                    Product(
                        productId,
                        productTitle,
                        productPrice
                    )
                )

            } while (c.moveToNext())

            c.close()
        }

        val mAdapter = ProductListAdapter(this, productList)

        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}