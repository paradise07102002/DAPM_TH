package edu.huflit.doanpm_th.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import edu.huflit.doanpm_th.R;
import edu.huflit.doanpm_th.TimKiemSach;

public class MyAdapterTimKiemSach extends BaseAdapter {
    LayoutInflater inflater;
    TextView textView;
    ImageView imageView;
    Context context;
    public MyAdapterTimKiemSach(Context context)
    {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }
    @Override
    public int getCount() {
        return TimKiemSach.saches.size();
    }

    @Override
    public Object getItem(int i) {
        return TimKiemSach.saches.get(i);
    }

    @Override
    public long getItemId(int i) {
        return TimKiemSach.saches.get(i).getMa_sach_s();
    }


    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        View view = inflater.inflate(R.layout.item_list_tim_kiem_sach, null);

        textView = (TextView) view.findViewById(R.id.tks_tv1);
        textView = (TextView) view.findViewById(R.id.tks_tv2);
        textView = (TextView) view.findViewById(R.id.tks_tv3);
        textView.setText(Integer.toString(TimKiemSach.saches.get(i).getMa_sach_s()));
        textView = (TextView) view.findViewById(R.id.tks_tv4);
        textView.setText(TimKiemSach.saches.get(i).getTen_sach_s());

        byte[] bytes = TimKiemSach.saches.get(i).getImage_sach();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        imageView = (ImageView) view.findViewById(R.id.tks_image);
        imageView.setImageBitmap(bitmap);

        return view;
    }
}
