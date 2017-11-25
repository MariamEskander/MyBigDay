package com.android.mybigday.ui.fragment.firstFragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.mybigday.R;

import com.android.mybigday.data.database.MyBigDaySharedPreference;
import com.android.mybigday.data.model.Plan;
import com.android.mybigday.data.model.TipsListView;
import com.android.mybigday.data.model.ToDoListView;
import com.android.mybigday.ui.DatePickerFragment;
import com.android.mybigday.ui.fragment.firstFragment.presenter.FirstFragmentPresenter;
import com.android.mybigday.util.GlobalVariables;
import com.android.mybigday.util.Log;
import com.android.mybigday.util.RealPathUtil;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

import butterknife.BindView;
import butterknife.ButterKnife;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusSupportFragment;
import pl.droidsonroids.gif.GifImageView;

@RequiresPresenter(FirstFragmentPresenter.class)
public class FirstFragment  extends NucleusSupportFragment<FirstFragmentPresenter> {

    private static final int REQUEST_CODE = 1234;
    @BindView(R.id.change_date)
    LinearLayout change_date;

    @BindView(R.id.days)
    TextView days;

    @BindView(R.id.hours)
    TextView hours;

    @BindView(R.id.mins)
    TextView mins;

    @BindView(R.id.secs)
    TextView secs;

    @BindView(R.id.add_cover)
    TextView add_cover;

    @BindView(R.id.plans_view)
    RecyclerView plans_view;

    @BindView(R.id.tips_view)
    RecyclerView tips_view;

    @BindView(R.id.todos_view)
    RecyclerView todos_view;

    @BindView(R.id.add)
    RelativeLayout add_plan;

    @BindView(R.id.retry_todos)
    TextView retry_todos;

    @BindView(R.id.retry_tips)
    TextView retry_tips;

    @BindView(R.id.cover_photo)
    ImageView cover_photo;

    @BindView(R.id.loading_tips)
    GifImageView loading_tips;

    @BindView(R.id.loading_todos)
    GifImageView loading_todos;

    Calendar calendar;
    private int year, month, day;
    private TodosAdapter todosAdapter;
    private TipsAdapter tipsAdapter;

    private Uri selectedImageUri;

    private String realPath;


    private static final int REQUEST_CAMERA = 30;
    private static final int REQUEST_GALLERY = 40;
    private String mCurrentPhotoPath;
    private PlansAdapter plansAdapter;


    // newInstance constructor for creating fragment with arguments
    public static FirstFragment newInstance(int page, String title) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.first_fragment, container, false);
        ButterKnife.bind(this, view);

        GlobalVariables.lc = new ReentrantLock();


        tipsAdapter = new TipsAdapter(this, new ArrayList<TipsListView>());
        tips_view.setAdapter(tipsAdapter);

        todosAdapter = new TodosAdapter(this, new ArrayList<ToDoListView>());
        todos_view.setAdapter(todosAdapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getActivity()); // (Context context, int spanCount)
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);

        tips_view.setLayoutManager(mLinearLayoutManagerVertical);
        tips_view.setItemAnimator(new DefaultItemAnimator());

        LinearLayoutManager lLinearLayoutManagerVertical = new LinearLayoutManager(getActivity()); // (Context context, int spanCount)
        lLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);

        todos_view.setLayoutManager(lLinearLayoutManagerVertical);
        todos_view.setItemAnimator(new DefaultItemAnimator());


        plansAdapter = new PlansAdapter(this, new ArrayList<Plan>());
        plans_view.setAdapter(plansAdapter);
        LinearLayoutManager xLinearLayoutManagerVertical = new LinearLayoutManager(getActivity()); // (Context context, int spanCount)
        xLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.HORIZONTAL);
        plans_view.setLayoutManager(xLinearLayoutManagerVertical);
        plans_view.setItemAnimator(new DefaultItemAnimator());


        add_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().toolBarInterface.screenNumber(2);
            }
        });

        add_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooserDialogs();
            }
        });

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        change_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getActivity().getFragmentManager(), "Picker");

            }
        });
        return view;
    }


    public void updateView(FirstFragmentViewModel viewModel) {
        days.setText(viewModel.getDays());
        hours.setText(viewModel.getHours());
        secs.setText(viewModel.getSecs());
        mins.setText(viewModel.getMins());

        if (viewModel.isLoadTodos()) {
            todos_view.setVisibility(View.GONE);
            retry_todos.setVisibility(View.GONE);
            loading_todos.setVisibility(View.VISIBLE);
        } else {
            if (!viewModel.getErrorTodos().equals("")) {
                retry_todos.setVisibility(View.VISIBLE);
                todos_view.setVisibility(View.GONE);
                loading_todos.setVisibility(View.GONE);
                retry_todos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getPresenter().getTodos();
                        retry_todos.setVisibility(View.GONE);
                        todos_view.setVisibility(View.GONE);
                        loading_todos.setVisibility(View.VISIBLE);
                        getPresenter().setLoadTodos(true);
                    }
                });

            } else {
                retry_todos.setVisibility(View.GONE);
                todos_view.setVisibility(View.VISIBLE);
                loading_todos.setVisibility(View.GONE);
                setTodos(viewModel.getToDoListView());

            }
        }

        if (viewModel.isLoadTips()) {
            tips_view.setVisibility(View.GONE);
            retry_tips.setVisibility(View.GONE);
            loading_tips.setVisibility(View.VISIBLE);
        } else {
            if (!viewModel.getErrorTips().equals("")) {
                retry_tips.setVisibility(View.VISIBLE);
                tips_view.setVisibility(View.GONE);
                loading_tips.setVisibility(View.GONE);
                retry_tips.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getPresenter().getTips();
                        retry_tips.setVisibility(View.GONE);
                        tips_view.setVisibility(View.GONE);
                        loading_tips.setVisibility(View.VISIBLE);
                        getPresenter().setLoadTips(true);
                    }
                });

            } else {
                retry_tips.setVisibility(View.GONE);
                tips_view.setVisibility(View.VISIBLE);
                loading_tips.setVisibility(View.GONE);
                setTips(viewModel.getTipsListView());

            }
        }

        if (!viewModel.getCoverPhoto().equals("")) {
            setCover(viewModel.getCoverPhoto());
        }

        if (viewModel.isNotifyPlansChanged()) {
            setPlans(viewModel.getPlans());
            getPresenter().setNotifyPlansChanged(false);
        }
    }

    private void setPlans(ArrayList<Plan> plans) {
        plansAdapter.resetAdapter();
        plansAdapter.addAll(plans);
    }

    private void setCover(String coverPhoto) {
        File imgFile = new File(coverPhoto);
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            cover_photo.setImageBitmap(myBitmap);

        }
    }

    private void setTips(ArrayList<TipsListView> tipsListView) {
        tipsAdapter.resetAdapter();
        tipsAdapter.addAll(tipsListView);


    }

    private void setTodos(ArrayList<ToDoListView> toDoListView) {
        todosAdapter.resetAdapter();
        todosAdapter.addAll(toDoListView);
    }


    private void openFileChooserDialogs() {
        AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
        builder2.setCancelable(true);
        builder2.setTitle("Add Picture");
        builder2.setItems(R.array.items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        askForPermission(Manifest.permission.CAMERA, REQUEST_CAMERA);
                        break;
                    case 1:
                        askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE, REQUEST_GALLERY);
                        break;
                    default:
                        break;
                }
            }
        });
        builder2.show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.e("Permission", "here ");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(getActivity(), permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case REQUEST_CAMERA:

                    if (Build.VERSION.SDK_INT > 21)
                        dispatchTakePictureIntent();
                    else
                        initCameraIntent();

                    break;

                case REQUEST_GALLERY:


                    initGalleryIntent();
                    break;
            }
        } else {
            Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
        }

    }

    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{permission}, requestCode);
        } else {
            //  Toast.makeText(getActivity(), "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
            switch (requestCode) {
                case REQUEST_CAMERA:

                    if (Build.VERSION.SDK_INT > 21)
                        dispatchTakePictureIntent();
                    else
                        initCameraIntent();


                    break;

                case REQUEST_GALLERY:


                    initGalleryIntent();
                    break;
            }
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.android.mybigday.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CAMERA);
            }
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }

    private Bitmap setPic() {
        // Get the dimensions of the View
        int targetW = 400;
        int targetH = 400;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        Log.i("bitmap1", String.valueOf(bitmap));

        MyBigDaySharedPreference sharedPreference = new MyBigDaySharedPreference(getActivity());
        sharedPreference.saveCover(mCurrentPhotoPath);
        return bitmap;

    }

    private void initCameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = getOutputMediafile(1);
        selectedImageUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, selectedImageUri);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CAMERA);
        }
    }

    private void initGalleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_GALLERY);
    }

    private File getOutputMediafile(int type) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), getResources().getString(R.string.app_name)
        );
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyHHdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == 1) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".png");
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    @SuppressLint("NewApi")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                if (Build.VERSION.SDK_INT > 21) {
                    galleryAddPic();
                    setPic();
                } else {
                    realPath = selectedImageUri.getPath();

                    MyBigDaySharedPreference sharedPreference = new MyBigDaySharedPreference(getActivity());
                    sharedPreference.saveCover(realPath);
                }

            } else if (requestCode == REQUEST_GALLERY) {
                selectedImageUri = data.getData();
                if (Build.VERSION.SDK_INT < 11) {
                    realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(getActivity(), data.getData());
                } else if (Build.VERSION.SDK_INT < 19) {
                    realPath = RealPathUtil.getRealPathFromURI_API11to18(getActivity(), data.getData());
                } else {
                    realPath = RealPathUtil.getRealPathFromURI_API19(getActivity(), data.getData());
                }


                MyBigDaySharedPreference sharedPreference = new MyBigDaySharedPreference(getActivity());
                sharedPreference.saveCover(realPath);
            }

        }
    }


    public long calculateTime(String format, String date) {
        Log.i("dates", format + "  " + date);
        DateFormat readFormat = new SimpleDateFormat("dd/M/yyyy HH:mm:ss");
        DateFormat writeTimeFormat = new SimpleDateFormat("HH:mm:ss");
        DateFormat writeDateFormat = new SimpleDateFormat("dd/M/yyyy");
        Date d = null, f = null;
        try {
            f = readFormat.parse(format);
            d = readFormat.parse(date);
            String formattedDate = "", formatedTime = "", formattedDate2 = "", formatedTime2 = "";

            formattedDate2 = writeDateFormat.format(d);
            formatedTime2 = writeTimeFormat.format(f);
            formattedDate = writeDateFormat.format(f);
            formatedTime = writeTimeFormat.format(d);



            long difference = writeDateFormat.parse(formattedDate2).getTime() - writeDateFormat.parse(formattedDate).getTime();
            long difference2 = writeTimeFormat.parse(formatedTime2).getTime() - writeTimeFormat.parse(formatedTime).getTime();

            return difference + difference2 > 1 ? difference + difference2 : (difference + difference2) *-1 ;
        } catch (ParseException e) {
            Log.i("error Hours", " error " + e.getMessage());
            return 0;
        }

    }


    public void startHandler() {
        getPresenter().startHandler();
    }
}



