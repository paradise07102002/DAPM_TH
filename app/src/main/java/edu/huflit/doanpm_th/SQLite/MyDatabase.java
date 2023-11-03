package edu.huflit.doanpm_th.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import edu.huflit.doanpm_th.Object.BinhLuanSach;
import edu.huflit.doanpm_th.Object.LoaiSach;
import edu.huflit.doanpm_th.Object.Sach;
import edu.huflit.doanpm_th.Object.User;

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
    //Kiểm tra đăng nhập
    public boolean checkLogin(String username, String password)
    {
        String select = "SELECT * FROM " + DBHelper.TABLE_USER + " WHERE " + DBHelper.USERNAME_USER + " = " + "'" + username + "'" + " AND " + DBHelper.PASSWORD_USER + " = " + "'" + password + "'";
        Cursor cursor = database.rawQuery(select, null);
        if (cursor.moveToFirst() == false)
        {
            return false;//Ko đúng user or pass
        }
        else
        {
            return true;
        }
    }
    public Cursor layDuLieuSachByID(int ma_sach)
    {
        String select = "SELECT * FROM " + DBHelper.TABLE_SACH + " WHERE " + DBHelper.MA_SACH_S + " = " + ma_sach;
        Cursor cursor = database.rawQuery(select, null);
        return cursor;
    }
    public Cursor layDuLieuDauSachByID(int ma_dau_sach)
    {
        String select = "SELECT * FROM " + DBHelper.TABLE_LOAI_SACH + " WHERE " + DBHelper.MA_LOAI_SACH_LS + " = " + ma_dau_sach;
        Cursor cursor = database.rawQuery(select, null);
        return cursor;
    }
    public boolean checkUsername(String username)
    {
        String select = "SELECT * FROM " + DBHelper.TABLE_USER + " WHERE " + DBHelper.USERNAME_USER + " = " + "'" + username + "'";
        Cursor cursor = database.rawQuery(select, null);
        if (cursor.moveToFirst())
        {
            return true;
        }
        return false;
    }
    public long addUser(User user)
    {
        ContentValues values = new ContentValues();
        values.put(DBHelper.USERNAME_USER, user.getUsername_user());
        values.put(DBHelper.PASSWORD_USER, user.getPassword_user());
        values.put(DBHelper.FULLNAME_USER, user.getFullname_user());
        values.put(DBHelper.EMAIL_USER, user.getEmail_user());
        values.put(DBHelper.PHONE_USER, user.getPhone_user());
        values.put(DBHelper.ROLE_USER, user.getRole_user());
        values.put(DBHelper.LOAI_KH_USER, user.getLoai_kh_user());
        return database.insert(DBHelper.TABLE_USER, null, values);
    }
    public Cursor getUserByUsername(String username)
    {
        String select = "SELECT * FROM " + DBHelper.TABLE_USER + " WHERE " + DBHelper.USERNAME_USER + " = " + "'" + username + "'";
        Cursor cursor = database.rawQuery(select, null);
        return cursor;
    }
    public long addBinhLuan(BinhLuanSach binhLuanSach)
    {
        ContentValues values = new ContentValues();
        values.put(DBHelper.MA_USER_BL, binhLuanSach.getMa_user_binh_luan());
        values.put(DBHelper.MA_SACH_BL, binhLuanSach.getMa_sach_binh_luan());
        values.put(DBHelper.NOI_DUNG_BL, binhLuanSach.getNoi_dung_binh_luan());
        return database.insert(DBHelper.TABLE_BINH_LUAN, null, values);
    }
    public Cursor layDuLieuBinhLuanByMaSach(int ma_sach)
    {
        String select = "SELECT * FROM " + DBHelper.TABLE_BINH_LUAN + " WHERE " + DBHelper.MA_SACH_BL + " = " + ma_sach;
        Cursor cursor = database.rawQuery(select, null);
        return cursor;
    }
    public User getUserByIDUser(int ma_user)
    {
        User user = new User();
        String select = "SELECT * FROM " + DBHelper.TABLE_USER + " WHERE " + DBHelper.ID_USER + " = " + ma_user;
        Cursor cursor = database.rawQuery(select, null);
        if (cursor != null)
        {
            int username_index = cursor.getColumnIndex(DBHelper.USERNAME_USER);
            cursor.moveToFirst();
            user.setUsername_user(cursor.getString(username_index));
        }
        cursor.close();
        return user;
    }
}
