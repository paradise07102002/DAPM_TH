package edu.huflit.doanpm_th;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import edu.huflit.doanpm_th.Adapter.MyAdapterTimKiemSach;
import edu.huflit.doanpm_th.Object.Sach;
import edu.huflit.doanpm_th.SQLite.DBHelper;
import edu.huflit.doanpm_th.SQLite.MyDatabase;

public class TimKiemSach extends Fragment {
    View view;
    public static ListView listView;
    public static ArrayList<Sach> saches;
    MyDatabase database;
    ManHinhChinh manHinhChinh;
    ImageView back;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tim_kiem_sach, container, false);
        database = new MyDatabase(getActivity());
        manHinhChinh = (ManHinhChinh) getActivity();
        saches = new ArrayList<>();
        anhXa();
        capNhatDuLieuDSach();
        return view;
    }
    public void anhXa()
    {
        back = (ImageView) view.findViewById(R.id.search_back);
        listView = (ListView) view.findViewById(R.id.lv_search);
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
        SharedPreferences name_sach = getActivity().getSharedPreferences("search_sach", Context.MODE_PRIVATE);
        String ten_sach = name_sach.getString("name_sach", null);
        database = new MyDatabase(getActivity());
        Cursor cursor = database.layDuLieuSachByName(ten_sach);
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
            listView.setAdapter(new MyAdapterTimKiemSach(getActivity()));
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }
}
