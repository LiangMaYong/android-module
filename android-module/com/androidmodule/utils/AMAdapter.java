package com.androidmodule.utils;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * AMAdapter
 * 
 * @author LiangMaYong
 * @version 1.0
 * @param <T>
 */
public abstract class AMAdapter<T> extends BaseAdapter {

	private final List<T> datas;
	private Context context;

	public AMAdapter(Context context, List<T> datas) {
		this.datas = datas;
		this.context = context;
	}

	public void addItem(T item) {
		datas.add(item);
		notifyDataSetChanged();
	}

	public void addAll(List<T> items) {
		datas.addAll(items);
		notifyDataSetChanged();
	}

	public void removeItem(T item) {
		if (datas.contains(item)) {
			datas.remove(item);
			notifyDataSetChanged();
		}
	}

	public void replaceDatas(List<T> datas) {
		this.datas.removeAll(this.datas);
		if (datas != null) {
			this.datas.addAll(datas);
		}
		notifyDataSetChanged();
	}

	public void removeAll() {
		this.datas.removeAll(this.datas);
		notifyDataSetChanged();
	}

	public void removeItem(int location) {
		if (location >= 0 && this.datas.size() > location) {
			this.datas.remove(location);
			notifyDataSetChanged();
		}
	}

	@Override
	public int getCount() {
		if (this.datas == null) {
			return 0;
		}
		return this.datas.size();
	}

	@Override
	public T getItem(int position) {
		if (this.datas == null) {
			return null;
		}
		return this.datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public final View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = newView(context, position, parent);
		}
		bindView(position, convertView, getItem(position));
		return convertView;
	}

	protected abstract View newView(Context context, int position, ViewGroup parent);

	protected abstract void bindView(int position, View convertView, T item);

}
