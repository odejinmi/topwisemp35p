package com.paylony.topwise.sdk.emv.database.table;

import android.content.Context;
import android.util.Log;
import com.topwise.cloudpos.struct.TlvList;
import com.topwise.sdk.emv.database.BaseDaoImpl;
import com.topwise.sdk.emv.database.table.Aid;
import com.topwise.sdk.emv.database.table.MyDBHelper;

import java.util.Iterator;
import java.util.List;

public class AidDaoImpl extends BaseDaoImpl<com.topwise.sdk.emv.database.table.Aid> {
    public AidDaoImpl(Context context) {
        super(new MyDBHelper(context), com.topwise.sdk.emv.database.table.Aid.class);
    }

    public List<com.topwise.sdk.emv.database.table.Aid> findAllAid() {
        StringBuffer sb = new StringBuffer("select * from tb_aid");
        List<com.topwise.sdk.emv.database.table.Aid> aidlist = this.rawQuery(sb.toString(), (String[])null);
        return aidlist;
    }

    public com.topwise.sdk.emv.database.table.Aid findByAid(String aid) {
        StringBuffer sb = (new StringBuffer("select * from tb_aid where aid='")).append(aid).append("'");
        List<com.topwise.sdk.emv.database.table.Aid> aidlist = this.rawQuery(sb.toString(), (String[])null);
        return aidlist != null && aidlist.size() != 0 ? (com.topwise.sdk.emv.database.table.Aid)aidlist.get(0) : null;
    }

    public com.topwise.sdk.emv.database.table.Aid findByAidAndAsi(String aid) {
        List<com.topwise.sdk.emv.database.table.Aid> mList = this.findAllAid();
        if (mList != null && mList.size() != 0) {
            Iterator var3 = mList.iterator();

            com.topwise.sdk.emv.database.table.Aid cAid;
            do {
                if (!var3.hasNext()) {
                    return null;
                }

                cAid = (com.topwise.sdk.emv.database.table.Aid)var3.next();
                Log.d("findByAidAndAsi", "aid.getAid(): " + cAid.getAid());
            } while(!aid.startsWith(cAid.getAid()));

            return cAid;
        } else {
            return null;
        }
    }

    public void addAid(String aidStr) {
        com.topwise.sdk.emv.database.table.Aid aid = new Aid((byte)0);
        TlvList list = new TlvList();
        list.fromHex(aidStr);
        aid.fromTlvList(list);
        if (this.findByAid(aid.getAid()) != null) {
            this.update(aid);
        } else {
            this.insert(aid);
        }

    }

    public void deleteAid(String rid) {
        StringBuffer sb = new StringBuffer("delete from tb_aid where aid=='" + rid + "'");
        this.execSql(sb.toString(), (Object[])null);
    }

    public void deleteAllAid() {
        StringBuffer sb = new StringBuffer("delete from tb_aid");
        this.execSql(sb.toString(), (Object[])null);
    }
}

