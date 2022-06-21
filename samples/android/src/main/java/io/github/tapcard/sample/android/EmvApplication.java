package io.github.tapcard.sample.android;

import android.app.Application;
import android.graphics.Typeface;

import com.bugsnag.android.Bugsnag;

/**
 * Application class
 * 
 * @author MILLAU Julien
 * 
 */
public class EmvApplication extends Application {

	/**
	 * Custom typeface
	 */
	public static Typeface sTypeface;

	@Override
	public void onCreate() {
		super.onCreate();
		Bugsnag.start(this);
		sTypeface = Typeface.createFromAsset(getAssets(), "OCR-A.ttf");
	}

}
