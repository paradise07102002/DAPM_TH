package edu.huflit.doanpm_th;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import edu.huflit.doanpm_th.Object.User;
import edu.huflit.doanpm_th.SQLite.DBHelper;
import edu.huflit.doanpm_th.SQLite.MyDatabase;

public class ManHinhChinh extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final int FR_TRANG_CHU = 0;
    private int currentFragment = FR_TRANG_CHU;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    public static NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
    EditText edt_tim_kiem;
    ImageView img_tim_kiem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chinh);
        setTitle("");
        anhXa();
        //Hiển thị thông tin đăng nhập lên header
        SharedPreferences get_user = getSharedPreferences("login", MODE_PRIVATE);
        String username = get_user.getString("username", null);
        boolean is_login = get_user.getBoolean("is_login", false);
        if (is_login == false)
        {
            TextView show_username2 = navigationView.getHeaderView(0).findViewById(R.id.username1);
            TextView show_email2 = navigationView.getHeaderView(0).findViewById(R.id.email1);
            show_email2.setText("Email");
            show_username2.setText("Username");
            replaceFragment(new ManHinhUser());
        }
        else
        {
            MyDatabase database = new MyDatabase(getApplicationContext());
            //LẤY USER NAME EMAIL SHOW LÊN HEADER
            TextView show_username = navigationView.getHeaderView(0).findViewById(R.id.username1);
            TextView show_email = navigationView.getHeaderView(0).findViewById(R.id.email1);
            Cursor cursor = database.getUserByUsername(username);

            int username_index = cursor.getColumnIndex(DBHelper.USERNAME_USER);
            int email_index = cursor.getColumnIndex(DBHelper.EMAIL_USER);
            cursor.moveToFirst();

            show_username.setText(cursor.getString(username_index));
            show_email.setText(cursor.getString(email_index));
            cursor.close();
            replaceFragment(new ManHinhUser());
        }
        //Hiển thị trang chủ
        replaceFragment(new ManHinhUser());

        //ADD Toolbar
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,   R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.action_trang_chu)
                {
                    if (currentFragment != FR_TRANG_CHU)
                    {
                        currentFragment = FR_TRANG_CHU;
                    }
                    replaceFragment(new ManHinhUser());
                }
                return true;
            }
        });
        //Kiểm tra đăng nhập, nếu chưa thì hiện đăng nhập, ngược lại hiện đăng xuất trên menu
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        Menu navigationMenu = navigationView.getMenu();
        MenuItem menuItem = navigationMenu.findItem(R.id.nav_tittle6);
        boolean check_login = sharedPreferences.getBoolean("is_login", false);
        if (check_login == false)
        {
            menuItem.setTitle("Đăng nhập");
        }
        else
        {
            menuItem.setTitle("Đăng xuất");
        }
        //Tìm kiếm sách
        img_tim_kiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_tim_kiem.length() == 0)
                {
                    Toast.makeText(ManHinhChinh.this, "Vui lòng nhập thông tin tìm kiếm", Toast.LENGTH_LONG).show();
                }
                else
                {
                    String ten_sach_tim_kiem = edt_tim_kiem.getText().toString().trim();
                    SharedPreferences name_sach = getSharedPreferences("search_sach", MODE_PRIVATE);
                    SharedPreferences.Editor editor = name_sach.edit();
                    editor.putString("name_sach", ten_sach_tim_kiem);
                    editor.apply();
                    replaceFragment(new TimKiemSach());
                }
            }
        });
    }
    public void anhXa()
    {
        drawerLayout = (DrawerLayout) findViewById(R.id.hdrawer_layout);
        toolbar = (Toolbar) findViewById(R.id.htoolbar);
        navigationView = (NavigationView) findViewById(R.id.hnavigation_view);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);

        img_tim_kiem = (ImageView) findViewById(R.id.img_tim_kiem);
        edt_tim_kiem = (EditText) findViewById(R.id.edt_tim_kiem);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_tittle3)
        {

        }
        else if (id == R.id.nav_tittle6)
        {
            SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            boolean check_login = sharedPreferences.getBoolean("is_login", false);
            if (check_login == false)
            {
                replaceFragment(new DangNhap());
            }
            else if (check_login)
            {
                //Khi đăng xuất thì header ko còn hiện username và email
                TextView show_username2 = navigationView.getHeaderView(0).findViewById(R.id.username1);
                TextView show_email2 = navigationView.getHeaderView(0).findViewById(R.id.email1);
                show_email2.setText("Email");
                show_username2.setText("Username");

                SharedPreferences sharedPreferences1 = getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor editorr = sharedPreferences1.edit();
                editorr.putBoolean("is_login", false);
                editorr.putString("username", null);
                editorr.apply();
                Toast.makeText(ManHinhChinh.this, "Đã đăng xuất", Toast.LENGTH_LONG).show();

                //Kiểm tra đăng nhập, nếu chưa thì hiện đăng nhập, ngược lại hiện đăng xuất trên menu
                Menu navigationMenu = navigationView.getMenu();
                MenuItem menuItem = navigationMenu.findItem(R.id.nav_tittle6);
                menuItem.setTitle("Đăng nhập");
                replaceFragment(new ManHinhUser());

            }
        }

        //Đóng drawer
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    private void replaceFragment(Fragment fragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.hcontent_frame, fragment);
        transaction.commit();
    }
    public void gotoTrangChu()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ManHinhUser manHinhUser = new ManHinhUser();

        fragmentTransaction.replace(R.id.hcontent_frame, manHinhUser);
        fragmentTransaction.commit();
    }
    public void gotoChiTietSach()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ChiTietSach chiTietSach = new ChiTietSach();

        fragmentTransaction.replace(R.id.hcontent_frame, chiTietSach);
        fragmentTransaction.commit();
    }
    public void gotoDangKy()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        DangKy dangKy = new DangKy();

        fragmentTransaction.replace(R.id.hcontent_frame, dangKy);
        fragmentTransaction.commit();
    }
    public void gotoDangNhap()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        DangNhap dangNhap = new DangNhap();

        fragmentTransaction.replace(R.id.hcontent_frame, dangNhap);
        fragmentTransaction.commit();
    }
}