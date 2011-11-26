package com.rushdevo.gitdroid.activities;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.github.api.v2.schema.User;
import com.github.api.v2.services.auth.OAuthAuthentication;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import com.rushdevo.gitdroid.R;
import com.rushdevo.gitdroid.fragments.CollaboratorRepositoriesFragment;
import com.rushdevo.gitdroid.fragments.FollowersFragment;
import com.rushdevo.gitdroid.fragments.FollowingFragment;
import com.rushdevo.gitdroid.fragments.GistsFragment;
import com.rushdevo.gitdroid.fragments.NewsFeedFragment;
import com.rushdevo.gitdroid.fragments.OrganizationsFragment;
import com.rushdevo.gitdroid.fragments.PublicActivityFragment;
import com.rushdevo.gitdroid.fragments.RepositoriesFragment;
import com.rushdevo.gitdroid.fragments.WatchedRepositoriesFragment;
import com.rushdevo.gitdroid.github.Github;
import com.rushdevo.gitdroid.github.GithubImpl;

/**
 * @author jasonrush
 * Base activity for shared functionality
 */
public abstract class BaseActivity extends FragmentActivity {
	/** The key in intent's extras for the action selected */
	public static final String SELECTED_ACTION = "SELECTED_ACTION";
	
	protected GoogleAnalyticsTracker analyticsTracker;
	protected Github github;
	protected Map<String, Fragment> contentFragmentMap;
	protected Map<String, String> reverseContentFragmentMap;
	
	/**
	 * Track a page view with Google Analytics
	 * @param className - The name of the activity or fragment we are tracking
	 */
	public void trackPageView(String className) {
		GoogleAnalyticsTracker tracker = getAnalyticsTracker();
		if (tracker != null) {
			tracker.trackPageView("/"+className);
		}
	}
	
	/**
	 * Lazy-load the github delegate
	 */
	public Github getGithub() {
		if (github == null) {
			github = new GithubImpl(this, getSharedPrefs());
		}
		return github;
	}
	
	/**
	 * @return the instance of shared preferences
	 */
	public SharedPreferences getSharedPrefs() {
		return PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());
	}
	
	/**
	 * @return the authentication instance 
	 */
	public OAuthAuthentication getAuthentication() {
		return getGithub().getOAuthAuthentication();
	}
	
	/**
	 * @return the currently authenticated user
	 */
	public User getCurrentUser() {
		return getGithub().getCurrentUser();
	}
	
	/**
	 * If the analytics tracker is running, stop it
	 */
	protected void stopAnalyticsTracking() {
		if (analyticsTracker != null) {
			analyticsTracker.stopSession();
			analyticsTracker = null;
		}
	}
	
	/**
	 * @return The lazy-loaded Google Analytics Tracker
	 */
	protected GoogleAnalyticsTracker getAnalyticsTracker() {
		if (analyticsTracker == null) {
			analyticsTracker = GoogleAnalyticsTracker.getInstance();
			Properties analyticsProperties = new Properties();
			try {
				analyticsProperties.load(this.getResources().getAssets().open("analytics.properties"));
				analyticsTracker.startNewSession(analyticsProperties.getProperty("account"), 30, this);
			} catch (IOException e) {
				e.printStackTrace();
				analyticsTracker = null;
			}
		}
		return analyticsTracker;
	}
	
	/**
	 * @return The lazy-loaded content fragment map
	 */
	protected Map<String, Fragment> getContentFragmentMap() {
		if (contentFragmentMap == null) {
	    	contentFragmentMap = new TreeMap<String, Fragment>();
			contentFragmentMap.put(getString(R.string.news_feed), new NewsFeedFragment());
			contentFragmentMap.put(getString(R.string.public_activity), new PublicActivityFragment());
			contentFragmentMap.put(getString(R.string.repositories), new RepositoriesFragment());
			contentFragmentMap.put(getString(R.string.collaborator_repositories), new CollaboratorRepositoriesFragment());
			contentFragmentMap.put(getString(R.string.watched_repositories), new WatchedRepositoriesFragment());
			contentFragmentMap.put(getString(R.string.followers), new FollowersFragment());
			contentFragmentMap.put(getString(R.string.following), new FollowingFragment());
			contentFragmentMap.put(getString(R.string.organizations), new OrganizationsFragment());
			contentFragmentMap.put(getString(R.string.gists), new GistsFragment());
		}
		return contentFragmentMap;
    }
	
	/**
	 * Clear the selected action from preferences
	 */
	protected void clearSelectedAction() {
        Editor prefEditor = getSharedPrefs().edit();
        prefEditor.remove(SELECTED_ACTION);
        prefEditor.commit();
	}
}
