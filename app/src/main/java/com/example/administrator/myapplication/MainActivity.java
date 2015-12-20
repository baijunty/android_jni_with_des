package com.example.administrator.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class MainActivity extends AppCompatActivity {
    static {
        System.loadLibrary("app");
    }
    public native byte[] desEdCrypt(byte[] data, byte[] key,int flag);
    String data="1234567890";
    String key="12345678";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    byte b[] = desEdCrypt(data.getBytes(), key.getBytes(), 1);
                    System.out.println("encrypt==>" + Base64.encodeToString(b, Base64.DEFAULT));
                    byte jb[] = encrypt(key.getBytes(),data);
                    System.out.println("java encrypt==>" + Base64.encodeToString(jb, Base64.DEFAULT));
                    b = desEdCrypt(b, key.getBytes(), 0);
                    System.out.println("decrypt==>" + new String(b));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public static byte[] encrypt(byte rawKeyData[], String str)
            throws Exception{
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建一个DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(rawKeyData);
        // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, key, sr);
        // 现在，获取数据并加密
        byte data[] = str.getBytes();
        // 正式执行加密操作
        byte[] encryptedData = cipher.doFinal(data);
        return encryptedData;
    }

    void quickSort(int  array[], int start, int end)
    {
        if (start<end)
        {
            int i=start,j=end,x=array[start];
            while (i<j)
            {
                while (i<j&&array[j]>x)
                {
                    --j;
                }
                if (i<j)
                {
                    array[i++]=array[j];
                }
                while (i<j&&array[i]<x)
                {
                    ++i;
                }
                if (i<j)
                {
                    array[j--]=array[i];
                }
            }
            array[i]=x;
            quickSort(array,start,i-1);
            quickSort(array,i+1,end);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
