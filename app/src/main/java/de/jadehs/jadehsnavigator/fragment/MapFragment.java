/**
 * This file is part of JadeHS-Navigator.
 *
 * JadeHS-Navigator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * JadeHS-Navigator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with JadeHS-Navigator.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.jadehs.jadehsnavigator.fragment;

import android.app.Fragment;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

import de.jadehs.jadehsnavigator.R;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;

/**
 * Created by re1015 on 18.08.2015.
 */
public class MapFragment extends Fragment {
    final String TAG = "MAPFRAGMENT";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        try {
            final ImageViewTouch mapImage = (ImageViewTouch) getActivity().findViewById(R.id.map);

            Matrix matrix = new Matrix();
            // Set start zoom state
            matrix.postScale((float)2,(float)2);

            //@todo: Basierend auf preferences (location) korrekten plan anzeigen (und in besserer quali)
            Toast.makeText(getActivity(),"Pläne für Oldenburg und Elsfleth sind in Arbeit und werden nachgereicht",Toast.LENGTH_LONG).show();
            Bitmap bitmap = getBitmapFromAsset("images/plan_whv.png");
            mapImage.setImageBitmap(bitmap, matrix, 1, 3);
        }catch (Exception ex){
            Log.wtf(TAG, "FAILED TO LOAD IMAGE", ex);
        }
    }

    public Bitmap getBitmapFromAsset(String filePath){
        AssetManager assetManager = getActivity().getAssets();

        InputStream istr;
        Bitmap bitmap = null;
        try{
            istr = assetManager.open(filePath);
            // Converts source file into bitmap file
            bitmap = BitmapFactory.decodeStream(istr);
        }catch (IOException ex){
            Log.wtf(TAG, "FAILED TO CREATE BITMAP", ex);
        }
        return bitmap;
    }
}
