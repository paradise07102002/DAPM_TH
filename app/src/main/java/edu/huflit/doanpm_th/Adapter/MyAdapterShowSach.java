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

import org.w3c.dom.Text;

import edu.huflit.doanpm_th.ManHinhUser;
import edu.huflit.doanpm_th.R;

public class MyAdapterShowSach extends BaseAdapter {
    LayoutInflater inflater;
    TextView textView;
    ImageView imageView;
    Context context;
    public MyAdapterShowSach(Context context)
    {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return ManHinhUser.saches.size();
    }

    @Override
    public Object getItem(int i) {
        return ManHinhUser.saches.get(i);
    }

    @Override
    public long getItemId(int i) {
        return ManHinhUser.saches.get(i).getMa_sach_s();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.item_list_show_book, null);

        textView = (TextView) view.findViewById(R.id.tv_dms11);
        textView = (TextView) view.findViewById(R.id.tv_dms22);
        textView = (TextView) view.findViewById(R.id.tv_dms33);
        textView.setText(Integer.toString(ManHinhUser.saches.get(i).getMa_sach_s()));
        textView = (TextView) view.findViewById(R.id.tv_dms44);
        textView.setText(ManHinhUser.saches.get(i).getTen_sach_s());

        byte[] bytes = ManHinhUser.saches.get(i).getImage_sach();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        imageView = (ImageView) view.findViewById(R.id.dms_image2);
        imageView.setImageBitmap(bitmap);


        return view;
    }
}
