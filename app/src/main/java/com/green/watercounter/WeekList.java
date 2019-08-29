package com.green.watercounter;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.stream.Stream;

public class WeekList implements Parcelable {

    private ArrayList<Daily> list;
    static private int listMAXsize = 7;
    private int size;


    public WeekList() {
        load();
        list = new ArrayList<Daily>();
        size = list.size();
    }

    protected WeekList(Parcel in) {
        list = in.createTypedArrayList(Daily.CREATOR);
        size = in.readInt();
    }

    public static final Creator<WeekList> CREATOR = new Creator<WeekList>() {
        @Override
        public WeekList createFromParcel(Parcel in) {
            return new WeekList(in);
        }

        @Override
        public WeekList[] newArray(int size) {
            return new WeekList[size];
        }
    };

    public void addDay(Daily addthis) throws FileNotFoundException {
        if (list.size() < listMAXsize - 1) {
            list.add(addthis);
        }
        else //already contains 7
        {
            removeFirst();
            list.add(addthis);
        }

        save();
    }

    private void removeFirst() {
        list.remove(0);
    }

    public Daily get(int index) {
        return list.get(index);
    }

    public int size() {
        if (list.isEmpty() || list == null) {
            return 0;
        }
        return list.size();
    }

    public Daily getCurrent() {
        return list.get(list.size() - 1);
    }

    public void load() {
        try {
            FileInputStream fin = new FileInputStream("list.dat");
            ObjectInputStream ois = new ObjectInputStream(fin);

            list = (ArrayList) ois.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new FileOutputStream("list.dat"));
        for (Daily daily : list) {
            pw.println(daily);
        }
        pw.close();

        ///////

        try {
            FileOutputStream fout = new FileOutputStream("list.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(list);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeArray(new ArrayList[]{list});
        dest.writeInt(size);
    }
}
