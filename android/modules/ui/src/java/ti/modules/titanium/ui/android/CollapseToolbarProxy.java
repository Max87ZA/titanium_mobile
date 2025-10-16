/**
 * Titanium SDK
 * Copyright TiDev, Inc. 04/07/2022-Present
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
package ti.modules.titanium.ui.android;

import android.app.Activity;

import org.appcelerator.kroll.KrollFunction;
import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.titanium.TiC;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.util.TiConvert;
import org.appcelerator.titanium.view.TiDrawableReference;
import org.appcelerator.titanium.view.TiUIView;
import android.graphics.drawable.Drawable;

import ti.modules.titanium.ui.widget.TiUICollapseToolbar;

@Kroll.proxy(creatableInModule = AndroidModule.class)
public class CollapseToolbarProxy extends TiViewProxy
{
	private static final String TAG = "CollapseToolbarProxy";
	private TiUICollapseToolbar collapseToolbar;

	@Override
	public TiUIView createView(Activity activity)
	{
		collapseToolbar = new TiUICollapseToolbar(this);
		collapseToolbar.getLayoutParams().autoFillsHeight = true;
		collapseToolbar.getLayoutParams().autoFillsWidth = true;
		return collapseToolbar;
	}

	@Kroll.setProperty
	public void setImage(Object obj)
	{
		collapseToolbar.setImage(TiDrawableReference.fromObject(this, obj).getBitmap(false));
	}

	@Kroll.setProperty
	public void setTitle(String text)
	{
		collapseToolbar.setTitle(text);
	}

	@Kroll.setProperty
	public void setContentView(Object obj)
	{
		if (obj instanceof TiViewProxy) {
			setPropertyAndFire(TiC.PROPERTY_CONTENT_VIEW, (TiViewProxy) obj);
		}
	}

	@Kroll.setProperty
	public void setBarColor(String value)
	{
		collapseToolbar.setBarColor(TiConvert.toColor(value, TiApplication.getAppCurrentActivity()));
	}

	@Kroll.setProperty
	public void setContentScrimColor(String value)
	{
		collapseToolbar.setContentScrimColor(TiConvert.toColor(value, TiApplication.getAppCurrentActivity()));
	}

	@Kroll.setProperty
	public void setDisplayHomeAsUp(boolean value)
	{
		collapseToolbar.setDisplayHomeAsUp(value);
	}

	@Kroll.setProperty
	public void setImageHeight(int value)
	{
		collapseToolbar.setImageHeight(value);
	}

	@Kroll.setProperty
	public void setonHomeIconItemSelected(KrollFunction value)
	{
		collapseToolbar.setonHomeIconItemSelected(value);
	}

	@Kroll.setProperty
	public void setFlags(int value)
	{
		collapseToolbar.setFlags(value);
	}

	@Kroll.setProperty
	public void setColor(String value)
	{
		collapseToolbar.setColor(TiConvert.toColor(value, TiApplication.getAppCurrentActivity()));
	}

	@Kroll.setProperty
	public void setNavigationIconColor(String value)
	{
		collapseToolbar.setNavigationIconColor(TiConvert.toColor(value, TiApplication.getAppCurrentActivity()));
	}
	
	// @Kroll.method
	// public void addMenuItem(int itemId, String title, boolean showAsAction)
	// {
	// 	// int iconResId = TiDrawableReference.fromObject(this, iconPath).getBitmap(false)
	// 	collapseToolbar.addMenuItem(itemId, title, showAsAction);
	// }
	@Kroll.method
	public void addMenuItem(KrollDict d)
	{

		int itemId = d.optInt("itemId", 0);
		String title = d.optString("title", "");
		boolean showAsAction = d.optBoolean("showAsAction", false);

		// Try to resolve image if provided
		if (d.containsKey("image") && d.get("image") != null) {
			TiDrawableReference ref = TiDrawableReference.fromObject(this, d.get("image"));

			if (ref != null) {
				// Fast path: if it’s a real Android resource, use the int overload
				// if (ref.isTypeResourceId()) {
				// 	int resId = ref.getResourceId();
				// 	collapseToolbar.addMenuItem(itemId, title, resId, showAsAction);
				// 	return;
				// }

				// Asset/file/blob/url → use Drawable overload
				Drawable icon = ref.getDrawable();
				collapseToolbar.addMenuItem(itemId, title, icon, showAsAction);
				return;
			}
		}

		// No icon
		collapseToolbar.addMenuItem(itemId, title, (Drawable) null, showAsAction);
	}

	@Kroll.method
	public void removeMenuItem(int itemId)
	{
		collapseToolbar.removeMenuItem(itemId);
	}

	@Kroll.method
	public void clearMenu()
	{
		collapseToolbar.clearMenu();
	}

	@Kroll.method
	public void setOnMenuItemClickListener(KrollFunction function)
	{
		collapseToolbar.setOnMenuItemClickListener(function);
	}
}
