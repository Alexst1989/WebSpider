package ru.alex.st.hh.disk.search;

import java.util.LinkedList;
import java.util.List;

public class SearchResult {
        
    private List<Occurrence> occurrenceList = new LinkedList<>();
        
    public void addOccurrence(Occurrence occurrence) {
        occurrenceList.add(occurrence);
    }
    
    public List<Occurrence> getOccurrenceList() {
        return occurrenceList;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Occurrence occur : occurrenceList) {
            sb.append("Link: ").append(occur.getLink()).append("\r")
                .append("File path: ").append(occur.getPath()).append("\r")
                .append("Position in file: [").append(occur.getRow()).append(": ")
                .append(occur.getColumn()).append("]").append("\r")
                .append(occur.getGroup()).append("\r")
                .append("\r");
        }
        return sb.toString();
    }

}
