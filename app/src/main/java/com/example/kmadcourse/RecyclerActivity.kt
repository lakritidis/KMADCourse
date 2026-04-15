package com.example.kmadcourse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.LinkedList

class RecyclerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        val mProductListDataset = LinkedList<Product>().apply {
            add(Product(1, "Sony Playstation 3", 16.60, 0))
            add(Product(2, "LG 42.5' Monitor", 140.2, 0))
            add(Product(3, "Intel Core I7 7200 CPU", 167.3,0))
            add(Product(4, "Apple iPhone 7 - 32 GB", 500.3,0))
            add(Product(5, "Samsung Galaxy Note 3", 600.3,0))
            add(Product(6, "Xiaomi Redmi Note 4", 125.3,0))
            add(Product(7, "S-Band Microwave Radio Link", 900.2,0))
            add(Product(8, "Solid State Power Amplifier", 3000.0,0))
            add(Product(9, "A laptop", 800.0,0))
            add(Product(10, "Washing machine", 650.0,0 ))
            add(Product(11, "HP Laptop", 50.0,0 ))
            add(Product(12, "Dell Laptop", 50.0,0 ))
            add(Product(13, "Sony Playstation 3", 16.60, 0))
            add(Product(14, "LG 42.5' Monitor", 140.2, 0))
            add(Product(15, "Intel Core I7 7200 CPU", 167.3,0))
            add(Product(16, "Apple iPhone 7 - 32 GB", 500.3,0))
            add(Product(17, "Samsung Galaxy Note 3", 600.3,0))
            add(Product(18, "Xiaomi Redmi Note 4", 125.3,0))
            add(Product(19, "S-Band Microwave Radio Link", 900.2,0))
            add(Product(20, "Solid State Power Amplifier", 3000.0,0))
            add(Product(21, "A laptop", 800.0,0))
            add(Product(22, "Washing machine", 650.0,0 ))
            add(Product(23, "HP Laptop", 50.0,0 ))
            add(Product(24, "Dell Laptop", 50.0,0 ))
        }

        val recyclerView = findViewById<RecyclerView>(R.id.product_recycler)

        val adapter = ProductListAdapter(this, mProductListDataset)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}
