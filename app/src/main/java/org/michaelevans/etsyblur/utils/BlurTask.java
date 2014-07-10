/*
 * Copyright 2014 Michael Evans <michaelcevans10@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.michaelevans.etsyblur.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;

public class BlurTask extends AsyncTask<Void, Void, Void> {

    private Activity mContext;
    private int mBlurLayoutId;
    private Bitmap mDownScaled;
    private Intent mIntent;

    public BlurTask(Activity activity, Intent intent, int layoutId) {
        mContext = activity;
        mIntent = intent;
        mBlurLayoutId = layoutId;
    }

    private View getViewFrame() {
        return mContext.findViewById(this.mBlurLayoutId);
    }

    @Override
    protected Void doInBackground(Void... params) {
        mIntent.putExtra(Constants.BLUR_FILENAME, getBlurredBackgroundFilename());
        return null;
    }

    @Override
    protected void onPreExecute() {
        this.mDownScaled = Utils.drawViewToBitmap(getViewFrame(), Color.parseColor("#fff5f5f5"));
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        mContext.startActivity(mIntent);
    }

    private String getBlurredBackgroundFilename()
    {
        Bitmap localBitmap = Blur.fastblur(this.mContext, this.mDownScaled, 8);
        String str = Utils.saveBitmapToFile(this.mContext, localBitmap);
        this.mDownScaled.recycle();
        localBitmap.recycle();
        return str;
    }
}
