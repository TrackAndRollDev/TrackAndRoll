package com.neos.trackandroll.view.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.neos.trackandroll.R;
import com.neos.trackandroll.model.DataStore;
import com.neos.trackandroll.model.player.Player;
import com.neos.trackandroll.model.sensor.TrackAndRollSensor;
import com.neos.trackandroll.utils.DialogUtils;
import com.neos.trackandroll.utils.FileUtils;
import com.neos.trackandroll.utils.ImageUtils;
import com.neos.trackandroll.utils.LogUtils;
import com.neos.trackandroll.utils.PermissionUtils;
import com.neos.trackandroll.utils.PictureUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomPlayerActivity extends AbstractActivity {

    /**
     * Value of the request code when catch photo
     */
    private static final int REQUEST_CODE_CATCH_PHOTO       = 2;

    /**
     * Value of the request code when pick photo from file
     */
    private static final int REQUEST_CODE_PICK_FROM_FILE    = 3;

    /**
     * The current player
     */
    private Player currentPlayer;

    /**
     * The edit text of the player last name
     */
    private EditText etPlayerLastName;

    /**
     * The edit text of the player first name
     */
    private EditText etPlayerFirstName;

    /**
     * The edit text of the player age
     */
    private EditText etPlayerAge;

    /**
     * The edit text of the player post
     */
    private EditText etPlayerPost;

    /**
     * The edit text of the player number
     */
    private EditText etPlayerNumber;

    /**
     * The text view of the player last name
     */
    private TextView tvPlayerLastName;

    /**
     * The text view of the player first name
     */
    private TextView tvPlayerFirstName;

    /**
     * The button of import picture (take photo from file)
     */
    private View btnImportPicture;

    /**
     * The button of take picture (catch picture)
     */
    private View btnTakePicture;

    /**
     * The button remove picture
     */
    private View btnRemovePicture;

    /**
     * The circle image view of the player if he has a photo
     */
    private CircleImageView icPhoto;

    /**
     * The file path of the photo
     */
    private String currentPhotoPath;

    /**
     * The floating action button to delete the player
     */
    private FloatingActionButton fabDelete;

    /**
     * The floating action button to save the changes
     */
    private FloatingActionButton fabSaveChanges;

    /**
     * The attribute corresponding to this activity
     */
    private AbstractActivity me;

    /**
     * Process called at the creation of the activity
     * @param savedInstanceState : the saved instance state
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        me = this;

        setContentView(R.layout.activity_custom_player);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Get views
        etPlayerLastName = (EditText) findViewById(R.id.etPlayerLastName);
        etPlayerFirstName = (EditText) findViewById(R.id.etPlayerFirstName);
        etPlayerAge = (EditText) findViewById(R.id.etPlayerAge);
        etPlayerPost = (EditText) findViewById(R.id.etPlayerPost);
        etPlayerNumber = (EditText) findViewById(R.id.etPlayerNumber);

        tvPlayerLastName = (TextView) findViewById(R.id.tvAccountLastName);
        tvPlayerFirstName = (TextView) findViewById(R.id.tvAccountFirstName);

        btnImportPicture = findViewById(R.id.btnImportPicture);
        btnTakePicture = findViewById(R.id.btnTakePicture);
        btnRemovePicture = findViewById(R.id.btnRemovePicture);

        icPhoto = (CircleImageView) findViewById(R.id.icMyAccountPhoto);

        fabDelete = (FloatingActionButton) findViewById(R.id.fabDelete);
        fabSaveChanges = (FloatingActionButton) findViewById(R.id.fabSaveChanges);

        // Init model
        currentPhotoPath = "";

        // Get params from activities
        if (mapParams != null && mapParams.get(ServiceActivity.PARAM_PLAYER_KEY) != null) {
            currentPlayer = DataStore.getInstance().getAppConfiguration().getPlayerMap().get(
                    mapParams.get(ServiceActivity.PARAM_PLAYER_KEY));
            initViewFromPlayer();
        }

        // Init the listeners
        initListener();
    }

    /**
     * Process called to init the view from player
     */
    private void initViewFromPlayer() {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.activity_custom_player_title);
        }
        tvPlayerLastName.setText(currentPlayer.getLastName());
        tvPlayerFirstName.setText(currentPlayer.getFirstName());
        currentPhotoPath = currentPlayer.getPathPhoto();
        displayRoundPhoto();
        etPlayerLastName.setText(currentPlayer.getLastName());
        etPlayerFirstName.setText(currentPlayer.getFirstName());
        etPlayerAge.setText(String.valueOf(currentPlayer.getAge()));
        etPlayerPost.setText(currentPlayer.getPostName());
        etPlayerNumber.setText(String.valueOf(currentPlayer.getPlayerNumber()));
    }

    /**
     * Process called when user click on the player's photo
     */
    private View.OnClickListener photoChooser = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            importPicture();
        }
    };

    /**
     * Process called to init the activity listener
     */
    private void initListener() {
        icPhoto.setOnClickListener(photoChooser);
        btnImportPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importPicture();
            }
        });
        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent(REQUEST_CODE_CATCH_PHOTO);
            }
        });
        btnRemovePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPhotoPath = "";
                displayRoundPhoto();
            }
        });
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.displayDialogSureToRemovePlayer(me);
            }
        });
        fabSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processValidEntryUser();
            }
        });
    }

    private void dispatchTakePictureIntent(int actionCode) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, actionCode);
    }

    /**
     * Process called to remove the player of the activity
     */
    @Override
    public void removePlayer() {
        super.manageRemovePlayer(currentPlayer);
        finishWithResult(Integer.valueOf(mapParams.get(ServiceActivity.PARAM_ROOT_SCREEN)), mapParams);
    }

    /**
     * Process called to import picture from file
     */
    private void importPicture() {
        // Check permission
        if (!PermissionUtils.checkStoragePermissions(this)) {
            return;
        }
        // Start activity to choose a file
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_PICK_FROM_FILE);
    }

    /**
     * Process called to manage the player key when a player has been saved
     */
    private void managePlayerKey() {
        HashMap<String, Player> playerMap = DataStore.getInstance().getAppConfiguration().getPlayerMap();
        playerMap.remove(currentPlayer.getPlayerKey());

        // Analyse if a sensor allocation player need to change
        int sensorPositionToChange = -1;
        ArrayList<TrackAndRollSensor> sensorList = DataStore.getInstance().getAppConfiguration().getTrackAndRollSensorList();
        for (int i = 0; i < sensorList.size(); i++) {
            if (sensorList.get(i).getAllocatedPlayerKey().equals(currentPlayer.getPlayerKey())) {
                sensorPositionToChange = i;
            }
        }

        // Update player key
        currentPlayer.setPlayerKey();

        // Update sensor allocated player
        if (sensorPositionToChange > -1) {
            sensorList.get(sensorPositionToChange).setAllocatedPlayerKey(currentPlayer.getPlayerKey());
        }

        // Update player map
        playerMap.put(currentPlayer.getPlayerKey(), currentPlayer);
    }

    /**
     * Process called to valid the entry user.
     */
    private void processValidEntryUser() {
        // TODO improve the message when error entry
        boolean validEntry = true;
        validEntry &= !etPlayerLastName.getText().toString().isEmpty();
        validEntry &= !etPlayerFirstName.getText().toString().isEmpty();
        validEntry &= isIntegerField(etPlayerAge.getText().toString());
        validEntry &= !etPlayerPost.getText().toString().isEmpty();
        validEntry &= isIntegerField(etPlayerNumber.getText().toString());
        validEntry &= currentPhotoPath != null;
        if (validEntry) {
            if (mapParams != null && mapParams.get(ServiceActivity.PARAM_PLAYER_KEY) != null) {
                currentPlayer.setLastName(etPlayerLastName.getText().toString());
                currentPlayer.setFirstName(etPlayerFirstName.getText().toString());
                currentPlayer.setAge(Integer.valueOf(etPlayerAge.getText().toString()));
                currentPlayer.setPostName(etPlayerPost.getText().toString());
                currentPlayer.setPlayerNumber(Integer.valueOf(etPlayerNumber.getText().toString()));
                currentPlayer.setPathPhoto(currentPhotoPath);
                managePlayerKey();
            } else {
                Player newPlayer = new Player(
                        etPlayerLastName.getText().toString(),
                        etPlayerFirstName.getText().toString(),
                        Integer.valueOf(etPlayerAge.getText().toString()),
                        etPlayerPost.getText().toString(),
                        Integer.valueOf(etPlayerNumber.getText().toString()),
                        currentPhotoPath

                );
                DataStore.getInstance().getAppConfiguration().getPlayerMap().put(newPlayer.getPlayerKey(), newPlayer);
                DataStore.getInstance().getPlayerList().add(newPlayer);
            }
            FileUtils.saveAppConfiguration(getApplicationContext());
            onBackPressed();
        }
    }

    /**
     * Process called to know if the field is an integer or not
     * @param field : the field to analyse
     * @return if the field is an integer or not
     */
    private boolean isIntegerField(String field) {
        boolean isIntegerField = true;
        if (field.isEmpty()) {
            isIntegerField = false;
        }
        if (isIntegerField) {
            String regex = "\\d+";
            Pattern pattern = Pattern.compile(regex);
            if (!pattern.matcher(field).matches()) {
                isIntegerField = false;
            }
        }
        return isIntegerField;
    }

    /**
     * Process called to display the round photo if file exist. Else display avatar photo
     */
    public void displayRoundPhoto() {
        if (currentPhotoPath != null && ImageUtils.isFileImage(currentPhotoPath)) {
            ImageUtils.loadPhoto(currentPhotoPath, icPhoto);
        } else {
            icPhoto.setImageResource(R.drawable.ic_avatar);
        }
    }

    /**
     * Process called to take a picture CATCH_PHOTO
     */
    private void takePicture() {
        if (!PermissionUtils.checkStoragePermissions(this)) {
            return;
        }

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            LogUtils.d(LogUtils.DEBUG_TAG, "PackageManager()) != null");
            File photoFile = null;

            try {
                photoFile = PictureUtils.createImageFile();
                currentPhotoPath = photoFile.getPath();
                LogUtils.d(LogUtils.DEBUG_TAG, "URI : " + currentPhotoPath);
            } catch (IOException ex) {
                LogUtils.e(LogUtils.DEBUG_TAG, "File can not be created.", ex);
            }

            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getApplicationContext().getPackageName() + ".provider", photoFile));
                startActivityForResult(takePictureIntent, REQUEST_CODE_CATCH_PHOTO);
            } else {
                LogUtils.e(LogUtils.DEBUG_TAG, "photoFile = null");
            }
        }
    }

    /**
     * Process called when an item of the toolbar has been selected
     * @param item : the item selected
     * @return boolean Return false to allow normal menu processing to
     *          proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Process called when the user press the back button of the android device
     */
    @Override
    public void onBackPressed() {
        if (mapParams != null && mapParams.get(ServiceActivity.PARAM_CHILD_SCREEN) != null) {
            mapParams.put(ServiceActivity.PARAM_PLAYER_KEY, currentPlayer.getPlayerKey());
            finishWithResult(Integer.valueOf(mapParams.get(ServiceActivity.PARAM_CHILD_SCREEN)), mapParams);
        } else {
            finishWithResult(ServiceActivity.DISPLAY_ACTIVITY_PLAYERS);
        }
    }

    private void handleCameraPhoto(Intent intent) {
        Bundle extras = intent.getExtras();
        Bitmap mImageBitmap = (Bitmap) extras.get("data");
        // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
        Uri tempUri = getImageUri(getApplicationContext(), mImageBitmap);

        // CALL THIS METHOD TO GET THE ACTUAL PATH
        File finalFile = new File(getRealPathFromURI(tempUri));

        LogUtils.d(LogUtils.DEBUG_TAG,"file path => " + finalFile.getPath());
        currentPhotoPath = finalFile.getPath();
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        //inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    /**
     * Process called when the activity is reopen after an other destruction
     * @param requestCode : the request code of the destroyed activity
     * @param resultCode : the result code of the destroyed activity
     * @param data : the data of the destroyed activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CATCH_PHOTO && resultCode == RESULT_OK) {
            handleCameraPhoto(data);
            displayRoundPhoto();
        }

        if (requestCode == REQUEST_CODE_PICK_FROM_FILE && resultCode == RESULT_OK) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            currentPhotoPath = cursor.getString(columnIndex);
            cursor.close();

            displayRoundPhoto();
        }
    }
}
