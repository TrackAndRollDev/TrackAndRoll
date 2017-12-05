package com.neos.trackandroll.view.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.neos.trackandroll.R;
import com.neos.trackandroll.model.DataStore;
import com.neos.trackandroll.model.account.Account;
import com.neos.trackandroll.utils.DialogUtils;
import com.neos.trackandroll.utils.FileUtils;
import com.neos.trackandroll.utils.ImageUtils;
import com.neos.trackandroll.utils.LogUtils;
import com.neos.trackandroll.utils.PermissionUtils;
import com.neos.trackandroll.utils.PictureUtils;
import com.neos.trackandroll.utils.ViewUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;


public class MyAccountActivity extends AbstractActivityWithToolbar {

    private ImageView imgArrowTitle1;
    private ImageView imgArrowTitle2;

    private View layoutPersonalInformationsTitle;
    private View layoutPersonalInformationsEdit;
    private View layoutPasswordTitle;
    private View layoutPasswordEdit;

    private boolean viewVisible1;
    private boolean viewVisible2;

    private static final int REQUEST_CODE_CATCH_PHOTO = 2;
    private static final int REQUEST_CODE_PICK_FROM_FILE = 3;

    private Account account;
    private EditText etAccountLastName;
    private EditText etAccountFirstName;
    private EditText etAccountEmail;
    private EditText etAccountUsername;
    private EditText etAccountActualPassword;
    private EditText etAccountNewPassword;
    private EditText etAccountConfirmationPassword;
    private TextView tvAccountLastName;
    private TextView tvAccountFirstName;

    private View btnImportPicture;
    private View btnTakePicture;
    private View btnRemovePicture;

    private CircleImageView icPhoto;

    private String currentPhotoPath;

    private FloatingActionButton fabSaveChanges;

    private AbstractActivityWithToolbar me;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        me = this;

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View childLayout = inflater.inflate(R.layout.activity_my_account, (ViewGroup) findViewById(R.id.activity_my_account));
        rlActivityContainer.addView(childLayout);

        layoutPersonalInformationsTitle = findViewById(R.id.layoutPersonalInformationsTitle);
        layoutPersonalInformationsEdit = findViewById(R.id.layoutPersonalInformationsEdit);
        layoutPasswordTitle = findViewById(R.id.layoutPasswordTitle);
        layoutPasswordEdit = findViewById(R.id.layoutPasswordEdit);
        imgArrowTitle1 = (ImageView) findViewById(R.id.imgArrowTitle1);
        imgArrowTitle2 = (ImageView) findViewById(R.id.imgArrowTitle2);

        etAccountLastName = (EditText) findViewById(R.id.etAccountLastName);
        etAccountFirstName = (EditText) findViewById(R.id.etAccountFirstName);
        etAccountEmail = (EditText) findViewById(R.id.etAccountEmail);
        etAccountUsername = (EditText) findViewById(R.id.etAccountUsername);
        etAccountActualPassword = (EditText) findViewById(R.id.etAccountActualPassword);
        etAccountNewPassword = (EditText) findViewById(R.id.etAccountNewPassword);
        etAccountConfirmationPassword = (EditText) findViewById(R.id.etAccountConfirmationPassword);

        tvAccountLastName = (TextView) findViewById(R.id.tvAccountLastName);
        tvAccountFirstName = (TextView) findViewById(R.id.tvAccountFirstName);

        btnImportPicture = findViewById(R.id.btnImportPicture);
        btnTakePicture = findViewById(R.id.btnTakePicture);
        btnRemovePicture = findViewById(R.id.btnRemovePicture);

        icPhoto = (CircleImageView) findViewById(R.id.icAccountPhoto);

        fabSaveChanges = (FloatingActionButton) findViewById(R.id.fabSaveChanges);

        initModel();
        updateView();
        initListener();
    }

    private void initModel() {
        account = DataStore.getInstance().getAppConfiguration().getAccount();
    }

    private void updateView() {
        if (viewVisible1) {
            ViewUtils.makeRotate90Anticlockwise(getApplicationContext(), imgArrowTitle1);
        }
        if (viewVisible2) {
            ViewUtils.makeRotate90Anticlockwise(getApplicationContext(), imgArrowTitle2);
        }
        layoutPersonalInformationsEdit.setVisibility(View.GONE);
        viewVisible1 = false;
        layoutPasswordEdit.setVisibility(View.GONE);
        viewVisible2 = false;

        etAccountLastName.setText(account.getLastName());
        etAccountFirstName.setText(account.getFirstName());
        etAccountEmail.setText(account.getEmail());
        etAccountUsername.setText(account.getUsername());

        tvAccountLastName.setText(account.getLastName());
        tvAccountFirstName.setText(account.getFirstName());
        currentPhotoPath = account.getPathPhoto();
        displayRoundPhoto();

        super.updateHeaderView();
    }

    private View.OnClickListener photoChooser = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            importPicture();
        }
    };

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

        fabSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processValidEntryUser();
            }
        });

        layoutPersonalInformationsTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!viewVisible1) {
                    ViewUtils.makeLayoutVisible(getApplicationContext(), layoutPersonalInformationsEdit);
                    viewVisible1 = true;
                    ViewUtils.makeRotate90Clockwise(getApplicationContext(), imgArrowTitle1);
                } else {
                    ViewUtils.makeLayoutGone(getApplicationContext(), layoutPersonalInformationsEdit);
                    viewVisible1 = false;
                    ViewUtils.makeRotate90Anticlockwise(getApplicationContext(), imgArrowTitle1);
                }
            }
        });

        layoutPasswordTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!viewVisible2) {
                    ViewUtils.makeLayoutVisible(getApplicationContext(), layoutPasswordEdit);
                    viewVisible2 = true;
                    ViewUtils.makeRotate90Clockwise(getApplicationContext(), imgArrowTitle2);
                } else {
                    ViewUtils.makeLayoutGone(getApplicationContext(), layoutPasswordEdit);
                    viewVisible2 = false;
                    ViewUtils.makeRotate90Anticlockwise(getApplicationContext(), imgArrowTitle2);
                }
            }
        });

    }

    private void processValidEntryUser() {
        boolean validEntry = true;

        if (viewVisible1 && viewVisible2) {
            validEntry &= !etAccountLastName.getText().toString().isEmpty();
            validEntry &= !etAccountFirstName.getText().toString().isEmpty();
            validEntry &= !etAccountEmail.getText().toString().isEmpty();
            validEntry &= !etAccountUsername.getText().toString().isEmpty();
            validEntry &= !etAccountActualPassword.getText().toString().isEmpty();
            validEntry &= !etAccountNewPassword.getText().toString().isEmpty();
            validEntry &= !etAccountConfirmationPassword.getText().toString().isEmpty();
        } else if (viewVisible1) {
            validEntry &= !etAccountLastName.getText().toString().isEmpty();
            validEntry &= !etAccountFirstName.getText().toString().isEmpty();
            validEntry &= !etAccountEmail.getText().toString().isEmpty();
            validEntry &= !etAccountUsername.getText().toString().isEmpty();
        } else if (viewVisible2) {
            validEntry &= !etAccountActualPassword.getText().toString().isEmpty();
            validEntry &= !etAccountNewPassword.getText().toString().isEmpty();
            validEntry &= !etAccountConfirmationPassword.getText().toString().isEmpty();
        }


        if (validEntry) {
            account.setLastName(etAccountLastName.getText().toString());
            account.setFirstName(etAccountFirstName.getText().toString());
            account.setEmail(etAccountEmail.getText().toString());
            account.setUsername(etAccountUsername.getText().toString());
            account.setPassword(etAccountConfirmationPassword.getText().toString());
            account.setPathPhoto(currentPhotoPath);
            FileUtils.saveAppConfiguration(getApplicationContext());
            updateView();
        }

    }

    private void dispatchTakePictureIntent(int actionCode) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, actionCode);
    }

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

    private void takePicture() {
        if (!PermissionUtils.checkStoragePermissions(this)) {
            return;
        }

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_CODE_CATCH_PHOTO);
            } else {
                LogUtils.e(LogUtils.DEBUG_TAG, "photoFile = null");
            }
        }
    }

    public void displayRoundPhoto() {
        if (currentPhotoPath != null && ImageUtils.isFileImage(currentPhotoPath)) {
            ImageUtils.loadPhoto(currentPhotoPath, icPhoto);
        } else {
            icPhoto.setImageResource(R.drawable.ic_avatar);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void handleCameraPhoto(Intent intent) {
        Bundle extras = intent.getExtras();
        Bitmap mImageBitmap = (Bitmap) extras.get("data");
        // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
        Uri tempUri = getImageUri(getApplicationContext(), mImageBitmap);

        // CALL THIS METHOD TO GET THE ACTUAL PATH
        File finalFile = new File(getRealPathFromURI(tempUri));

        LogUtils.d(LogUtils.DEBUG_TAG, "file path => " + finalFile.getPath());
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