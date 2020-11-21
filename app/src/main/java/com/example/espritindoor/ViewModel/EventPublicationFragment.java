package com.example.espritindoor.ViewModel;


import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.espritindoor.Model.Event;
import com.example.espritindoor.R;
import com.example.espritindoor.technique.ApiInterface;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventPublicationFragment extends Fragment {
    EditText dateformat;
    View view;
    int year;
    int month;
    int day;

    EditText time;
    EditText eventPlace;
    String eventName;
    String eventDescription;
    String localtion;
    Uri uri;
    String date;
    private Uri path2;
    private Bitmap bitmap;

    private Button publishEvent;
    private ImageButton poster;
    EventFragment eff;

    {
        try {
            eff = new EventFragment();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public EventPublicationFragment() {
        // Required empty public constructor
    }

    private static final int READ_REQUEST_CODE = 42;

   // private Bitmap bitmap  ;

    private  static  final  int IMG_REQUEST = 777;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_event_publication, container, false);
         ImageButton  imageBn = (ImageButton) view.findViewById(R.id.add_btn_image_event);

        final EditText eventNameEditText = (EditText) view.findViewById(R.id.add_name_event);
        final EditText eventDescriptionEditText = (EditText) view.findViewById(R.id.add_description_event);
        eventPlace = (EditText) view.findViewById(R.id.add_place_event);

        publishEvent = (Button) view.findViewById(R.id.button_event_publish);
      //  poster = (ImageButton) view.findViewById(R.id.add_btn_image_event);


        eventName = eventNameEditText.getText().toString();
        eventDescription = eventDescriptionEditText.getText().toString();
        localtion = eventPlace.getText().toString();
        imageBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 10);
            }
        });
        publishEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postimage(bitmap);

            }
        });
/*
        final Event finalevent = null;
        publishEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            //    addEventToDB();
            }
        });

*/
        eventDescriptionEditText.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                eventDescription = eventDescriptionEditText.getText().toString();
                if (eventDescription.length() < 20) {

                    publishEvent.setEnabled(false);
                } else {
                    publishEvent.setEnabled(true);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }



        });

        eventNameEditText.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                eventName = eventNameEditText.getText().toString();
                if (eventName.length() < 4) {
                    publishEvent.setEnabled(false);
                } else {
                    publishEvent.setEnabled(true);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }



        });
        dateformat = view.findViewById(R.id.add_date_event);
        final Calendar calendar = Calendar.getInstance();
        dateformat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateformat.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
                    }
                } ,year,month,day);
                datePickerDialog.show();
            }
        });


        time = view.findViewById(R.id.add_time_event);
        Button pickTimeButton =  view.findViewById(R.id.add_btn_time_event);
        final Calendar c = Calendar.getInstance();
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);
        pickTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time.setText(hourOfDay+":"+minute);
                    }
                },hour,minute,android.text.format.DateFormat.is24HourFormat(getContext()));
                timePickerDialog.show();
            }
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);

            }
        }
        enable_button();



        System.out.println(date+"alooooooooooooooooooooooooooooooooooooooooooooo");
       /* imageBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, IMG_REQUEST);
            }
        });
*/

/*

        Button btn_photo =  view.findViewById(R.id.add_btn_photo_event);
        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                intent.setType("image/*");
                startActivity(intent);
            }
        });
*/

        return view;
    }



    private void enable_button() {

       /* publishEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* ((AppCompatActivity)getContext())
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, eff)
                        .addToBackStack(null)
                        .commit();*/
                //   System.out.println(uri+"hedhaaaaaaaaaaahowaaaaaaaaaaaaaaaaaaaaaa");
                /*
                   new MaterialFilePicker()
                            .withActivity(MainActivity.this)
                            .withRequestCode(10)
                            .start();*/
            //}
        //});
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 100 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)){
            enable_button();
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
            }
        }
    }

    ProgressDialog progress;

    public String getRealPathFromURI(Uri contentUri) {

        // can post image
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery( contentUri,
                proj, // Which columns to return
                null, // WHERE clause; which rows to return (all rows)
                null, // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if(requestCode == 10 && resultCode == RESULT_OK && data != null){

            progress = new ProgressDialog(this.getContext());
            progress.setTitle("Uploading");
            progress.setMessage("Please wait...");
            Uri path = data.getData();
            try {
                bitmap  = MediaStore.Images.Media.getBitmap(getContext().getContentResolver() ,path);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            progress.show();
/*
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {

                    Uri  uri = data.getData();
                    //   path2=data.getData();
                    path2 = uri;
                    if (uri != null) {
                        System.out.println("ssssssssssssssss"+uri.getPath());
                    }
                    File f  = new File(getRealPathFromURI(uri));
                    System.out.println(f+"weeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                    String content_type  = getMimeType(f.getPath());
                    //  ImageButton  imageBn = (ImageButton) view.findViewById(R.id.add_btn_image_event);
                    String file_path = f.getAbsolutePath();
                    OkHttpClient client = new OkHttpClient();
                    RequestBody file_body = RequestBody.create(MediaType.parse(content_type),f);

                    RequestBody request_body = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("eventName",eventName)
                            .addFormDataPart("description",eventDescription)
                            .addFormDataPart("location",eventPlace.getText()+"")
                            .addFormDataPart("date",dateformat.getText()+" "+time.getText())
                            .addFormDataPart("type",content_type)
                            .addFormDataPart("photo",file_path.substring(file_path.lastIndexOf("/")+1), file_body)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://192.168.1.20:3000/events/userId/5eb8efb9a4f40a288445a12a")
                            .post(request_body)

                            .build();

                    try {
                        Response response = client.newCall(request).execute();

                        if(!response.isSuccessful()){
                            throw new IOException("Error : "+response);
                        }

                        progress.dismiss();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            });
            t.start();*/
        }
    }
    public void postimage(Bitmap b) {
            try {
                System.out.println("ffffffff"+b);
                File filesDir = getContext().getFilesDir();
                File file = new File(filesDir, "image" + ".png");

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.PNG, 0, bos);
                byte[] bitmapdata = bos.toByteArray();


                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();


                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), reqFile);
                RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "image");


                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://192.168.1.20:3000/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();


                    ApiInterface apiInterface = retrofit.create(ApiInterface.class);

                    Call<Event> call = apiInterface.uploadImage(body,name);

                    call.enqueue(new Callback<Event>() {
                        @Override
                        public void onResponse(Call<Event> call, Response<Event> response) {
                            Log.d("********", " enrigisté ");
                        }

                        @Override
                        public void onFailure(Call<Event> call, Throwable t) {
                            Log.d("/////:", " error "+ t.toString());
                        }
                    });





            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    private String getMimeType(String path) {

        String extension = MimeTypeMap.getFileExtensionFromUrl(path);

        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }
  /*  private String uriToFilename(Uri uri) {
        String path = null;

        if ((Build.VERSION.SDK_INT < 19) && (Build.VERSION.SDK_INT > 11)) {
            path = getRealPathFromURI_API11to18(getContext(), uri);
        } else {
            path = getFilePath(getContext(), uri);
        }

        return path;
    }

    public static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        String result = null;
        CursorLoader cursorLoader = new CursorLoader(
                context,
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();
        if (cursor != null) {
            int column_index =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
        }
        return result;
    }

    public String getFilePath(Context context, Uri uri) {
        //Log.e("uri", uri.getPath());
        String filePath = "";
        if (DocumentsContract.isDocumentUri(context, uri)) {
            String wholeID = DocumentsContract.getDocumentId(uri);
            //Log.e("wholeID", wholeID);
            // Split at colon, use second item in the array
            String[] splits = wholeID.split(":");
            if (splits.length == 2) {
                String id = splits[1];

                String[] column = {MediaStore.Images.Media.DATA};
                // where id is equal to
                String sel = MediaStore.Images.Media._ID + "=?";
                Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        column, sel, new String[]{id}, null);
                int columnIndex = cursor.getColumnIndex(column[0]);
                if (cursor.moveToFirst()) {
                    filePath = cursor.getString(columnIndex);
                }
                cursor.close();
            }
        } else {
            filePath = uri.getPath();
        }
        return filePath;
    }

*/

/*
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data!=null ) {
            Uri path = data.getData();
            path2 = data.getData();
            ImageButton  imageBn = view.findViewById(R.id.add_btn_image_event);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),path);
                imageBn.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    */



/*
    private void addEventToDB(String... input) {

        AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... input) {

                String message;
                String datetime = "2016-12-09 10:00:00";
                File file  = new File(getRealPathFromURI(uri));
                System.out.println(file+"weeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee2222222222");
                System.out.println(uri+"weeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee2222222222");
                String content_type  = getMimeType(file.getPath());

                String file_path = file.getAbsolutePath();
                OkHttpClient client = new OkHttpClient();
                RequestBody file_body = RequestBody.create(MediaType.parse(content_type),file);

                RequestBody request_body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("type",content_type)
                        .addFormDataPart("photo",file_path.substring(file_path.lastIndexOf("/")+1), file_body)
                        .build();

                Request request = new Request.Builder()
                        .url("http://192.168.43.63:3000/events/photo")
                        .post(request_body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    message = response.body().string();
                    // we need another object to store the address


                } catch (IOException e) {
                    e.printStackTrace();
                    message = "Could not connect to server";
                }
                return message;
            }

            @Override
            protected void onPostExecute(String message) {
                if (message.equals("success")) {

                } else {

                    System.out.println("mezel bkri");
                    //     ProgressBar progressBar = (ProgressBar) findViewById(R.id.login_progress);
                    //    progressBar.setVisibility(View.GONE);
                }
            }
        };

        asyncTask.execute(input);
    }

*/

/*
    private void addEventToDB(String... input) {

        AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... input) {
                String message = null;
         //       System.out.println(input[0]+"KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
                OkHttpClient client = new OkHttpClient();
                RequestBody formBody = new FormBody.Builder()
                        .add("idEvent",input[0])
                        .add("eventName", input[1] )
                        .add("description", input[2])
                        .add("location", input[3])
                        .add("photo",input[4])
                        .add("date", String.valueOf(3))
                        .build();
                Request request = new Request.Builder()
                        .url("http://192.168.43.63:3000/events/userId/5e9594b39b7d942358da83dc").post(formBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String resp = response.body().string();
                    System.out.println(resp+"JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ");



                } catch (IOException e) {
                    e.printStackTrace();
                    message = "Could not connect to server";
                }
                return message;
            }

            @Override
            protected void onPostExecute(String message) {
                if (message.equals("correct")) {
                  //  Intent intent = new Intent(getContext(), MainActivity.class);
                  //  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                  //  startActivity(intent);
                } else {
                    Toast.makeText(getContext(), message,
                            Toast.LENGTH_SHORT).show();
                }
            }
        };

        asyncTask.execute(input);
    }
*/
    /*
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
      {
             final ImageView img = view.findViewById(R.id.add_btn_photo_event);
            final List<Bitmap> bitmaps = new ArrayList<>();
            ClipData clipData = data.getClipData();
            if (clipData != null)
            {
                    for (int i = 0;i< clipData.getItemCount();i++)
                    {
                        Uri imageUri = clipData.getItemAt(i).getUri();
                        try {
                            InputStream inputStream = getActivity().getContentResolver().openInputStream(imageUri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                            bitmaps.add(bitmap);

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
            }else {

                Uri imageUri =data.getData();
                InputStream inputStream = null;
                try {
                    inputStream = getActivity().getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    bitmaps.add(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
            new Thread(new Runnable() {
                @Override
                public void run() {

                    for (final Bitmap b : bitmaps){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                img.setImageBitmap(b);
                            }
                        });
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

        }
    }

    */
}
