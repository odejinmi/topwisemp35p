package com.paylony.topwise.sdk.emv.database.table;


import android.content.Context;
import android.util.Log;
import com.topwise.cloudpos.struct.TlvList;
import com.topwise.sdk.emv.database.BaseDaoImpl;
import com.topwise.sdk.emv.database.table.Capk;
import com.topwise.sdk.emv.database.table.MyDBHelper;

import java.util.List;

public class CapkDaoImpl extends BaseDaoImpl<com.topwise.sdk.emv.database.table.Capk> {
    public CapkDaoImpl(Context context) {
        super(new MyDBHelper(context), com.topwise.sdk.emv.database.table.Capk.class);
    }

    public com.topwise.sdk.emv.database.table.Capk findByRidIndex(String rid, byte index) {
        String ridindex = (rid + Integer.toHexString(index & 255)).toUpperCase();
        StringBuffer sb = (new StringBuffer("select * from tb_capk where ridindex='")).append(ridindex).append("'");
        List<com.topwise.sdk.emv.database.table.Capk> capklist = this.rawQuery(sb.toString(), (String[])null);
        return capklist != null && capklist.size() != 0 ? (com.topwise.sdk.emv.database.table.Capk)capklist.get(0) : null;
    }

    public List<com.topwise.sdk.emv.database.table.Capk> findAllCapk() {
        StringBuffer sb = new StringBuffer("select * from tb_capk");
        List<com.topwise.sdk.emv.database.table.Capk> capklist = this.rawQuery(sb.toString(), (String[])null);
        return capklist;
    }

    public void addCapk(String capkStr) {
        com.topwise.sdk.emv.database.table.Capk capk = new Capk();
        TlvList list = new TlvList();
        Log.d("addCapk", "addCapk: " + capkStr);
        list.fromHex(capkStr);
        capk.fromTlvList(list);
        if (this.findByRidIndex(capk.getRid(), capk.getIndex()) != null) {
            this.update(capk);
        } else {
            this.insert(capk);
        }

    }

    public void deleteCapk(String ridindex) {
        StringBuffer sb = new StringBuffer("delete from tb_capk where ridindex='" + ridindex + "'");
        this.execSql(sb.toString(), (Object[])null);
    }

    public void deleteAllCapk() {
        StringBuffer sb = new StringBuffer("delete from tb_capk");
        this.execSql(sb.toString(), (Object[])null);
    }
}

