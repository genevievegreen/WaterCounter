package com.green.watercounter;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ProgressBar;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;

/*
*   DAILY CLASS
*   HOLDS THE AMOUNT OF WATER CONSUMED PER DAY
* */

public class Daily implements Parcelable {

    private int numCups;
    private GregorianCalendar date;
    private String dateString;



    public Daily() {
        numCups = 0;
        date = new GregorianCalendar();

        String month[] = { "January", "February", "March", "April",
                "May", "June", "July", "August",
                "September", "October", "November", "December" };

        dateString = (month[getMonth()] + " " + getDay() + ", " + getYear());
    }

    public Daily (int num, GregorianCalendar cal) {
        numCups = num;
        this.date = cal;

        String month[] = { "January", "February", "March", "April",
                "May", "June", "July", "August",
                "September", "October", "November", "December" };

        dateString = (month[getMonth()] + " " + getDay() + ", " + getYear());
    }

    protected Daily(Parcel in) {
        numCups = in.readInt();
        dateString = in.readString();
    }

    public static final Creator<Daily> CREATOR = new Creator<Daily>() {
        @Override
        public Daily createFromParcel(Parcel in) {
            return new Daily(in);
        }

        @Override
        public Daily[] newArray(int size) {
            return new Daily[size];
        }
    };

    public void add() {
        numCups++;
    }

    public void minus() {
        numCups--;
    }

    public void setNumCups(int i) {
        numCups = i;
    }

    public int getYear(){
        return date.get(Calendar.YEAR);
    }

    public int getMonth(){
        return date.get(Calendar.MONTH);
    }

    public int getDay(){
        return date.get(Calendar.DAY_OF_MONTH);
    }

    public String getDateString() {
        return dateString;
    }

    public int getNumCups() {
        return numCups;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(numCups);
        dest.writeString(dateString);
    }
}
