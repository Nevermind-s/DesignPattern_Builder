package boutlendj.salim.designpattern.model;



/**
 * Created by Salim on 25/10/2017.
 */

public class User {

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

}
