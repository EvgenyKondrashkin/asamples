package com.androidsocialnetworks.lib;

import android.os.Parcel;
import android.os.Parcelable;

public class FacebookPerson extends SocialPerson implements Parcelable {

    public static final Parcelable.Creator<FacebookPerson> CREATOR
            = new Parcelable.Creator<FacebookPerson>() {
        public FacebookPerson createFromParcel(Parcel in) {
            return new FacebookPerson(in);
        }

        public FacebookPerson[] newArray(int size) {
            return new FacebookPerson[size];
        }
    };

    public String id;
    public String name;
    public String avatarURL;
	public String firstName;
	public String middleName;
	public String lastName;
	public String link;
	public String username;
	public String birthday;
	public String city;

    public FacebookPerson() {

    }

    private FacebookPerson(Parcel in) {
        id = in.readString();
        name = in.readString();
        avatarURL = in.readString();
		firstName = in.readString();
		middleName = in.readString();
		lastName = in.readString();
		link = in.readString();
		username = in.readString();
		birthday = in.readString();
		city = in.readString();
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
        dest.writeString(firstName);
		dest.writeString(middleName);
		dest.writeString(lastName);
		dest.writeString(link);
		dest.writeString(username);
		dest.writeString(birthday);
		dest.writeString(city);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof FacebookPerson)) return false;

        FacebookPerson that = (FacebookPerson) o;

        if (avatarURL != null ? !avatarURL.equals(that.avatarURL) : that.avatarURL != null)	return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
		if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
		if (middleName != null ? !middleName.equals(that.middleName) : that.middleName != null) return false;
		if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
		if (link != null ? !link.equals(that.link) : that.link != null) return false;
		if (username != null ? !username.equals(that.username) : that.username != null) return false;
		if (birthday != null ? !birthday.equals(that.birthday) : that.birthday != null) return false;
		if (city != null ? !city.equals(that.city) : that.city != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (avatarURL != null ? avatarURL.hashCode() : 0);
		result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
		result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
		result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
		result = 31 * result + (link != null ? link.hashCode() : 0);
		result = 31 * result + (username != null ? username.hashCode() : 0);
		result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
		result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FacebookPerson{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", avatarURL='" + avatarURL + '\'' +
				", firstName='" + firstName + '\'' +
				", middleName='" + middleName + '\'' +
				", lastName='" + lastName + '\'' +
				", link='" + link + '\'' +
				", username='" + username + '\'' +
				", birthday='" + birthday + '\'' +
				", city='" + city + '\'' +
                '}';
    }
}
