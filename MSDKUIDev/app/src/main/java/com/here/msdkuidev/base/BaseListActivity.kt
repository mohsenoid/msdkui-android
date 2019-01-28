/*
 * Copyright (C) 2017-2019 HERE Europe B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.here.msdkuidev.base

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.here.msdkuidev.R
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.widget.DividerItemDecoration
import android.widget.LinearLayout.VERTICAL


@SuppressLint("Registered")
open class BaseListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    protected fun setUpList(list : List<Pair<String, String>>, listener: LandingScreenAdapter.Listener) {
        with(landing_list) {
            layoutManager = android.support.v7.widget.LinearLayoutManager(
                this@BaseListActivity,
                VERTICAL,
                false
            )
            setHasFixedSize(true)
            val dividerItemDecoration = DividerItemDecoration(this@BaseListActivity, VERTICAL)
            addItemDecoration(dividerItemDecoration)
        }
        val adapter = LandingScreenAdapter(list, this@BaseListActivity)
        adapter.itemListener = listener
        landing_list.adapter = adapter
    }

    class LandingScreenAdapter(private val items: List<Pair<String, String>>,
                               private val context: Context) : RecyclerView.Adapter<ViewHolder>() {

        var itemListener: Listener? = null

        override fun getItemCount(): Int {
            return items.size
        }

        // Inflates the item views
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val rowItem = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false)
            rowItem.setOnClickListener { itemListener?.onItemClicked(rowItem) }
            return ViewHolder(rowItem)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            with(holder) {
                text1.text = items[position].first
                with(text2) {
                    if(items[position].second.isEmpty()) {
                        visibility = View.GONE
                    } else {
                        visibility = View.VISIBLE
                        text = items[position].second
                    }
                }
            }
        }

        /**
         * Listener to be notified when item is clicked.
         */
        interface Listener {

            /**
             * Callback to indicate a row has been clicked.
             */
            fun onItemClicked(view: View)
        }
    }

    /**
     * ViewHolder to hold data for a row.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text1 = view.findViewById<TextView>(android.R.id.text1)!!
        val text2 = view.findViewById<TextView>(android.R.id.text2)!!
    }
}