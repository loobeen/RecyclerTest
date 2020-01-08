package com.elegion.recyclertest;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.content.AsyncTaskLoader;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

public class PhoneNumberLoader extends AsyncTaskLoader<String> {

    private String mId;
    private String number;

    private WeakReference<MainActivity> mActivityWeakReference;

    public PhoneNumberLoader(MainActivity activity, String id) {
        super(activity);
        mId = id;

        mActivityWeakReference = new WeakReference<>(activity);
    }

    @Override
    public String loadInBackground() {

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        Cursor cursor = mActivityWeakReference.get().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND "
                        + ContactsContract.CommonDataKinds.Phone.TYPE + " = ?",
                new String[]{mId, String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)},
                null);

        if (cursor != null && cursor.moveToFirst()) {
            number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            cursor.close();
        }

        return number;
    }
}
