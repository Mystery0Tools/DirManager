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
import android.widget.TextView
import java.io.File

abstract class BaseDirAdapter<T : RecyclerView.ViewHolder>(open val list: ArrayList<File>) : RecyclerView.Adapter<T>() {
	private var rootPath: String? = null
	var dirSelectedListener: DirSelectedListener? = null
	lateinit var currentFile: File

	override fun getItemCount(): Int {
		return list.size
	}

	override fun onBindViewHolder(holder: T, position: Int) {
		initViewHolder(holder, position)
		val file = list[position]
		holder.itemView.setOnClickListener {
			currentFile = file
			if (dirSelectedListener != null) {
				dirSelectedListener!!.onSelected(file)
			}
		}
	}

	abstract fun initViewHolder(holder: T, position: Int)

	fun setTextViewText(file: File, textView: TextView) {
		if (file.absolutePath == currentFile.parentFile.absolutePath || file.absolutePath == rootPath)
			textView.text = ".."
		else
			textView.text = file.name
	}

	fun setRootPath(rootPath: String) {
		this.rootPath = rootPath
		currentFile = File(rootPath)
	}

	fun setDirSelectedListener(listener: (File) -> Unit) {
		dirSelectedListener = object : DirSelectedListener {
			override fun onSelected(selectedFile: File) {
				listener(selectedFile)
			}
		}
	}

	interface DirSelectedListener {
		fun onSelected(selectedFile: File)
	}
}