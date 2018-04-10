package a.getter_setter;

/**
 * Created by HP on 10-04-2018.
 */

public class User_Address {

    private String AddressID = null;
    private String FlatNo = null;
    private String StreetName = null;
    private String LandMark = null;
    private String State = null;
    private String District = null;
    private String Pincode = null;

    public String getAddressID()
    {
        return AddressID;
    }
    public String getFlatNo()
    {
        return FlatNo;
    }

    public String getStreetName()
    {
        return StreetName;
    }

    public String getLandMark()
    {
        return LandMark;
    }

    public String getState()
    {
        return State;
    }

    public String getDistrict()
    {
        return District;
    }
    public String getPincode()
    {
        return Pincode;
    }




    public void setAddressID(String AddressID)
    {
        this.AddressID = AddressID;
    }

    public void setFlatNo(String Flatno)
    {
        this.FlatNo = Flatno;
    }

    public void setStreetName(String Street)
    {
        this.StreetName = Street;
    }

    public void setLandMark(String Landmark)
    {
        this.LandMark = Landmark;
    }

    public void setState(String State)
    {
        this.State = State;
    }
    public void setPincode(String Pincode)
    {
        this.Pincode = Pincode;
    }

    public void setDistrict(String District)
    {
        this.District=District;
    }

}
