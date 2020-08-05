package task1.dto;

public class DataItem {
    CoeficientsData data;

    public DataItem( CoeficientsData data ) {
        this.data = data;
    }

    public DataItem() {
    }

    public CoeficientsData getData() {
        return data;
    }

    public void setData( CoeficientsData data ) {
        this.data = data;
    }
}
