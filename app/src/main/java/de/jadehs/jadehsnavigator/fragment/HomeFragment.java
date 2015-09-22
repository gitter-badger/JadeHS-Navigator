package de.jadehs.jadehsnavigator.fragment;
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
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import de.jadehs.jadehsnavigator.R;

public class HomeFragment extends Fragment{
    private static final String TAG = "HOMEFRAGMENT";
	
	public HomeFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        /* Sets up all buttons with the correct URLs */
        getActivity().findViewById(R.id.btnHomepage).setOnClickListener(new ButtonOnClickListener(getString(R.string.extHomepage)));
        getActivity().findViewById(R.id.btnMoodle).setOnClickListener(new ButtonOnClickListener(getString(R.string.extMoodle)));
        getActivity().findViewById(R.id.btnECampus).setOnClickListener(new ButtonOnClickListener(getString(R.string.extECampus)));
        getActivity().findViewById(R.id.btnOWA).setOnClickListener(new ButtonOnClickListener(getString(R.string.extOWA)));
        getActivity().findViewById(R.id.btnGuide).setOnClickListener(new ButtonOnClickListener(getString(R.string.extGuide)));
        getActivity().findViewById(R.id.btnOwncloud).setOnClickListener(new ButtonOnClickListener(getString(R.string.extOwncloud)));
    }

    private class ButtonOnClickListener implements View.OnClickListener {
        String url;
        public ButtonOnClickListener(String url){
            this.url = url;
        }

        @Override
        public void onClick(View v) {
            try{
                final String url = this.url;
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity().getApplicationContext());
                    builder.setMessage(getActivity().getString(R.string.dialog_open_website))
                            .setCancelable(true)
                            .setPositiveButton(getActivity().getString(R.string.positive),
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(final DialogInterface dialog, final int id) {
                                            try {
                                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                                intent.setData(Uri.parse(url));
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                                getActivity().startActivity(intent);
                                            } catch (Exception ex) {
                                                Log.wtf(TAG, "Failed to open website", ex);
                                            }
                                        }
                                    })
                            .setNegativeButton(getActivity().getString(R.string.negative),
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(
                                                final DialogInterface dialog,
                                                final int id) {
                                            dialog.dismiss();
                                        }
                                    });
                    final AlertDialog alert = builder.create();
                    alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                    alert.show();
                }else{
                    Toast.makeText(getActivity(), getActivity().getString(R.string.failed_to_open_website), Toast.LENGTH_LONG).show();
                }
            }catch (Exception ex){
                Log.wtf(TAG, "Failed to create OnClickListener", ex);
            }
        }
    }
}
