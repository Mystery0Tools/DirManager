/*
 * Created by Mystery0 on 18-3-20 下午2:01.
 * Copyright (c) 2018. All Rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package vip.mystery0.dirManager

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import java.io.File

internal class DirAdapter(private val arrayList: ArrayList<File>) : BaseDirAdapter<DirAdapter.ViewHolder>(arrayList) {
	override fun initViewHolder(holder: ViewHolder, position: Int) {
		val file = arrayList[position]
		setTextViewText(file, holder.textViewTitle)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.dirmanager_item_dir_manager, parent, false))
	}

	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val imageView = itemView.findViewById<ImageView>(R.id.imageView)
		val textViewTitle = itemView.findViewById<TextView>(R.id.textView_title)
	}
}