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

package org.michaelevans.etsyblur.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import org.michaelevans.etsyblur.utils.Constants;
import org.michaelevans.etsyblur.R;
import org.michaelevans.etsyblur.utils.Utils;

public class DetailsActivity extends Activity {
    private String mBackgroundFilename;
    private View mBackgroundContainer;
    private ImageView mBlurImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        mBackgroundContainer = findViewById(R.id.container);
        mBlurImage = (ImageView) findViewById(R.id.blur_image);
        View mFigure = findViewById(R.id.figure);

        setBlurBackground();

        mFigure.setBackgroundResource(getIntent().getIntExtra(Constants.DROID_ID, R.drawable.chocolate));
    }

    private void setBlurBackground() {
        mBackgroundFilename = getIntent().getStringExtra(Constants.BLUR_FILENAME);
        if(!TextUtils.isEmpty(mBackgroundFilename)){
            mBackgroundContainer.setVisibility(View.VISIBLE);
            Bitmap background = Utils.loadBitmapFromFile(mBackgroundFilename);
            if (background != null) {
                mBlurImage.setImageBitmap(background);
                mBlurImage.animate().alpha(1).setDuration(1000);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cleanupBlurBackground();
    }

    private void cleanupBlurBackground() {
        if(!TextUtils.isEmpty(mBackgroundFilename)){
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    Utils.deleteFile(mBackgroundFilename);
                    return null;
                }
            }.execute();
        }
    }
}
