package com.neos.trackandroll.view.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.neos.trackandroll.R;
import com.neos.trackandroll.model.AppConfiguration;
import com.neos.trackandroll.model.DataStore;
import com.neos.trackandroll.utils.ImageUtils;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public abstract class AbstractActivityWithToolbar extends AbstractActivity {

    protected DrawerLayout drawerLayout;
    protected NavigationView nvMainActivity;
    protected Toolbar toolbar;
    protected ActionBarDrawerToggle mDrawerToggle;
    protected RelativeLayout rlActivityContainer;
    protected TextView tvHeaderName;
    protected TextView tvHeaderEmail;
    protected CircleImageView icHeaderPhoto;
    protected String currentPathPhoto;

    /**
     * Process called when the activity is created
     * @param savedInstanceState : the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set Layout
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.dlMainActivity);
        nvMainActivity = (NavigationView) findViewById(R.id.nvMainActivity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rlActivityContainer = (RelativeLayout) findViewById(R.id.rlActivityContainer);

        updateHeaderView();
        setUpActionBar();
        setUpNavigationView();
        initListener();
    }

    /**
     * Process called to inflate the layout of the activity that have a drawer layout
     * @param menuSelected : the menu selected on the navigation view
     * @param layoutId : the layout id of the activity
     * @param layout : the layout of the activity
     */
    protected void inflateLayout(int menuSelected, int layoutId, ViewGroup layout) {
        nvMainActivity.getMenu().getItem(menuSelected).setChecked(true);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View childLayout = inflater.inflate(layoutId, layout);
        rlActivityContainer.addView(childLayout);
        childLayout.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT)
        );
    }

    /**
     * Process called to update the header informations (names, e-mail, photo)
     */
    protected void updateHeaderView(){
        tvHeaderName = (TextView) nvMainActivity.getHeaderView(0).findViewById(R.id.tvHeaderName);
        tvHeaderName.setText(DataStore.getInstance().getAppConfiguration().getAccount().getFirstName() + " " + DataStore.getInstance().getAppConfiguration().getAccount().getLastName());
        tvHeaderEmail = (TextView) nvMainActivity.getHeaderView(0).findViewById(R.id.tvHeaderEmail);
        tvHeaderEmail.setText(DataStore.getInstance().getAppConfiguration().getAccount().getEmail());

        icHeaderPhoto = (CircleImageView) nvMainActivity.getHeaderView(0).findViewById(R.id.icHeaderPhoto);
        currentPathPhoto = DataStore.getInstance().getAppConfiguration().getAccount().getPathPhoto();
        if (currentPathPhoto != null && ImageUtils.isFileImage(currentPathPhoto)) {
            ImageUtils.loadPhoto(currentPathPhoto, icHeaderPhoto);
        } else {
            icHeaderPhoto.setImageResource(R.drawable.ic_avatar);
        }
    }


    /**
     * Process called to set up the action bar
     */
    private void setUpActionBar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                    R.string.activity_login_subtitle, R.string.login_message_error) {

                public void onDrawerClosed(View view) {
                    supportInvalidateOptionsMenu();
                    drawerOpen = false;
                }

                public void onDrawerOpened(View drawerView) {
                    supportInvalidateOptionsMenu();
                    drawerOpen = true;
                }
            };
            mDrawerToggle.setDrawerIndicatorEnabled(true);
            drawerLayout.setDrawerListener(mDrawerToggle);
            mDrawerToggle.syncState();
        }
    }

    /**
     * Process called to init the listeners of the activity
     */
    private void initListener() {
        nvMainActivity.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer();
                finishWithResult(ServiceActivity.DISPLAY_ACTIVITY_MY_ACCOUNT, new HashMap<String, String>());
            }
        });
    }


    /**
     * Process called to create the navigation menu view
     */
    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        nvMainActivity.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                // Check the item
                uncheckAllItems();
                menuItem.setChecked(true);

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.action_players:
                        closeDrawer();
                        finishWithResult(ServiceActivity.DISPLAY_ACTIVITY_PLAYERS);
                        break;

                    case R.id.action_session:
                        closeDrawer();
                        finishWithResult(ServiceActivity.DISPLAY_ACTIVITY_SESSIONS_MANAGER);
                        break;

                    case R.id.action_sensors:
                        closeDrawer();
                        finishWithResult(ServiceActivity.DISPLAY_ACTIVITY_SENSORS_MANAGER);
                        break;

                    case R.id.action_disconnect:
                        closeDrawer();
                        finishWithResult(ServiceActivity.DISPLAY_ACTIVITY_LOGIN);
                        break;

                    case R.id.action_help:
                        finishWithResult(ServiceActivity.DISPLAY_ACTIVITY_HELP);
                        break;
                }
                return true;
            }
        });
    }

    /**
     * Process called to uncheck all items of the navigation view
     */
    private void uncheckAllItems() {
        for (int i = 0; i < nvMainActivity.getMenu().size(); i++) {
            nvMainActivity.getMenu().getItem(i).setChecked(false);
        }
    }

    /**
     * Process called when the menu is created on the toolbar
     * @param menu : the toolbar menu
     * @return : true to display the view
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_empty_toolbar, menu);
        return true;
    }

    /**
     * Process called to close the drawer and
     */
    @Override
    protected void closeDrawer() {
        drawerLayout.closeDrawer(Gravity.START);
        drawerOpen = false;
    }
}
