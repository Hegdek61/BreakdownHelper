package uk.ac.tees.aad.B1212361;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Emergency extends AppCompatActivity {

    private static final int REQUEST_CONTACT = 1;
    private static final int REQUEST_READ_CONTACTS_PERMISSION = 0;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =129 ;

    Button button;
    ListView list;
    Button sendEmerGency;

    String message;

    String mobile;
    SmsManager smsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        list = findViewById(R.id.list);
        smsManager = SmsManager.getDefault();

        message = "I Have Emergency, please find me at lat:"+getIntent().getDoubleExtra("latitudeValue",0)+ " lng:"+getIntent().getDoubleExtra("longitudeValue",0);

        final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);

        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_READ_CONTACTS_PERMISSION);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }

        sendEmerGency = findViewById(R.id.sendemergency);
        sendEmerGency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMSAll();
            }
        });

        button = findViewById(R.id.addcontact);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(pickContact, REQUEST_CONTACT);
            }
        });

        loadData();
    }

    private void sendSMSAll() {


        SharedPreferences sharedPref = getSharedPreferences("contacts",MODE_PRIVATE);

        String json = sharedPref.getString("data", "");

        if(!json.equals(""))
        {
            String[] dat = json.split("::");
            for (int x=0;x<dat.length;x++)
            {
                mobile = dat[x].split(":22:")[1];
                smsManager.sendTextMessage(mobile, null, message, null, null);

                Toast.makeText(getApplicationContext(),"Message sent to "+mobile,Toast.LENGTH_SHORT).show();
            }
            startActivity(new Intent(getApplicationContext(),Confirmation2.class));
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(mobile != null){
                        smsManager.sendTextMessage(mobile, null, message, null, null);
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == REQUEST_CONTACT && data != null)
        {
            Uri contactUri = data.getData();
            String[] queryFields = new String[]{ContactsContract.Contacts.DISPLAY_NAME,ContactsContract.Contacts.HAS_PHONE_NUMBER,ContactsContract.Contacts._ID};
            Cursor cursor = this.getContentResolver().query(contactUri, queryFields, null, null, null);
            try {
                if (cursor.getCount() == 0) return;
                cursor.moveToFirst();
                String name = cursor.getString(0);
                ContentResolver cr = getContentResolver();
                Cursor cursor2 = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                        "DISPLAY_NAME = '" + name + "'", null, null);
                if (cursor2.moveToFirst()) {
                    String contactId =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                    boolean found = false;
                    while (phones.moveToNext()) {
                        if(!found) {
                            String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            number = number.replace(" ","").replace("-","");
                            Contact on =new Contact(name,number);
                            addContact(on);
                            found = true;
                        }
                    }
                    phones.close();
                }
                cursor.close();
            }
            finally {

                cursor.close();
            }
        }
    }

    private void addContact(Contact on) {
        SharedPreferences sharedPref = getSharedPreferences("contacts",MODE_PRIVATE);

        String json = sharedPref.getString("data", "");

        SharedPreferences.Editor editor = sharedPref.edit();

        if(!json.equals(""))
            json = json +"::"+ on.name+":22:"+on.mobile;
        else
            json = on.name+":22:"+on.mobile;


        editor.putString("data",json);
        editor.apply();
        editor.commit();
        loadData();
    }

    private void loadData() {
        String[] datad = new String[]{};

        SharedPreferences sharedPref = getSharedPreferences("contacts",MODE_PRIVATE);

        String json = sharedPref.getString("data", "");

        if (!json.equals("")){
            datad = json.split("::");

            for (int x=0;x<datad.length;x++) {
                datad[x] = datad[x].replace(":22:"," ");
            }
        }



        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,datad);
        list.setAdapter(adapter);
    }
}
