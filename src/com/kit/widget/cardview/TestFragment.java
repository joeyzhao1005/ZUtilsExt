package com.kit.widget.cardview;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

import com.kit.extend.widget.R;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

public class TestFragment extends Fragment{
	private TextView tv;
	private View root;

	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container, Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.card_view_frag_layout, container,false);
		initUI(root);
		return root;
	}

	private void initUI(final View root) {
		root.setClickable(true);
		tv = (TextView) root.findViewById(R.id.textView);
		root.findViewById(R.id.button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				root.setClickable(false);
				ViewPropertyAnimator.animate(root)
				.rotationY(-90).setDuration(200)
				.setListener(new AnimatorListenerAdapter(){
					@Override
					public void onAnimationEnd(Animator animation) {
						root.clearAnimation();
						root.setVisibility(View.INVISIBLE);
					}
				});
			}
		});
	}

    @TargetApi(11)
	public void show(final View view,Bundle bundle){
		String text = bundle.getString("text");
		tv.setText(text);
		view.setClickable(false);
		view.setRotationY(0);
		ViewHelper.setRotationY(root, -90);
		root.setVisibility(View.VISIBLE);

		ViewPropertyAnimator.animate(view).rotationY(90)
		.setDuration(300).setListener(null)
		.setInterpolator(new AccelerateInterpolator());

		ViewPropertyAnimator.animate(root)
		.rotationY(0).setDuration(200).setStartDelay(300)
		.setListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				ViewHelper.setRotationY(view, 0);
				view.setClickable(true);
			}
		});
	}
}
