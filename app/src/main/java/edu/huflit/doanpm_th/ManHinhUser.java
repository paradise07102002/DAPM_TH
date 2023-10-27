package edu.huflit.doanpm_th;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import edu.huflit.doanpm_th.Adapter.MyAdapterShowSach;
import edu.huflit.doanpm_th.Object.LoaiSach;
import edu.huflit.doanpm_th.Object.Sach;
import edu.huflit.doanpm_th.SQLite.DBHelper;
import edu.huflit.doanpm_th.SQLite.MyDatabase;

public class ManHinhUser extends Fragment {
    View view;
    MyDatabase database;
    public static ListView listView;
    public static ArrayList<Sach> saches;
    ManHinhChinh manHinhChinh;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.man_hinh_user, container, false);
        anhXa();
        manHinhChinh = (ManHinhChinh) getActivity();
        database = new MyDatabase(getActivity());
        saches = new ArrayList<>();
        //Thêm sách
        //insertBook();
        capNhatDuLieuDSach();

        return view;
    }
    public void anhXa()
    {
        listView = (ListView) view.findViewById(R.id.lv_show_sach_for_user);
    }
    public void capNhatDuLieuDSach()
    {
        if (saches == null)
        {
            saches = new ArrayList<Sach>();
        }
        else
        {
            saches.removeAll(saches);
        }
        database = new MyDatabase(getActivity());
        Cursor cursor = database.layDuLieuSach();
        if (cursor != null)
        {
            int ten_sach_index = cursor.getColumnIndex(DBHelper.TEN_SACH_S);
            int ma_sach_index = cursor.getColumnIndex(DBHelper.MA_SACH_S);
            int img_sach_index = cursor.getColumnIndex(DBHelper.IMAGE_SACH);
            while (cursor.moveToNext())
            {
                Sach sach = new Sach();
                if (ten_sach_index != -1)
                {
                    sach.setTen_sach_s(cursor.getString(ten_sach_index));
                }
                if (ma_sach_index != -1)
                {
                    sach.setMa_sach_s(cursor.getInt(ma_sach_index));
                }
                if (img_sach_index != -1)
                {
                    sach.setImage_sach(cursor.getBlob(img_sach_index));
                }
                saches.add(sach);
            }
        }
        if (saches != null)
        {
            listView.setAdapter(new MyAdapterShowSach(getActivity()));
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SharedPreferences lay_ma_sach = getActivity().getSharedPreferences("lay_ma_sach", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = lay_ma_sach.edit();
                int ma_sach = saches.get(i).getMa_sach_s();
                editor.putInt("ma_sach", ma_sach);
                editor.apply();

                manHinhChinh.gotoChiTietSach();
            }
        });
    }
    public void insertDauSach()
    {
        LoaiSach loaiSach1 = new LoaiSach();
        loaiSach1.setLoai_sach_ls("Truyện tranh");

        LoaiSach loaiSach2 = new LoaiSach();
        loaiSach2.setLoai_sach_ls("Tiểu thuyết");

        LoaiSach loaiSach3 = new LoaiSach();
        loaiSach3.setLoai_sach_ls("Cổ tích");

        MyDatabase myDatabase = new MyDatabase(getActivity());
        myDatabase.addLoaiSach(loaiSach1);
        myDatabase.addLoaiSach(loaiSach2);
        myDatabase.addLoaiSach(loaiSach3);

    }
    public void insertBook()
    {
        insertDauSach();

        Sach sach1 = new Sach();
        sach1.setImage_sach(convertImageToByArray(getActivity(), R.drawable.sach1));
        sach1.setMa_loai_sach_s(1);
        sach1.setTen_sach_s("Đô rê mon");
        sach1.setNam_xuat_ban_s(2022);
        sach1.setNha_xuat_ban_s("ABC");
        sach1.setMo_ta_sach("");
        sach1.setTrang_thai_s(0);
        sach1.setTac_gia_s("Khôi");

        Sach sach2 = new Sach();
        sach2.setImage_sach(convertImageToByArray(getActivity(), R.drawable.sach2));
        sach2.setMa_loai_sach_s(2);
        sach2.setTen_sach_s("Sách tiểu thuyết");
        sach2.setNam_xuat_ban_s(2022);
        sach2.setNha_xuat_ban_s("ABCD");
        sach2.setMo_ta_sach("");
        sach2.setTrang_thai_s(0);
        sach2.setTac_gia_s("Phạm");

        Sach sach3 = new Sach();
        sach3.setImage_sach(convertImageToByArray(getActivity(), R.drawable.sach3));
        sach3.setMa_loai_sach_s(3);
        sach3.setTen_sach_s("Ăn khế trả vàng");
        sach3.setNam_xuat_ban_s(2022);
        sach3.setNha_xuat_ban_s("ABCD");
        sach3.setMo_ta_sach("");
        sach3.setTrang_thai_s(0);
        sach3.setTac_gia_s("Phạm");

        MyDatabase myDatabase = new MyDatabase(getActivity());
        myDatabase.addBook(sach1);
        myDatabase.addBook(sach2);
        myDatabase.addBook(sach3);

    }
    private byte[] convertImageToByArray(Context context, int drawableResourceId)
    {
        Drawable drawable = ContextCompat.getDrawable(context, drawableResourceId);
        if (drawable != null)
        {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0,0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            // Nén và chuyển đổi Bitmap thành mảng byte

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
            return stream.toByteArray();
        }
        else
        {
            return null;
        }
    }
}
