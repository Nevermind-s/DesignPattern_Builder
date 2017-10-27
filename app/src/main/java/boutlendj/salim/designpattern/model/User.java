package boutlendj.salim.designpattern.model;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Salim on 25/10/2017.
 */

public class User implements Parcelable{

    private String mID;
    private String mFirstName;
    private String mLastName;
    private String mNickName;
    private String mAddress;
    private String mPhone;
    private String mGender;
    private String mEmail;
    private String mAge;

    private User(Builder userBuilder){
        this.mID = userBuilder.mID;
        this.mFirstName = userBuilder.mFirstName;
        this.mLastName = userBuilder.mLastName;
        this.mNickName = userBuilder.mNickName;
        this.mAddress =userBuilder.mAddress;
        this.mPhone = userBuilder.mPhone;
        this.mGender = userBuilder.mGender;
        this.mEmail = userBuilder.mEmail;
        this.mAge = userBuilder.mAge;
    }

    public String getmID() {
        return mID;
    }

    public void setmID(String mID) {
        this.mID = mID;
    }

    public String getmFirstName() {
        return mFirstName;
    }

    public void setmFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getmLastName() {
        return mLastName;
    }

    public void setmLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getmNickName() {
        return mNickName;
    }

    public void setmNickName(String mNickName) {
        this.mNickName = mNickName;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getmGender() {
        return mGender;
    }

    public void setmGender(String mGender) {
        this.mGender = mGender;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmAge() {
        return mAge;
    }

    public void setmAge(String mAge) {
        this.mAge = mAge;
    }

    public static final class Builder {

        private String mID;
        private String mFirstName;
        private String mLastName;
        private String mNickName;
        private String mAddress;
        private String mPhone;
        private String mGender;
        private String mEmail;
        private String mAge;

        public Builder (String mID, String mEmail) {
            this.mID = mID;
            this.mEmail = mEmail;
        }

        public Builder firstName(String mFirstName) {
            this.mFirstName = mFirstName;
            return this;
        }

        public Builder lastName(String mLastName){
            this.mLastName = mLastName;
            return this;
        }

        public Builder age(String mAge){
            this.mAge = mAge;
            return this;
        }

        public Builder nickName(String mNickName) {
            this.mNickName = mNickName;
            return this;
        }

        public Builder address(String mAddress) {
            this.mAddress = mAddress;
            return this;
        }

        public Builder phone(String mPhone) {
            this.mPhone = mPhone;
            return this;
        }

        public Builder gender(String mGender) {
            this.mGender = mGender;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mID);
        dest.writeString(this.mFirstName);
        dest.writeString(this.mLastName);
        dest.writeString(this.mNickName);
        dest.writeString(this.mAddress);
        dest.writeString(this.mPhone);
        dest.writeString(this.mGender);
        dest.writeString(this.mEmail);
        dest.writeString(this.mAge);
    }

    protected User(Parcel in) {
        this.mID = in.readString();
        this.mFirstName = in.readString();
        this.mLastName = in.readString();
        this.mNickName = in.readString();
        this.mAddress = in.readString();
        this.mPhone = in.readString();
        this.mGender = in.readString();
        this.mEmail = in.readString();
        this.mAge = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
