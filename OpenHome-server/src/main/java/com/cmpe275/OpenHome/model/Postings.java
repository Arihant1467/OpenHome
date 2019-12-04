package com.cmpe275.OpenHome.model;

import com.cmpe275.OpenHome.enums.PropertyType;
import com.cmpe275.OpenHome.enums.SharingType;
import com.cmpe275.OpenHome.enums.StateType;
import com.cmpe275.OpenHome.enums.WifiType;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "postings", schema = "Openhome")
public class Postings {
    private String streetAddress;
    private String cityName;
    private StateType state;
    private Integer zipcode;
    private SharingType sharingType;
    private PropertyType propertyType;
    private Integer noOfBedrooms;
    private Integer placeArea;
    private Integer privateRoomArea;
    private Byte hasPrivateBr;
    private Byte hasPrivateShower;
    private double weekendRent;
    private double weekRent;
    private Integer contactNumber;
    private String description;
    private String pictureUrl;
    private Byte parkingAvailable;
    private Integer parkingPay;
    private Integer dailyParkingFee;
    private WifiType wifi;
    private Byte smokingAllowed;
    private String dayAvailability;
    private Byte onsiteLaundry;
    private Byte cityView;
    private Integer propertyId;
    private String userId;
    private Timestamp startDate;
    private Timestamp endDate;
    private Integer pageViews;

    @Basic
    @Column(name = "STREET_ADDRESS")
    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    @Basic
    @Column(name = "CITY_NAME")
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "STATE")
    public StateType getState() {
        return state;
    }

    public void setState(StateType state) {
        this.state = state;
    }

    @Basic
    @Column(name = "ZIPCODE")
    public Integer getZipcode() {
        return zipcode;
    }

    public void setZipcode(Integer zipcode) {
        this.zipcode = zipcode;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "SHARING_TYPE")
    public SharingType getSharingType() {
        return (SharingType) sharingType;
    }

    public void setSharingType(SharingType sharingType) {
        this.sharingType = sharingType;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "PROPERTY_TYPE")
    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    @Basic
    @Column(name = "NO_OF_BEDROOMS")
    public Integer getNoOfBedrooms() {
        return noOfBedrooms;
    }

    public void setNoOfBedrooms(Integer noOfBedrooms) {
        this.noOfBedrooms = noOfBedrooms;
    }

    @Basic
    @Column(name = "PLACE_AREA")
    public Integer getPlaceArea() {
        return placeArea;
    }

    public void setPlaceArea(Integer placeArea) {
        this.placeArea = placeArea;
    }

    @Basic
    @Column(name = "PRIVATE_ROOM_AREA")
    public Integer getPrivateRoomArea() {
        return privateRoomArea;
    }

    public void setPrivateRoomArea(Integer privateRoomArea) {
        this.privateRoomArea = privateRoomArea;
    }

    @Basic
    @Column(name = "HAS_PRIVATE_BR")
    public Byte getHasPrivateBr() {
        return hasPrivateBr;
    }

    public void setHasPrivateBr(Byte hasPrivateBr) {
        this.hasPrivateBr = hasPrivateBr;
    }

    @Basic
    @Column(name = "HAS_PRIVATE_SHOWER")
    public Byte getHasPrivateShower() {
        return hasPrivateShower;
    }

    public void setHasPrivateShower(Byte hasPrivateShower) {
        this.hasPrivateShower = hasPrivateShower;
    }

    @Basic
    @Column(name = "WEEKEND_RENT")
    public double getWeekendRent() {
        return weekendRent;
    }

    public void setWeekendRent(double weekendRent) {
        this.weekendRent = weekendRent;
    }

    @Basic
    @Column(name = "WEEK_RENT")
    public double getWeekRent() {
        return weekRent;
    }

    public void setWeekRent(double weekRent) {
        this.weekRent = weekRent;
    }

    @Basic
    @Column(name = "CONTACT_NUMBER")
    public Integer getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Integer contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Basic
    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "PICTURE_URL")
    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    @Basic
    @Column(name = "PARKING_AVAILABLE")
    public Byte getParkingAvailable() {
        return parkingAvailable;
    }

    public void setParkingAvailable(Byte parkingAvailable) {
        this.parkingAvailable = parkingAvailable;
    }

    @Basic
    @Column(name = "PARKING_PAY")
    public Integer getParkingPay() {
        return parkingPay;
    }

    public void setParkingPay(Integer parkingPay) {
        this.parkingPay = parkingPay;
    }

    @Basic
    @Column(name = "DAILY_PARKING_FEE")
    public Integer getDailyParkingFee() {
        return dailyParkingFee;
    }

    public void setDailyParkingFee(Integer dailyParkingFee) {
        this.dailyParkingFee = dailyParkingFee;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "WIFI")
    public WifiType getWifi() {
        return wifi;
    }

    public void setWifi(WifiType wifi) {
        this.wifi = wifi;
    }

    @Basic
    @Column(name = "SMOKING_ALLOWED")
    public Byte getSmokingAllowed() {
        return smokingAllowed;
    }

    public void setSmokingAllowed(Byte smokingAllowed) {
        this.smokingAllowed = smokingAllowed;
    }

    @Basic
    @Column(name = "DAY_AVAILABILITY")
    public String getDayAvailability() {
        return dayAvailability;
    }

    public void setDayAvailability(String dayAvailability) {
        this.dayAvailability = dayAvailability;
    }

    @Basic
    @Column(name = "ONSITE_LAUNDRY")
    public Byte getOnsiteLaundry() {
        return onsiteLaundry;
    }

    public void setOnsiteLaundry(Byte onsiteLaundry) {
        this.onsiteLaundry = onsiteLaundry;
    }

    @Basic
    @Column(name = "CITY_VIEW")
    public Byte getCityView() {
        return cityView;
    }

    public void setCityView(Byte cityView) {
        this.cityView = cityView;
    }

    @Id
    //@Column(name="U_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "PROPERTY_ID")
    public Integer getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }

    @Basic
    @Column(name = "USER_ID")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "START_DATE")
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "END_DATE")
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "PAGE_VIEWS")
    public Integer getPageViews() {
        return pageViews;
    }

    public void setPageViews(Integer pageViews) {
        this.pageViews = pageViews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Postings that = (Postings) o;
        return Objects.equals(streetAddress, that.streetAddress) &&
                Objects.equals(cityName, that.cityName) &&
                Objects.equals(state, that.state) &&
                Objects.equals(zipcode, that.zipcode) &&
                Objects.equals(sharingType, that.sharingType) &&
                Objects.equals(propertyType, that.propertyType) &&
                Objects.equals(noOfBedrooms, that.noOfBedrooms) &&
                Objects.equals(placeArea, that.placeArea) &&
                Objects.equals(privateRoomArea, that.privateRoomArea) &&
                Objects.equals(hasPrivateBr, that.hasPrivateBr) &&
                Objects.equals(hasPrivateShower, that.hasPrivateShower) &&
                Objects.equals(weekendRent, that.weekendRent) &&
                Objects.equals(weekRent, that.weekRent) &&
                Objects.equals(contactNumber, that.contactNumber) &&
                Objects.equals(description, that.description) &&
                Objects.equals(pictureUrl, that.pictureUrl) &&
                Objects.equals(parkingAvailable, that.parkingAvailable) &&
                Objects.equals(parkingPay, that.parkingPay) &&
                Objects.equals(dailyParkingFee, that.dailyParkingFee) &&
                Objects.equals(wifi, that.wifi) &&
                Objects.equals(smokingAllowed, that.smokingAllowed) &&
                Objects.equals(dayAvailability, that.dayAvailability) &&
                Objects.equals(onsiteLaundry, that.onsiteLaundry) &&
                Objects.equals(cityView, that.cityView) &&
                Objects.equals(propertyId, that.propertyId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(pageViews, that.pageViews);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetAddress, cityName, state, zipcode, sharingType, propertyType, noOfBedrooms, placeArea, privateRoomArea, hasPrivateBr, hasPrivateShower, weekendRent, weekRent, contactNumber, description, pictureUrl, parkingAvailable, parkingPay, dailyParkingFee, wifi, smokingAllowed, dayAvailability, onsiteLaundry, cityView, propertyId, userId, startDate, endDate, pageViews);
    }
}
