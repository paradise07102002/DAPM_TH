package edu.huflit.doanpm_th;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.huflit.doanpm_th.SQLite.DBHelper;
import edu.huflit.doanpm_th.SQLite.MyDatabase;

public class TaiKhoan extends Fragment {
    View view;
    MyDatabase database;
    ManHinhChinh manHinhChinh;
    ImageView avartar, back;
    TextView name, email, phone, username;
    Button next_update;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tai_khoan, container, false);
        database = new MyDatabase(getActivity());
        manHinhChinh = (ManHinhChinh) getActivity();
        anhXa();
        showThongTin();
        return view;
    }
    public void anhXa()
    {
        avartar = (ImageView) view.findViewById(R.id.tk_avartar);
        back = (ImageView) view.findViewById(R.id.tk_back);
        next_update = (Button) view.findViewById(R.id.tk_next_update);

        name = (TextView) view.findViewById(R.id.tk_name);
        email = (TextView) view.findViewById(R.id.tk_email);
        phone = (TextView) view.findViewById(R.id.tk_phone);
        username = (TextView) view.findViewById(R.id.tk_username);
    }
    public void showThongTin()
    {
        SharedPreferences get_user = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        Cursor cursor = database.layThongTinTaiKhoan(get_user.getString("username", null));
        if (cursor.moveToFirst())
        {
            int name_index = cursor.getColumnIndex(DBHelper.FULLNAME_USER);
            int phone_index = cursor.getColumnIndex(DBHelper.PHONE_USER);
            int email_index = cursor.getColumnIndex(DBHelper.EMAIL_USER);
            int username_index = cursor.getColumnIndex(DBHelper.USERNAME_USER);

            name.setText(cursor.getString(name_index));
            phone.setText(cursor.getString(phone_index));
            email.setText(cursor.getString(email_index));
            username.setText(cursor.getString(username_index));
            cursor.close();
        }
    }
}
