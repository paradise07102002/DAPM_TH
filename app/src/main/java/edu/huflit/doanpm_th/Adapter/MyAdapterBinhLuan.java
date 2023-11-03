package edu.huflit.doanpm_th.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import edu.huflit.doanpm_th.ChiTietSach;
import edu.huflit.doanpm_th.Object.User;
import edu.huflit.doanpm_th.R;
import edu.huflit.doanpm_th.SQLite.MyDatabase;

public class MyAdapterBinhLuan extends BaseAdapter {
    LayoutInflater inflater;
    TextView textView;
    ImageView imageView;
    Context context;
    public MyAdapterBinhLuan(Context context)
    {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }
    @Override
    public int getCount() {
        return ChiTietSach.binhLuanSaches.size();
    }

    @Override
    public Object getItem(int i) {
        return ChiTietSach.binhLuanSaches.get(i);
    }

    @Override
    public long getItemId(int i) {
        return ChiTietSach.binhLuanSaches.get(i).getMa_binh_luan();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        View view = inflater.inflate(R.layout.item_list_binh_luan, null);

        textView = (TextView) view.findViewById(R.id.tv_username_bl);
        //lấy username khi đã có id user
        MyDatabase database = new MyDatabase(context);
        User user = database.getUserByIDUser(ChiTietSach.binhLuanSaches.get(i).getMa_user_binh_luan());
        textView.setText(user.getUsername_user());

        textView = (TextView) view.findViewById(R.id.tv_noi_dung_bl);
        textView.setText(DetailSach.binhLuanSaches.get(i).getNoi_dung_binh_luan());

        return view;
    }
}
