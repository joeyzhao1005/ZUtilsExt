//package com.emilsjolander.components.stickylistheaders.test;
//
//import static android.widget.Toast.LENGTH_SHORT;
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.app.ExpandableListActivity;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.View;
//import android.widget.AbsListView;
//import android.widget.AbsListView.OnScrollListener;
//import android.widget.AdapterView;
//import android.widget.Toast;
//
//import com.emilsjolander.components.stickylistheaders.StickyListHeadersListView;
//import com.emilsjolander.components.stickylistheaders.StickyListHeadersListView.OnHeaderClickListener;
//import com.kit.extend.widget.R;
//
//public class TestActivity extends Activity implements OnScrollListener,
//		AdapterView.OnItemClickListener, OnHeaderClickListener {
//
//	private static final String KEY_LIST_POSITION = "KEY_LIST_POSITION";
//	private int firstVisible;
//	private TestBaseAdapter adapter;
//
//	@SuppressLint("NewApi")
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.main);
//		StickyListHeadersListView stickyList = (StickyListHeadersListView) findViewById(R.id.list);
//		stickyList.setOnScrollListener(this);
//		stickyList.setOnItemClickListener(this);
//		stickyList.setOnHeaderClickListener(this);
//
//		if (savedInstanceState != null) {
//			firstVisible = savedInstanceState.getInt(KEY_LIST_POSITION);
//		}
//
//		// stickyList.addHeaderView(getLayoutInflater().inflate(R.layout.list_header,
//		// null));
//		// stickyList.addFooterView(getLayoutInflater().inflate(R.layout.list_footer,
//		// null));
//		stickyList.setEmptyView(findViewById(R.id.empty));
//		adapter = new TestBaseAdapter(this);
//		stickyList.setAdapter(adapter);
//		stickyList.setSelection(firstVisible);
//
//		stickyList.setDrawingListUnderStickyHeader(true);
//
//		new Handler().postDelayed(new Runnable() {
//
//			public void run() {
//				// adapter.clearAll();
//				adapter.notifyDataSetChanged();
//				Toast.makeText(getApplicationContext(), "notifyDataSetChanged",
//						Toast.LENGTH_SHORT).show();
//			}
//		}, 5000);
//	}
//
//	@Override
//	public void onSaveInstanceState(Bundle outState) {
//		super.onSaveInstanceState(outState);
//		outState.putInt(KEY_LIST_POSITION, firstVisible);
//	}
//
//	public void onScroll(AbsListView view, int firstVisibleItem,
//			int visibleItemCount, int totalItemCount) {
//		this.firstVisible = firstVisibleItem;
//	}
//
//	public void onScrollStateChanged(AbsListView view, int scrollState) {
//	}
//
//	public void onItemClick(AdapterView<?> parent, View view, int position,
//			long id) {
//		Toast.makeText(this, "Item " + position + " clicked!", LENGTH_SHORT)
//				.show();
//	}
//
//	public void onHeaderClick(StickyListHeadersListView l, View header,
//			int itemPosition, long headerId, boolean currentlySticky) {
//		Toast.makeText(this, "header " + headerId, Toast.LENGTH_SHORT).show();
//	}
//
//}