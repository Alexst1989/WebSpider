package ru.alex.st.hh.disk.search;

public class Occurrence {
    
    private String link;
    
    private int row;
    
    private int column;
    
    private String group;
    
    public Occurrence(String link, int row, int column, String group) {
        this.link = link;
        this.row = row;
        this.column = column;
        this.group = group;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
    
    
    

}
