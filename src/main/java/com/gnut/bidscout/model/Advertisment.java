package com.gnut.bidscout.model;

public interface Advertisment {
     String getId();
     void setId(String id);
     String getOwner();
     void setOwner(String owner);
     boolean isEnabled();
     void setEnabled(boolean enabled);
     String getName();
     void setName(String name);
     Limits getLimits();
     void setLimits(Limits limits);
     Requirements getRequirements();
     void setRequirements(Requirements requirements);
     Statistics getStatistics();
     void setStatistics(Statistics statistics);
}
