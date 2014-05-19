package com.androidsocialnetworks.lib;

import android.os.Parcel;
import android.os.Parcelable;

public class GooglePlusPerson implements Parcelable {

    public static final Parcelable.Creator<GooglePlusPerson> CREATOR
            = new Parcelable.Creator<GooglePlusPerson>() {
        public GooglePlusPerson createFromParcel(Parcel in) {
            return new GooglePlusPerson(in);
        }

        public GooglePlusPerson[] newArray(int size) {
            return new GooglePlusPerson[size];
        }
    };

    public String id;
    public String name;
    public String avatarURL;
	public String aboutMe;
	public String birthday;
	public String braggingRights;
	public String coverURL;
	public int friendsCount;
	public String currentLocation;
	public int gender;
	public String lang;
	public String nickname;
	public int objectType;
	public String company;
	public String position;
	public String placeLivedValue;
	public int relationshipStatus;
	public String tagline;
	public String url;
    public GooglePlusPerson() {

    }

    private GooglePlusPerson(Parcel in) {
        id = in.readString();
        name = in.readString();
        avatarURL = in.readString();
        aboutMe = in.readString();
        birthday = in.readString();
        braggingRights = in.readString();
        coverURL = in.readString();
        friendsCount = in.readInt();
        currentLocation = in.readString();
        gender = in.readInt();
        lang = in.readString();
        nickname = in.readString();
        objectType = in.readInt();
        company = in.readString();
        position = in.readString();
        placeLivedValue = in.readString();
        relationshipStatus = in.readInt();
        tagline = in.readString();
        url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(avatarURL);
        dest.writeString(aboutMe);
        dest.writeString(birthday);
        dest.writeString(braggingRights);
        dest.writeString(coverURL);
        dest.writeInt(friendsCount);
        dest.writeString(currentLocation);
        dest.writeInt(gender);
        dest.writeString(lang);
        dest.writeString(nickname);
        dest.writeInt(objectType);
        dest.writeString(company);
        dest.writeString(position);
        dest.writeString(placeLivedValue);
        dest.writeInt(relationshipStatus);
        dest.writeString(tagline);
        dest.writeString(url);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof GooglePlusPerson)) return false;

        GooglePlusPerson that = (GooglePlusPerson) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (avatarURL != null ? !avatarURL.equals(that.avatarURL) : that.avatarURL != null)	return false;
        if (createdDate != 0 ? !createdDate.equals(that.createdDate) : that.createdDate != null)	return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (favoritesCount != 0 ? !(favoritesCount == that.favoritesCount) : that.favoritesCount != 0)	return false;
        if (followersCount != 0 ? !(followersCount == that.followersCount) : that.followersCount != 0)	return false;
        if (friendsCount != 0 ? !(friendsCount == that.friendsCount) : that.friendsCount != 0)	return false;
        if (lang != null ? !lang.equals(that.lang) : that.lang != null) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (screenName != null ? !screenName.equals(that.screenName) : that.screenName != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (timezone != null ? !timezone.equals(that.timezone) : that.timezone != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (isTranslator != null ? !isTranslator.equals(that.isTranslator) : that.isTranslator != null) return false;
        if (isVerified != null ? !isVerified.equals(that.isVerified) : that.isVerified != null) return false;

        return true;
    }
	
    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (avatarURL != null ? avatarURL.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (((Integer)favoritesCount) != null ? ((Integer)favoritesCount).hashCode() : 0);
        result = 31 * result + (((Integer)followersCount) != null ? ((Integer)followersCount).hashCode() : 0);
        result = 31 * result + (((Integer)friendsCount) != null ? ((Integer)friendsCount).hashCode() : 0);
        result = 31 * result + (lang != null ? lang.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (screenName != null ? screenName.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (timezone != null ? timezone.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (isTranslator != null ? isTranslator.hashCode() : 0);
        result = 31 * result + (isVerified != null ? isVerified.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GooglePlusPerson{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", avatarURL='" + avatarURL + '\'' +
				", createdDate='" + createdDate + '\'' +
				", description='" + description + '\'' +
				", favoritesCount='" + favoritesCount + '\'' +
				", followersCount='" + followersCount + '\'' +
				", friendsCount='" + friendsCount + '\'' +
				", lang='" + lang + '\'' +
				", location='" + location + '\'' +
				", screenName='" + screenName + '\'' +
				", status='" + status + '\'' +
				", timezone='" + timezone + '\'' +
				", url='" + url + '\'' +
				", isTranslator='" + isTranslator + '\'' +
				", isVerified='" + isVerified + '\'' +
                '}';
    }
}
