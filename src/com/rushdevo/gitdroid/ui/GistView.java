package com.rushdevo.gitdroid.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rushdevo.gitdroid.R;
import com.rushdevo.gitdroid.github.v3.models.Gist;

/**
 * @author jasonrush
 * View for displaying a Github gist in a list
 */
public class GistView extends LinearLayout {
private Gist gist;
	
	/**
	 * Constructor
	 * @param ctx
	 * @param gist
	 */
	public GistView(Context ctx, Gist gist) {
		super(ctx);
		this.gist = gist;
		addView(inflateView(ctx), new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT));
	}
	
	// Getters and Setters
	public Gist getGist() {
		return this.gist;
	}
	public void setGist(Gist gist) {
		this.gist = gist;
		updateView(this);
	}
	
	// Helpers
	private View inflateView(Context ctx) {
		LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view  = inflater.inflate(R.layout.gist_list_item, null);
		updateView(view);
		return view;
	}
	
	private void updateView(View view) {
		// Grab the views from the layout
		TextView idView = (TextView)view.findViewById(R.id.gist_id);
		TextView descriptionView = (TextView)view.findViewById(R.id.gist_description);
		TextView creatorAndTimestampView = (TextView)view.findViewById(R.id.gist_creator_and_timestamp);
		idView.setText("gist: " + gist.getId());
		descriptionView.setText(gist.getDescription());
		creatorAndTimestampView.setText(gist.getFormattedDateAndByString());
	}
}
