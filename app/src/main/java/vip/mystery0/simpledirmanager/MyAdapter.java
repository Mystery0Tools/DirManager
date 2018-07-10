package vip.mystery0.simpledirmanager;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import vip.mystery0.dirManager.BaseDirAdapter;

public class MyAdapter extends BaseDirAdapter<MyAdapter.ViewHolder> {
	private List<File> list;

	public MyAdapter(@NotNull ArrayList<File> list) {
		super(list);
		this.list = list;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dirmanager_item_dir_manager, parent, false));
	}

	@Override
	public void initViewHolder(@NotNull ViewHolder holder, int position) {
		File file = list.get(position);
		setTextViewText(file, holder.textView);
	}

	class ViewHolder extends RecyclerView.ViewHolder {
		TextView textView = itemView.findViewById(R.id.textView_title);

		ViewHolder(View itemView) {
			super(itemView);
		}
	}
}
