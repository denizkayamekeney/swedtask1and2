package task1.dto;

public class DataItem {
    Data data;

    public DataItem( Data data ) {
        this.data = data;
    }

    public DataItem() {
    }

    public Data getData() {
        return data;
    }

    public void setData( Data data ) {
        this.data = data;
    }
}
