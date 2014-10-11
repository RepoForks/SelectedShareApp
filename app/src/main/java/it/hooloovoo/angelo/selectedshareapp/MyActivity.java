package it.hooloovoo.angelo.selectedshareapp;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.util.List;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {

        Button w, f, m; //three button

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_my, container, false);

            w = (Button) rootView.findViewById(R.id.wbutton);
            f = (Button) rootView.findViewById(R.id.fbutton);
            m = (Button) rootView.findViewById(R.id.mbutton);
            w.setOnClickListener(this);
            f.setOnClickListener(this);
            m.setOnClickListener(this);
            return rootView;
        }

        @Override
        public void onClick(View v) {

            if(R.id.wbutton == v.getId()){
                onClickWhatsApp();
            }else if(R.id.fbutton == v.getId()){
                onClickFacebook();
            }else{
                onCLickMail();
            }
        }

        private void onCLickMail() {
            if(!initShareIntent("gmail")) Toast.makeText(getActivity(), "Mail App not Installed", Toast.LENGTH_SHORT)
                    .show();
        }

        private void onClickFacebook() {
            if(!initShareIntent("facebook")) Toast.makeText(getActivity(), "Facebook not Installed", Toast.LENGTH_SHORT)
                    .show();
        }


        public void onClickWhatsApp() {

            /*
            PackageManager pm= getActivity().getPackageManager();
            try {

                Intent waIntent = new Intent(Intent.ACTION_SEND);
                waIntent.setType("text/plain");
                String text = "YOUR TEXT HERE";

                PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                //Check if package exists or not. If not then code
                //in catch block will be called
                waIntent.setPackage("com.whatsapp");

                waIntent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(waIntent, "Share with"));

            } catch (PackageManager.NameNotFoundException e) {
                Toast.makeText(getActivity(), "WhatsApp not Installed", Toast.LENGTH_SHORT)
                        .show();
            }*/

            if(!initShareIntent("whats")) Toast.makeText(getActivity(), "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();


        }


        private boolean initShareIntent(String type) {
            boolean found = false;
            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            share.setType("text/plain");

            // gets the list of intents that can be loaded.
            List<ResolveInfo> resInfo = getActivity().getPackageManager().queryIntentActivities(share, 0);
            if (!resInfo.isEmpty()){
                for (ResolveInfo info : resInfo) {
                    if (info.activityInfo.packageName.toLowerCase().contains(type) ||
                            info.activityInfo.name.toLowerCase().contains(type) ) {
                        share.putExtra(Intent.EXTRA_SUBJECT,  "subject");
                        share.putExtra(Intent.EXTRA_TEXT,     "your text");
                        share.setPackage(info.activityInfo.packageName);
                        found = true;
                        break;
                    }
                }
                if (!found)
                    return found;

                startActivity(Intent.createChooser(share, "Select"));

            }
            return true;
        }
    }
}
