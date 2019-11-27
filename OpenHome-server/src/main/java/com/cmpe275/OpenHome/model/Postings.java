package com.cmpe275.OpenHome.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
import java.util.Objects;

@Entity(name = "postings")
public class Postings {
    private String streetAddress;
    private String cityName;
    private Object state;
    private int zipcode;
    private Object sharingType;
    private Object propertyType;
    private int noOfBedrooms;
    private int placeArea;
    private int privateRoomArea;
    private byte hasPrivateBr;
    private byte hasPrivateShower;
    private int weekendRent;
    private int weekRent;
    private int contactNumber;
    private String description;
    private String pictureUrl;
    private byte parkingAvailable;
    private int parkingPay;
    private int dailyParkingFee;
    private Object wifi;
    private byte smokingAllowed;
    private String dayAvailability;
    private byte onsiteLaundry;
    private byte cityView;
    private int propertyId;
    private String userId;
    private Date startDate;
    private Date endDate;
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

    @Basic
    @Column(name = "STATE")
    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    @Basic
    @Column(name = "ZIPCODE")
    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    @Basic
    @Column(name = "SHARING_TYPE")
    public Object getSharingType() {
        return sharingType;
    }

    public void setSharingType(Object sharingType) {
        this.sharingType = sharingType;
    }

    @Basic
    @Column(name = "PROPERTY_TYPE")
    public Object getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(Object propertyType) {
        this.propertyType = propertyType;
    }

    @Basic
    @Column(name = "NO_OF_BEDROOMS")
    public int getNoOfBedrooms() {
        return noOfBedrooms;
    }

    public void setNoOfBedrooms(int noOfBedrooms) {
        this.noOfBedrooms = noOfBedrooms;
    }

    @Basic
    @Column(name = "PLACE_AREA")
    public int getPlaceArea() {
        return placeArea;
    }

    public void setPlaceArea(int placeArea) {
        this.placeArea = placeArea;
    }

    @Basic
    @Column(name = "PRIVATE_ROOM_AREA")
    public int getPrivateRoomArea() {
        return privateRoomArea;
    }

    public void setPrivateRoomArea(int privateRoomArea) {
        this.privateRoomArea = privateRoomArea;
    }

    @Basic
    @Column(name = "HAS_PRIVATE_BR")
    public byte getHasPrivateBr() {
        return hasPrivateBr;
    }

    public void setHasPrivateBr(byte hasPrivateBr) {
        this.hasPrivateBr = hasPrivateBr;
    }

    @Basic
    @Column(name = "HAS_PRIVATE_SHOWER")
    public byte getHasPrivateShower() {
        return hasPrivateShower;
    }

    public void setHasPrivateShower(byte hasPrivateShower) {
        this.hasPrivateShower = hasPrivateShower;
    }

    @Basic
    @Column(name = "WEEKEND_RENT")
    public int getWeekendRent() {
        return weekendRent;
    }

    public void setWeekendRent(int weekendRent) {
        this.weekendRent = weekendRent;
    }

    @Basic
    @Column(name = "WEEK_RENT")
    public int getWeekRent() {
        return weekRent;
    }

    public void setWeekRent(int weekRent) {
        this.weekRent = weekRent;
    }

    @Basic
    @Column(name = "CONTACT_NUMBER")
    public int getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(int contactNumber) {
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
    public byte getParkingAvailable() {
        return parkingAvailable;
    }

    public void setParkingAvailable(byte parkingAvailable) {
        this.parkingAvailable = parkingAvailable;
    }

    @Basic
    @Column(name = "PARKING_PAY")
    public int getParkingPay() {
        return parkingPay;
    }

    public void setParkingPay(int parkingPay) {
        this.parkingPay = parkingPay;
    }

    @Basic
    @Column(name = "DAILY_PARKING_FEE")
    public int getDailyParkingFee() {
        return dailyParkingFee;
    }

    public void setDailyParkingFee(int dailyParkingFee) {
        this.dailyParkingFee = dailyParkingFee;
    }

    @Basic
    @Column(name = "WIFI")
    public Object getWifi() {
        return wifi;
    }

    public void setWifi(Object wifi) {
        this.wifi = wifi;
    }

    @Basic
    @Column(name = "SMOKING_ALLOWED")
    public byte getSmokingAllowed() {
        return smokingAllowed;
    }

    public void setSmokingAllowed(byte smokingAllowed) {
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
    public byte getOnsiteLaundry() {
        return onsiteLaundry;
    }

    public void setOnsiteLaundry(byte onsiteLaundry) {
        this.onsiteLaundry = onsiteLaundry;
    }

    @Basic
    @Column(name = "CITY_VIEW")
    public byte getCityView() {
        return cityView;
    }

    public void setCityView(byte cityView) {
        this.cityView = cityView;
    }

    @Id
    @Column(name = "PROPERTY_ID")
    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
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
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "END_DATE")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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
        Postings postings = (Postings) o;
        return zipcode == postings.zipcode &&
                noOfBedrooms == postings.noOfBedrooms &&
                placeArea == postings.placeArea &&
                privateRoomArea == postings.privateRoomArea &&
                hasPrivateBr == postings.hasPrivateBr &&
                hasPrivateShower == postings.hasPrivateShower &&
                weekendRent == postings.weekendRent &&
                weekRent == postings.weekRent &&
                contactNumber == postings.contactNumber &&
                parkingAvailable == postings.parkingAvailable &&
                parkingPay == postings.parkingPay &&
                dailyParkingFee == postings.dailyParkingFee &&
                smokingAllowed == postings.smokingAllowed &&
                onsiteLaundry == postings.onsiteLaundry &&
                cityView == postings.cityView &&
                propertyId == postings.propertyId &&
                Objects.equals(streetAddress, postings.streetAddress) &&
                Objects.equals(cityName, postings.cityName) &&
                Objects.equals(state, postings.state) &&
                Objects.equals(sharingType, postings.sharingType) &&
                Objects.equals(propertyType, postings.propertyType) &&
                Objects.equals(description, postings.description) &&
                Objects.equals(pictureUrl, postings.pictureUrl) &&
                Objects.equals(wifi, postings.wifi) &&
                Objects.equals(dayAvailability, postings.dayAvailability) &&
                Objects.equals(userId, postings.userId) &&
                Objects.equals(startDate, postings.startDate) &&
                Objects.equals(endDate, postings.endDate) &&
                Objects.equals(pageViews, postings.pageViews);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetAddress, cityName, state, zipcode, sharingType, propertyType, noOfBedrooms, placeArea, privateRoomArea, hasPrivateBr, hasPrivateShower, weekendRent, weekRent, contactNumber, description, pictureUrl, parkingAvailable, parkingPay, dailyParkingFee, wifi, smokingAllowed, dayAvailability, onsiteLaundry, cityView, propertyId, userId, startDate, endDate, pageViews);
    }
}
