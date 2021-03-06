package ru.testtask.trintiytest.models;


import android.databinding.BindingAdapter;
import android.support.v4.content.ContextCompat;
import android.text.format.DateUtils;
import android.util.Log;

import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ru.testtask.trintiytest.BaseApplication;
import ru.testtask.trintiytest.R;
import ru.testtask.trintiytest.stuff.Utils;

public class User {

    @SerializedName("age")
    private int age;

    @SerializedName("id")
    private int id;

    @SerializedName("lastSeen")
    private String lastSeen;

    @SerializedName("name")
    private String name;

    @SerializedName("status")
    private String status;

    @SerializedName("unreadMessages")
    private int unreadMessages;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("similarity")
    private int similarity;

    private Date lastSeenDate;

    private int colorSimilarity;
    private int colorStatus;

    private static final SimpleDateFormat lastSeenFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");

    private static final String TAG = "USER";

    public User(int age, int id, String lastSeen, String name, String status, int unreadMessages, String avatar, int similarity) {
        setAge(age);
        setId(id);
        setLastSeen(lastSeen);
        setName(name);
        setStatus(status);
        setUnreadMessages(unreadMessages);
        setAvatar(avatar);
        setSimilarity(similarity);
    }


    public int getUnreadMessages() {
        return unreadMessages;
    }

    public void setUnreadMessages(int unreadMessages) {
        this.unreadMessages = unreadMessages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastSeen() {
        String prefix = "dd MMMM";
        try {
            this.lastSeenDate = lastSeenFormat.parse(this.lastSeen);
            if (DateUtils.isToday(lastSeenDate.getTime()))
                prefix = BaseApplication.getInstance().getResources().getString(R.string.today);
            if (Utils.dayIsYesterday(new DateTime(lastSeenDate)))
                prefix = BaseApplication.getInstance().getResources().getString(R.string.yesterday);
            return (String)android.text.format.DateFormat.format(prefix + ", HH:mm", lastSeenDate);
        } catch (ParseException e) {
            Log.w(TAG, e);
            return "Invalidate value lastSeenDate: " + this.lastSeen;
        }
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAge() {
        return Integer.toString(age) + " " + Utils.age2string(age);
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSimilarity() {
        return Integer.toString(similarity) + "%";
    }

    public void setSimilarity(int similarity) {
        this.similarity = similarity;
    }

    @BindingAdapter({"android:imageUrl"})
    public static void loadImage(SimpleDraweeView view, String imageUrl) {
        view.setImageURI(imageUrl);
    }

    @BindingAdapter({"android:imageRes"})
    public static void loadImageRes(SimpleDraweeView view, int imageRes) {
        ImageRequest req = ImageRequestBuilder.newBuilderWithResourceId(imageRes).build();
        view.setImageURI(req.getSourceUri());
    }
    public int getColorSimilarity() {
        if (similarity < 70) {
            if (similarity < 40)
                colorSimilarity = ContextCompat.getColor(BaseApplication.getInstance().getApplicationContext(), R.color.colorAccent);
            else
                colorSimilarity = ContextCompat.getColor(BaseApplication.getInstance().getApplicationContext(), R.color.yellow);
        } else
            colorSimilarity =  ContextCompat.getColor(BaseApplication.getInstance().getApplicationContext(), R.color.green);
        return colorSimilarity;
    }

    public int getColorStatus() {
        switch (status) {
            case "dnd":
                colorStatus = R.drawable.dnd;
                break;
            case "away":
                colorStatus = R.drawable.away;
                break;
            case "online":
                colorStatus = R.drawable.online;
                break;
            default:
                colorStatus = R.drawable.dnd;
                break;
        }
        return colorStatus;
    }

}
