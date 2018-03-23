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

import android.content.Context
import android.os.Environment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.File

open class DirManager : FrameLayout {
	companion object {
		private const val TAG = "DirManager"
	}

	private var recyclerView: RecyclerView
	private var progressBar: ProgressBar
	private var rootPath: String = Environment.getExternalStorageDirectory().absolutePath//默认根目录是/sdcard
	private var currentPath = Environment.getExternalStorageDirectory()
	private var isRefresh = false
	private var baseDirAdapter: BaseDirAdapter<*> = DirAdapter(ArrayList())

	constructor(context: Context) : super(context)
	constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
	constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

	init {
		LayoutInflater.from(context).inflate(R.layout.dirmanager_view_dir_manager, this)
		recyclerView = findViewById(R.id.recyclerView)
		progressBar = findViewById(R.id.progressBar)
		progressBar.visibility = View.GONE
		recyclerView.layoutManager = LinearLayoutManager(context)
	}

	override fun onAttachedToWindow() {
		super.onAttachedToWindow()
		initDirManager()
	}

	private fun initDirManager() {
		baseDirAdapter.setRootPath(rootPath)
		baseDirAdapter.currentFile = File(rootPath)
		baseDirAdapter.dirSelectedListener = object : BaseDirAdapter.DirSelectedListener {
			override fun onSelected(selectedFile: File) {
				currentPath = selectedFile
				updateList()
			}
		}
		recyclerView.adapter = baseDirAdapter
		requestLayout()
		updateList()
	}

	fun <T : RecyclerView.ViewHolder> setAdapter(adapter: BaseDirAdapter<T>) {
		baseDirAdapter = adapter
	}

	fun setRootPath(rootPath: File) {
		setRootPath(rootPath.absolutePath)
	}

	fun setRootPath(rootPath: String) {
		this.rootPath = rootPath
	}

	fun getRootPath(): String {
		return rootPath
	}

	fun getCurrentPath(): String {
		return currentPath.absolutePath
	}

	private fun updateList() {
		if (isRefresh)
			return
		isRefresh = true
		Observable.create<Boolean> {
			baseDirAdapter.list.clear()
			if (currentPath.absolutePath != rootPath)
				baseDirAdapter.list.add(currentPath.parentFile)
			currentPath.listFiles().forEach {
				if (it.isDirectory && !baseDirAdapter.list.contains(it))
					baseDirAdapter.list.add(it)
			}
			baseDirAdapter.list.sort()
			it.onComplete()
		}
				.subscribeOn(Schedulers.newThread())
				.unsubscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(object : Observer<Boolean> {
					override fun onComplete() {
						baseDirAdapter.notifyDataSetChanged()
						progressBar.visibility = View.GONE
						isRefresh = false
					}

					override fun onSubscribe(d: Disposable) {
						progressBar.visibility = View.VISIBLE
						isRefresh = false
					}

					override fun onNext(t: Boolean) {
					}

					override fun onError(e: Throwable) {
						Log.w(TAG, "onError: ", e)
						progressBar.visibility = View.GONE
						isRefresh = false
					}
				})
	}
}