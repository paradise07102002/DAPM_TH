package edu.huflit.doanpm_th;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import edu.huflit.doanpm_th.Object.User;
import edu.huflit.doanpm_th.SQLite.MyDatabase;

public class DangNhap extends Fragment {
    View view;
    private View background;
    ImageView back;
    Button btn_login;
    TextView next_dang_ky;
    MyDatabase database;
    EditText username, password;
    ManHinhChinh manHinhChinh;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dang_nhap, container, false);
        manHinhChinh = (ManHinhChinh) getActivity();
        database = new MyDatabase(getActivity());
        anhXa();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangNhap();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        next_dang_ky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manHinhChinh.gotoDangKy();

            }
        });
        return view;
    }
    public void anhXa()
    {
        username = (EditText) view.findViewById(R.id.login_username2);
        password = (EditText) view.findViewById(R.id.login_pass2);
        btn_login = (Button) view.findViewById(R.id.login_login2);
        back = (ImageView) view.findViewById(R.id.login2_back);
        next_dang_ky = (TextView) view.findViewById(R.id.login2_next_dangky);
    }
    //CODE KHI ẤN BUTTON ĐĂNG NHẬP
    public void dangNhap()
    {
        if (username.getText().toString().trim().length() == 0)
        {
            String p = "<font color='#FF0000'>Chưa nhập username</font>";
            username.setHint(Html.fromHtml(p));
            return;
        }
        if (password.getText().toString().trim().length() == 0)
        {
            String p = "<font color='#FF0000'>Chưa nhập password</font>";
            password.setHint(Html.fromHtml(p));
            return;
        }
        boolean check = database.checkLogin(username.getText().toString().trim(), password.getText().toString().trim());
        if (check == false)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Tài khoảng hoặc mật khẩu không chính xác");
            builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.create().show();
            return;
        }
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username.getText().toString().trim());
        editor.putBoolean("is_login", true);
        editor.apply();

        //User user = database.checkRole(username.getText().toString().trim());

        Menu navigationMenu = ManHinhChinh.navigationView.getMenu();
        MenuItem menuItem = navigationMenu.findItem(R.id.nav_tittle6);
        menuItem.setTitle("Đăng xuất");
        Intent intent = new Intent(getActivity(), ManHinhChinh.class);
        startActivity(intent);
    }

}
