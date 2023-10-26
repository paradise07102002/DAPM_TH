package edu.huflit.doanpm_th.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import edu.huflit.doanpm_th.Object.LoaiSach;
import edu.huflit.doanpm_th.Object.Sach;

public class MyDatabase {
    SQLiteDatabase database;
    DBHelper helper;

    public MyDatabase(Context context)
    {
        helper = new DBHelper(context);
        database = helper.getWritableDatabase();
    }
    public Cursor layDuLieuSach()
    {
        String[] cot = {DBHelper.MA_SACH_S, DBHelper.TEN_SACH_S, DBHelper.IMAGE_SACH};
        Cursor cursor = database.query(DBHelper.TABLE_SACH, cot, null, null, null, null, null);
        return cursor;
    }
    public long addLoaiSach(LoaiSach loaiSach)
    {
        ContentValues values = new ContentValues();
        values.put(DBHelper.TEN_LOAI_SACH_LS, loaiSach.getLoai_sach_ls());
        return database.insert(DBHelper.TABLE_LOAI_SACH, null, values);
    }
    public long addBook(Sach sach)
    {
        ContentValues values = new ContentValues();
        values.put(DBHelper.MA_LOAI_SACH_S, sach.getMa_loai_sach_s());
        values.put(DBHelper.TEN_SACH_S, sach.getTen_sach_s());
        values.put(DBHelper.TAC_GIA_S, sach.getTac_gia_s());
        values.put(DBHelper.NHA_XUAT_BAN_S, sach.getNha_xuat_ban_s());
        values.put(DBHelper.NAM_XUAT_BAN_S, sach.getNam_xuat_ban_s());
        values.put(DBHelper.IMAGE_SACH, sach.getImage_sach());
        values.put(DBHelper.TRANG_THAI_S, 0);
        values.put(DBHelper.MO_TA_SACH, sach.getMo_ta_sach());
        return database.insert(DBHelper.TABLE_SACH, null, values);
    }
}
