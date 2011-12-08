package com.rushdevo.gitdroid.github.v3.models.event_payloads;

import com.rushdevo.gitdroid.R;
import com.rushdevo.gitdroid.github.v3.models.BaseGithubModel;
import com.rushdevo.gitdroid.github.v3.models.GollumPage;

/**
 * @author jasonrush
 * Event payload for a gollum event (wiki page created/updated/deleted)
 */
public class GollumEvent extends BaseGithubModel implements EventPayload {
	// Properties
	private GollumPage[] pages;
	
	// Getters and Setters
	public int getLayoutId() {
		return R.layout.default_event_list_item;
	}
	public GollumPage[] getPages() {
		return this.pages;
	}
	public void setPages(GollumPage[] pages) {
		this.pages = pages;
	}
}
