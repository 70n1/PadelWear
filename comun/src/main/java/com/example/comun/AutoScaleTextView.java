package com.example.comun;

import android.content.Context;
import android.graphics.Paint;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * A custom Text View that lowers the text size when the text is to big for the TextView.
 * Modified version of one found on stackoverflow
 *
 * @author Andreas Krings - www.ankri.de
 * @version 1.0
 *
 */

public class AutoScaleTextView extends TextView {
	private Paint textPaint;

	private float preferredTextSize;
	private float minTextSize;

	public AutoScaleTextView(Context context) {
		this(context, null);
	}

	public AutoScaleTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.textPaint = new Paint();
		this.minTextSize = 2;
		this.preferredTextSize = 2000;
	}

	/**
	 * Set the minimum text size for this view
	 * 
	 * @param minTextSize
	 *            The minimum text size
	 */
	public void setMinTextSize(float minTextSize) {
		this.minTextSize = minTextSize;
	}

	/**
	 * Resize the text so that it fits
	 * 
	 * @param text
	 *            The text. Neither <code>null</code> nor empty.
	 * @param textWidth
	 *            The width of the TextView. > 0
	 */
	private void refitText(String text, int textWidth) {
		this.minTextSize = 2;
		this.preferredTextSize = 2000;
		if (textWidth <= 0 || text == null || text.length() == 0)
			return;

		// the width
		int targetWidth = textWidth - this.getPaddingLeft()
				- this.getPaddingRight();
		int targetHeight = this.getHeight() - this.getPaddingTop()
				- this.getPaddingBottom();

		final float threshold = 0.5f; // How close we have to be

		this.textPaint.set(this.getPaint());

		while ((this.preferredTextSize - this.minTextSize) > threshold) {
			float size = (this.preferredTextSize + this.minTextSize) / 2;
			this.textPaint.setTextSize(size);
			int textHeight = getTextHeight(text, this.getPaint(), targetWidth,
					size);

			if (this.textPaint.measureText(text) >= targetWidth
					|| textHeight >= targetHeight) {
				this.preferredTextSize = size; // too big
			} else {
				this.minTextSize = size; // too small
			}
		}
		// Use min size so that we undershoot rather than overshoot
		this.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.minTextSize);
	}

	@Override
	protected void onTextChanged(final CharSequence text, final int start,
			final int before, final int after) {
		this.refitText(text.toString(), this.getWidth());
	}

	@Override
	protected void onSizeChanged(int width, int height, int oldwidth,
			int oldheight) {
		if (width != oldwidth || height != oldheight)
			this.refitText(this.getText().toString(), width);
	}

	// Set the text size of the text paint object and use a static layout to
	// render text off screen before measuring
	private int getTextHeight(CharSequence source, TextPaint originalPaint,
			int width, float textSize) {
		// Update the text paint object
		// modified: make a copy of the original TextPaint object for measuring
		// (apparently the object gets modified while measuring, see also the
		// docs for TextView.getPaint() (which states to access it read-only)
		TextPaint paint = new TextPaint(originalPaint);
		// Update the text paint object
		paint.setTextSize(textSize);
		// Measure using a static layout
		StaticLayout layout = new StaticLayout(source, paint, width,
				Alignment.ALIGN_CENTER, 1.0f, 0.0f, true);
		return layout.getHeight();
	}
}